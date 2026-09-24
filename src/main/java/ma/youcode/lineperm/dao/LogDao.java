package ma.youcode.lineperm.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import ma.youcode.lineperm.model.ActionTypes;
import ma.youcode.lineperm.model.Fichier;
import ma.youcode.lineperm.model.Log;
import ma.youcode.lineperm.model.Status;
import ma.youcode.lineperm.model.User;

public class LogDao extends AbstractDao<Log>{

    UserDao userDao = new UserDao();
    FichierDao fichierDao = new FichierDao();

    @Override 
    public void save(Log log){

        String sql = """
                    INSERT INTO logs (user_id, fichier_id, action, resultat, created_at)
                    VALUES (?, ?, ?, ?, ?);
                """;

        LocalDateTime createdAt = LocalDateTime.of(log.getDate(), log.getTime());

        try(PreparedStatement statement = con.prepareStatement(sql)){
            
            statement.setInt(1, log.getUser().getId());
            statement.setInt(2, log.getFichier().getId());
            statement.setString(3, log.getAction().name());
            statement.setString(4, log.getState().name());
            statement.setString(5, createdAt.toString());

            int rows = statement.executeUpdate();

            if(rows == 1){
                System.out.println("log ajouter avec succese");
            }
            else{
                System.out.println("Erreur lor l'ajoute de log");
            }
            
        }catch(SQLException e){
            System.out.println("Erreur lors de l'ajout du log: " + e.getMessage());
        }
    }

    @Override 
    public Optional<Log> findById(int id){

        String sql = """
                    SELECT * FROM logs
                    WHERE id = ?;
                """;

        try(PreparedStatement statement = con.prepareStatement(sql)){
            
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();

            if(result.next()){
                int userId = result.getInt("user_id");
                //user prepare
                Optional<User> userOptional = userDao.findById(userId);

                if(userOptional.isEmpty()){
                    System.out.println("Erreur lors trouver utilisateur");
                    return Optional.empty();
                }
                
                User user = userOptional.get();

                //fichier object prepare
                int fichierId = result.getInt("fichier_id");

                Optional<Fichier> fichierOptional = fichierDao.findById(fichierId);

                if(fichierOptional.isEmpty()){
                    System.out.println("Erreur lors trouver le fichier");
                    return Optional.empty();
                }

                Fichier fichier = fichierOptional.get();



                String actionString = result.getString("action");
                ActionTypes action = ActionTypes.valueOf(actionString);

                String resultatString = result.getString("resultat");
                Status resultat = Status.valueOf(resultatString);

                //prepare date & time
                String createdAtString = result.getString("created_at");
                LocalDateTime createdAt = LocalDateTime.parse(createdAtString);
                LocalDate date = createdAt.toLocalDate();
                LocalTime time = createdAt.toLocalTime();

                //creation d'objet log
                Log log = new Log(id, date, time,user , action, fichier, resultat);

                return Optional.of(log);

            }

        }catch(SQLException e){
            System.out.println("Erreur lors trouver log");
        }

        return Optional.empty();
    }
}
