package ma.youcode.lineperm.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static DBConnection instance;
    private Connection connection;

    private static final String URL = "jdbc:sqlite:auditdb.db";

    private DBConnection() {

        try{
            connection = DriverManager.getConnection(URL);
        }
        catch(SQLException e){
            System.out.println("Erreur de connexion à la base de données");
        }
    }

    public static DBConnection getInstance() {

        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }

    public Connection getConnection(){
        return connection;
    }
}
