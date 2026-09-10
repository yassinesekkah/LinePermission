package ma.youcode.lineperm.ui;

import ma.youcode.lineperm.model.File;
import java.util.Scanner;

import ma.youcode.lineperm.model.User;
import ma.youcode.lineperm.service.FileService;
import ma.youcode.lineperm.service.UserService;

public class ConsoleApp {

    private final Scanner scanner = new Scanner(System.in);
    private final UserService userService = new UserService();
    private User currentUser = null;
    private final FileService fileService = new FileService();

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

    private boolean isConnected(){
        if(currentUser == null){
            System.out.println("Vous devez etre connecte");
            return false;
        };
        return true;
    }

    private void listFiles(){

        System.out.println("===> Tous les fichiers <===");

        for(File file : fileService.getAllFiles()){

                String ownerPermissions = file.getOwnerPermissions();
                String otherPermissions = file.getOthersPermissions();
                String fileName = file.getName();


                System.out.println(ownerPermissions + "|" + otherPermissions + " " + fileName);
        }

        System.out.println("==========================");
    }

    private void cat(){

        System.out.println("Nom du fichier : ");
        String name = scanner.nextLine();

        File file = fileService.getFile(name);

        if(file == null){
            System.out.println("Fichier introuvable");
            return;
        }

        boolean canRead = fileService.canRead(file, currentUser.getLogin());

        if(!canRead){
            System.out.println("Acces refuse");
            return;
        }

        System.out.println(file.getContent());

    }

    private nano(){

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

            String command = scanner.nextLine().trim().toLowerCase();

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

                    if(!isConnected()){
                        break;
                    }

                    System.out.print("Le nom de fichier : " );
                    String fileName = scanner.nextLine().trim();

                    boolean createdFile = fileService.createFile(fileName, currentUser.getLogin());

                    if(createdFile){
                        System.out.println("Fichier cree");
                    }
                    else{
                        System.out.println("Ce nom de fichier est deja utilise");
                    }
                    break;

                case "ls":
                    if(!isConnected()){
                        break;
                    }
                    listFiles();
                    break;

                case "cat":
                    if(!isConnected()){
                        break;
                    }
                    cat();
                    break;

                case "nano":
                    if(!isConnected()){
                        break;
                    }

                    nano();
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