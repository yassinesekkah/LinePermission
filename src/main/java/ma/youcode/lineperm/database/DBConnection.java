package ma.youcode.lineperm.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {

    private static DBConnection instance;
    private Connection connection;

    private static final String URL = "jdbc:sqlite:auditdb.db";

    private DBConnection() {

        try {
            connection = DriverManager.getConnection(URL);

            createUserTable();

        } catch (SQLException e) {
            System.out.println("Erreur de connexion à la base de données");
        }
    }

    public static DBConnection getInstance() {

        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    public void createUserTable() {

        String sql = """
                        CREATE TABLE IF NOT EXISTS users(
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    login TEXT NOT NULL UNIQUE,
                    password TEXT NOT NULL
                )
                        """;

        try {
            Statement statement = connection.createStatement();
            statement.execute(sql);

            System.out.println("Table users créée");

        } catch (SQLException e) {
            System.out.println("Erreur creation table users");
        }
    }
}
