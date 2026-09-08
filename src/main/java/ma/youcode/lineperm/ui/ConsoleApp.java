package ma.youcode.lineperm.ui;

import java.util.Scanner;

import ma.youcode.lineperm.model.User;
import ma.youcode.lineperm.service.UserService;

public class ConsoleApp {

    private final Scanner scanner = new Scanner(System.in);
    private final UserService userService = new UserService();
    private User currentUser = null;

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

                default:
                    if (!command.isEmpty()) {
                        System.out.println("Commande inconue");
                    }
                    break;
            }

        }
    }
}