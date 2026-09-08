package ma.youcode.lineperm.ui;

import java.util.Scanner;

import ma.youcode.lineperm.model.User;
import ma.youcode.lineperm.service.UserService;

public class ConsoleApp{

    private final Scanner scanner = new Scanner(System.in);
    private final UserService userService = new UserService();
    private User currentUser = null;

    public void logout(){

        if(currentUser == null){

            System.out.println("Aucun utilisateur connecte");
            return;
        }

        currentUser = null;
        System.out.println("Deconnexion reussie");
    }


    public void run(){

        while (true) {

            if(currentUser == null){
                System.out.print("linperm> ");
            }
            else{
                System.out.print(currentUser.getLogin() + "@linperm>");
            }
            
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
                    
                    if(currentUser != null){
                        System.out.println("Un utilisateur est deja connecte");
                    }

                    System.out.print("login : ");
                    String log = scanner.nextLine();

                    System.out.print("Mot de passe : ");
                    String pass = scanner.nextLine();

                    boolean logged = userService.login(log, pass);

                    if(logged){
                        currentUser = userService.getUser(log);
                        System.out.println("Connexion reussie");
                    }
                    else{
                        System.out.println("Identifiants incorrects");
                    }
                    break;

                case "logout":
                    
                    logout();
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