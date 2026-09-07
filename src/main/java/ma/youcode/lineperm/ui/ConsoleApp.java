package ma.youcode.lineperm.ui;

import java.util.Scanner;

public class ConsoleApp{

    private Scanner scanner = new Scanner(System.in);

    public void run(){

        while (true) {
            System.out.println("linperm> ");
            
            String command = scanner.nextLine();
            
            switch (command) {

                case "signup":
                    System.out.println("signup");
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