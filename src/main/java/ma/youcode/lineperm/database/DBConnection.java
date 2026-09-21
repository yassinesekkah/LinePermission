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

            createUsersTable();
            createFichiersTable();

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

    private void createUsersTable() {

        String sql = """
                        CREATE TABLE IF NOT EXISTS users(
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    login TEXT NOT NULL UNIQUE,
                    password TEXT NOT NULL
                )
                        """;

        try (Statement statement = connection.createStatement()) {
            
            statement.execute(sql);
            System.out.println("Table users créée");

        } catch (SQLException e) {
            System.out.println("Erreur creation table users");
        }
    }

    private void createFichiersTable() {

        String sql = """
                        CREATE TABLE IF NOT EXISTS fichiers(
                    id  INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT NOT NULL,
                    owner_id INTEGER NOT NULL,
                    permissions TEXT NOT NULL,
                    FOREIGN KEY (owner_id) REFERENCES users(id)
                );
                        """;

        try (Statement statement = connection.createStatement()){
    
            statement.execute(sql);
            System.out.println("Table fichiers cree.");

        } catch (SQLException e) {
            System.out.println("Erreur sur creation table fichiers.");
        }

    }
}
