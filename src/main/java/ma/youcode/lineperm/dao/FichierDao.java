package ma.youcode.lineperm.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

import ma.youcode.lineperm.model.Fichier;

public class FichierDao extends AbstractDao<Fichier> {

    @Override 
    public void save(Fichier fichier){

        String sql = """
                    INSERT INTO fichiers(name, owner_id, permissions)
                    VALUES (?, ?, ?);
                """;
        try(PreparedStatement statement = con.prepareStatement(sql)){

            statement.setString(1, fichier.getName());
            statement.setInt(2, fichier.getOwner().getId());
            statement.setString(3, fichier.getPermissionsDisplay());

            statement.executeUpdate();
        }
        catch(SQLException e){
            System.out.println("Erreur lors de l'ajout du fichier");
        }
    }

    @Override 
    public Optional<Fichier> findById(int id){

    }
}
