package ma.youcode.lineperm.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import ma.youcode.lineperm.model.User;

public class UserDao extends AbstractDao<User> {

    @Override 
    public void save(User user) {

        String sql = """
                INSERT INTO users(login, password)
                    VALUES(?, ?);
                """;

        try(PreparedStatement statement = con.prepareStatement(sql)){

            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPasswordHash());

            statement.executeUpdate();

        }catch(SQLException e){
            System.out.println("Erreur lors de l'ajout de l'utilisateur");
        }
    }

    @Override
    public Optional<User> findById(int id) {

        String sql = """
                SELECT * FROM users
                WHERE id = ?;
                """;

        try(PreparedStatement statement = con.prepareStatement(sql)){

            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();

            if(result.next()){
                String login = result.getString("login");
                String password = result.getString("password");

                User user = new User(id, login, password);

                return Optional.of(user);
            }

            return Optional.empty();

        }catch(SQLException e){

            System.out.println("Erreur lors de la recherche utilisateur");

            return Optional.empty();
        }
    }

    public Optional<User> findByLogin(String login){

        String sql = """
                    SELECT * FROM users
                    WHERE (login = ?);
                """;

        try(PreparedStatement statement = con.prepareStatement(sql)){
            statement.setString(1, login);
            ResultSet result = statement.executeQuery();

            if(result.next()){
                int id = result.getInt("id");
                String logingStr = result.getString("login");
                String password = result.getString("password");

                User user = new User(id, logingStr, password);

                return Optional.of(user);
            }

            return Optional.empty();

        }
        catch(SQLException e){

            System.out.println("Erreur lors de la recherche utilisateur");
            return Optional.empty();
        }
    }

    public void delete(int id){

        String sql = """
                    DELETE FROM users
                    WHERE id = ?;
                """;

        try(PreparedStatement statement = con.prepareStatement(sql)){

            statement.setInt(1, id);

            int row = statement.executeUpdate();

            if(row == 1){
                System.out.println("User supprimé");
            }
            else{
                System.out.println("Aucun user trouvé");
            }
        }
        catch(SQLException e){
            System.out.println("Erreur lors supprimer user");
        }
    }

}
