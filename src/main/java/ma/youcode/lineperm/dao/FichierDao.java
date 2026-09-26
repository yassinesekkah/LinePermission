package ma.youcode.lineperm.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import ma.youcode.lineperm.model.Fichier;
import ma.youcode.lineperm.model.User;

public class FichierDao extends AbstractDao<Fichier> {

    public UserDao userDao = new UserDao();

    //tested
    @Override
    public void save(Fichier fichier) {

        String sql = """
                    INSERT INTO fichiers(name, owner_id, permissions)
                    VALUES (?, ?, ?);
                """;
        try (PreparedStatement statement = con.prepareStatement(sql)) {

            System.out.println("name = " + fichier.getName());
            System.out.println("owner id = " + fichier.getOwner().getId());
            System.out.println("permissions = " + fichier.getPermissionsDisplay());

            statement.setString(1, fichier.getName());
            statement.setInt(2, fichier.getOwner().getId());
            statement.setString(3, fichier.getPermissionsDisplay());

            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erreur fichier: " + e.getMessage());
        }
    }

    //tested
    @Override
    public Optional<Fichier> findById(int id) {

        String sql = """
                    SELECT * FROM fichiers
                    WHERE id = ?;
                """;

        try (PreparedStatement statement = con.prepareStatement(sql)) {

            statement.setInt(1, id);

            ResultSet result = statement.executeQuery();

            if (result.next()) {
                int fichierId = result.getInt("id");
                String name = result.getString("name");
                int ownerId = result.getInt("owner_id");
                String permi = result.getString("permissions");

                Optional<User> optionalOwner = userDao.findById(ownerId);

                if (optionalOwner.isEmpty()) {
                    return Optional.empty();
                }
                User user = optionalOwner.get();

                boolean[] permissions = parsePermissions(permi);

                Fichier fichier = new Fichier(fichierId, name, user, permissions[0], permissions[1], permissions[2],
                        permissions[3], permissions[4], permissions[5]);

                return Optional.of(fichier);

            }
        } catch (SQLException e) {
            System.out.println("Erreur lors trouver le fichier");

        }
        return Optional.empty();
    }

    public Optional<Fichier> findByName(String name){

        String sql = """
                    SELECT * FROM fichiers
                    WHERE name = ?;
                """;

        try(PreparedStatement statement = con.prepareStatement(sql)){

            statement.setString(1, name);
            ResultSet result = statement.executeQuery();

            if(result.next()){
                int id = result.getInt("id");
                int ownerId = result.getInt("owner_id");
                String permissionString = result.getString("permissions");

                Optional<User> userOp = userDao.findById(ownerId);

                if(userOp.isEmpty()){
                    return Optional.empty();
                }

                User user = userOp.get();

                boolean[] permissions = parsePermissions(permissionString);

                Fichier fichier = new Fichier(id, name, user, permissions[0], permissions[1], permissions[2], permissions[3], permissions[4], permissions[5]);

                return Optional.of(fichier);
            }

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }
    //tested
    private boolean[] parsePermissions(String permissions) {

        return new boolean[] {
                permissions.charAt(0) == 'r',
                permissions.charAt(1) == 'w',
                permissions.charAt(2) == 'd',
                permissions.charAt(4) == 'r',
                permissions.charAt(5) == 'w',
                permissions.charAt(6) == 'd'
        };
    }

    //tested
    public List<Fichier> findByOwner(int ownerId) {

        String sql = """
                    SELECT * FROM fichiers
                    WHERE owner_id = ?;
                """;

        List<Fichier> fichierList = new ArrayList<>();

        // owner get
        Optional<User> ownerOptional = userDao.findById(ownerId);

        if (ownerOptional.isEmpty()) {
            return fichierList;
        }

        User user = ownerOptional.get();

        try (PreparedStatement statement = con.prepareStatement(sql)) {

            statement.setInt(1, ownerId);
            ResultSet result = statement.executeQuery();

            while (result.next()) {

                int id = result.getInt("id");
                String name = result.getString("name");
                String permission = result.getString("permissions");

                boolean[] permissionsArray = parsePermissions(permission);

                Fichier fichier = new Fichier(id, name, user, permissionsArray[0], permissionsArray[1],
                        permissionsArray[2], permissionsArray[3], permissionsArray[4], permissionsArray[5]);

                fichierList.add(fichier);

            }
            return fichierList;

        } catch (SQLException e) {
            System.out.println("Erreur");
        }
        return fichierList;
    }

    //tested
    public void updatePermissions(int id, String permissions) {

        String sql = """
                    UPDATE fichiers
                    SET permissions = ?
                    WHERE id = ?;
                """;

        try (PreparedStatement statement = con.prepareStatement(sql)) {

            statement.setString(1, permissions);
            statement.setInt(2, id);
            int rows = statement.executeUpdate();

            if (rows == 1) {
                System.out.println("fichier modifie");
            } else {
                System.out.println("fichier introuvable");
            }

        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification des permissions");
        }
    }
}
