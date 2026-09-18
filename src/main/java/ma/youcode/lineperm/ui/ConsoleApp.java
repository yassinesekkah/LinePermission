package ma.youcode.lineperm.ui;

import ma.youcode.lineperm.model.AccessLog;
import ma.youcode.lineperm.model.FichierProtege;

import java.util.List;
import java.util.Scanner;
import ma.youcode.lineperm.model.User;
import ma.youcode.lineperm.service.FileService;
import ma.youcode.lineperm.service.LogAnalyzer;
import ma.youcode.lineperm.service.LogService;
import ma.youcode.lineperm.service.UserService;

public class ConsoleApp {

    private final Scanner scanner = new Scanner(System.in);
    private final UserService userService = new UserService();
    private User currentUser = null;
    private final LogService logService = new LogService();
    private final FileService fileService = new FileService(logService);

    private final LogAnalyzer logAnalyzer = new LogAnalyzer(logService.getLogs());

    private void logout() {

        if (currentUser == null) {

            System.out.println("Aucun utilisateur connecte");
            return;
        }

        currentUser = null;
        System.out.println("Deconnexion reussie");
    }

    private boolean isValidLogin(String login) {

        return !login.isEmpty() && !login.contains(" ") && !login.contains(":");
    }

    private boolean isConnected() {
        if (currentUser == null) {
            System.out.println("Vous devez etre connecte");
            return false;
        }
        ;
        return true;
    }

    private void listFiles() {

        System.out.println("===> Tous les fichiers <===");

        for (FichierProtege file : fileService.getAllFiles()) {

            String permissions = file.getPermissionsDisplay();
            String fileName = file.getName();
            String ownerName = file.getOwner();

            System.out.println(permissions + " owner : " + ownerName + " fichier: " + fileName);
        }

        System.out.println("==========================");
    }

    private void cat() {

        System.out.println("Nom du fichier : ");
        String name = scanner.nextLine();

        FichierProtege file = fileService.getFile(name);

        if (file == null) {
            System.out.println("Fichier introuvable");
            return;
        }

        String content = fileService.readFile(name, currentUser.getLogin());

        if (content == null) {
            System.out.println("Permission refusee");
            return;
        }

        System.out.println(content);

    }

    private void nano() {
        System.out.print("Le nom de fichier: ");
        String name = scanner.nextLine();

        if (!fileService.fileExists(name)) {
            System.out.println("fichier introuvable");
            return;
        }

        boolean canWrite = fileService.canWriteFile(name, currentUser.getLogin());

        if (!canWrite) {
            System.out.println("Permission denied.");
            return;
        }

        StringBuilder content = new StringBuilder();

        System.out.println("Entrez le contenu. Tapez EOF pour terminer:");

        while (true) {
            String line = scanner.nextLine();

            if (line.equals("EOF")) {
                break;
            }
            content.append(line).append("\n");
        }

        boolean written = fileService.writeFile(name, currentUser.getLogin(), content.toString());

        if (written) {
            System.out.println("Fichier modifie");
        } else {
            System.out.println("Erreur pendant l'ecriture");
        }
    }

    private void chmod(String[] parts) {

        if (!isConnected()) {
            return;
        }

        if (parts.length != 3) {
            System.out.println("command invalid");
            return;
        }

        String permission = parts[1];
        String fileName = parts[2];

        boolean changed = fileService.changePermission(fileName, currentUser.getLogin(), permission);

        if (changed) {
            System.out.println("Permission modifiee");
        } else {
            System.out.println("Aucune modification");
        }
    }

    private void rm(String[] parts) {

        if (!isConnected()) {
            return;
        }

        if (parts.length != 2) {
            System.out.println("command invalid");
            return;
        }

        String fichier = parts[1];

        boolean deleted = fileService.deleteFile(fichier, currentUser.getLogin());

        if (deleted) {
            System.out.println("le fichier est supprime");
        } else {
            System.out.println("Aucune modification");
        }

    }

    private void showRefusedAccessByUser() {

        System.out.print("Nom utilisateur : ");
        String name = scanner.nextLine();

        List<AccessLog> refusedLogs = logAnalyzer.getRefusedAccessByUser(name);

        if (refusedLogs.isEmpty()) {
            System.out.println("Aucun accès refusé.");
            return;
        }

        for (AccessLog log : refusedLogs) {
            System.out.println(
                    log.getDate() + ";" +
                            log.getTime() + ";" +
                            log.getUser() + ";" +
                            log.getAction() + ";" +
                            log.getFichier() + ";" +
                            log.getState());
        }
    }

