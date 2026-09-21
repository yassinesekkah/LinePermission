package ma.youcode.lineperm;

import java.sql.Connection;

import ma.youcode.lineperm.database.DBConnection;
import ma.youcode.lineperm.ui.ConsoleApp;

public class Main {

    public static void main(String[] args) {

        // ConsoleApp app = new ConsoleApp();
        // app.run();

        Connection connection = DBConnection.getInstance().getConnection();

        if(connection != null){
            System.out.println("Connexion SQLite réussie");
        }
        else{
            System.out.println("Connexion échouée");
        }


        
       
    }
}
