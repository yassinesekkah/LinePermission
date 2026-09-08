package ma.youcode.lineperm.ui;

import java.util.Scanner;

import ma.youcode.lineperm.model.User;
import ma.youcode.lineperm.service.UserService;

public class ConsoleApp{

    private Scanner scanner = new Scanner(System.in);
    private final UserService userService = new UserService();
    private User curretUser = null;


    public void run(){

        while (true) {
            System.out.print("linperm> ");
            
            String command = scanner.nextLine();
            
            switch (command) {

                case "signup":
                    
                    System.out.println("Login: ");
                    String login = scanner.nextLine();

                    System.out.println("mot de passe: ");
                    String password = scanner.nextLine();

                    boolean created = userService.createUser(login, password);

                    if(created){
                        System.out.println("compte cree");
                    }
                    else{
                        System.out.println("Login deja pris");
                    }

                    break;

                case "login":
                    System.out.print("login : ");
                    String log = scanner.nextLine();

                    System.out.print("Mot de passe : ");
                    String pass = scanner.nextLine();

                    boolean logged = userService.login(log, pass);

                    if(logged){
                        curretUser = userService.getUser(log);
                        System.out.println("Connexion reussie");
                    }
                    else{
                        System.out.println("Identifiants incorrects");
                    }
                    break;

                case "logout":
                    System.out.println("logout");
                    break;

                case "exit":
                    return;
            
                default:
                    if(!command.isEmpty()){
                        System.out.println("Commande inconue");
                    }
                    break;
            }

        }
    }
}