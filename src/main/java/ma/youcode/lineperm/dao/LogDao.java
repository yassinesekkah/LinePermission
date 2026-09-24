package ma.youcode.lineperm.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;

import ma.youcode.lineperm.model.Log;

public class LogDao extends AbstractDao<Log>{

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

    public Optional<Log> findById(int id){

        return Optional.empty();
    }
}
