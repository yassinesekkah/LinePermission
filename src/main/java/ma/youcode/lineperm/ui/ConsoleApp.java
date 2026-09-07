package ma.youcode.lineperm.ui;

import java.util.Scanner;

import ma.youcode.lineperm.service.UserService;

public class ConsoleApp{

    private Scanner scanner = new Scanner(System.in);
    private final UserService userService = new UserService();



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
                    System.out.println("login");
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