    private void showStats() {

        System.out.println("=== Statistiques ===");
        System.out.println("1. Total actions");
        System.out.println("2. Accès refusés");
        System.out.println("3. Utilisateurs distincts");
        System.out.println("4. Actions par utilisateur");
        System.out.println("5. Top 3 fichiers consultés");
        System.out.println("6. Accès refusés d'un utilisateur");
        System.out.println("7. Utilisateur le plus actif");
        System.out.println("8. Actions par type");
        System.out.println("0. Retour");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                System.out.println(logAnalyzer.countTotalActions());
                break;

            case 2:
                System.out.println(logAnalyzer.countRefusedAccess());
                break;

            case 3:
                System.out.println(logAnalyzer.countDistinctUsers());
                break;

            case 4:
                System.out.println(logAnalyzer.countActionsByUser());
                break;

            case 5:
                System.out.println(logAnalyzer.getTop3ConsultedFiles());
                break;

            case 6:
                showRefusedAccessByUser();
                break;

            case 7:
                System.out.println(
                        logAnalyzer.getMostActiveUser()
                                .orElse("Aucun utilisateur"));
                break;

            case 8:
                System.out.println(logAnalyzer.countActionByType());
                break;

            case 0:
                return;

            default:
                System.out.println("Choix invalide");
        }
    }

    public void run() {

        System.out.println("Bienvenue dans LinePermission");
        System.out.println("Commandes disponibles : signup, login, logout, exit");

        while (true) {

            if (currentUser == null) {
                System.out.print("linperm> ");
            } else {
                System.out.print(currentUser.getLogin() + "@linperm>");
            }

            // String command = scanner.nextLine().trim().toLowerCase();

            String commandLine = scanner.nextLine().trim();

            if (commandLine.isEmpty()) {
                continue;
            }
            String[] parts = commandLine.split("\\s+");
            String command = parts[0];

            switch (command) {

                case "signup":

                    if (currentUser != null) {
                        System.out.println("Un utilisateur est deja connecte");
                        break;
                    }

                    System.out.print("Login: ");
                    String login = scanner.nextLine();

                    System.out.print("mot de passe: ");
                    String password = scanner.nextLine();

                    if (!isValidLogin(login)) {
                        System.out.println("Login invalid");
                        break;
                    }

                    if (password.isEmpty()) {
                        System.out.println("Le mot de passe ne peut pas être vide");
                        break;
                    }

                    boolean created = userService.createUser(login, password);

                    if (created) {
                        System.out.println("compte cree");
                    } else {
                        System.out.println("Login deja pris");
                    }

                    break;

                case "login":

                    if (currentUser != null) {
                        System.out.println("Un utilisateur est deja connecte");
                        break;
                    }

                    System.out.print("login : ");
                    String log = scanner.nextLine();

                    System.out.print("Mot de passe : ");
                    String pass = scanner.nextLine();

                    if (!isValidLogin(log)) {
                        System.out.println("Login invalide");
                        break;
                    }

                    boolean logged = userService.login(log, pass);

                    if (logged) {
                        currentUser = userService.getUser(log);
                        System.out.println("Connexion reussie");
                    } else {
                        System.out.println("Identifiants incorrects");
                    }
                    break;

                case "logout":

                    logout();
                    break;

                case "exit":

                    System.out.println("Au revoir!");
                    return;

                case "touch":

                    if (!isConnected()) {
                        break;
                    }

                    System.out.print("Le nom de fichier : ");
                    String fileName = scanner.nextLine().trim();

                    boolean createdFile = fileService.createFile(fileName, currentUser.getLogin());

                    if (createdFile) {
                        System.out.println("Fichier cree");
                    } else {
                        System.out.println("Ce nom de fichier est deja utilise");
                    }
                    break;

                case "ls":
                    if (!isConnected()) {
                        break;
                    }
                    listFiles();
                    break;

                case "cat":
                    if (!isConnected()) {
                        break;
                    }
                    cat();
                    break;

                case "nano":
                    if (!isConnected()) {
                        break;
                    }

                    nano();
                    break;

                case "chmod":

                    chmod(parts);
                    break;

                case "rm":
                    rm(parts);
                    break;

                case "stats":
                    showStats();
                    break;

                default:
                    if (!command.isEmpty()) {
                        System.out.println("Commande inconue");
                    }
                    break;
            }
        }
    }
}