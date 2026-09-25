package ma.youcode.lineperm.dao;

import java.lang.StackWalker.Option;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import ma.youcode.lineperm.model.ActionTypes;
import ma.youcode.lineperm.model.Fichier;
import ma.youcode.lineperm.model.Log;
import ma.youcode.lineperm.model.Status;
import ma.youcode.lineperm.model.User;

public class LogDao extends AbstractDao<Log> {

    private final UserDao userDao = new UserDao();
    private final FichierDao fichierDao = new FichierDao();

    @Override
    public void save(Log log) {

        String sql = """
                    INSERT INTO logs (user_id, fichier_id, action, resultat, created_at)
                    VALUES (?, ?, ?, ?, ?);
                """;

        LocalDateTime createdAt = LocalDateTime.of(log.getDate(), log.getTime());

        try (PreparedStatement statement = con.prepareStatement(sql)) {

            statement.setInt(1, log.getUser().getId());
            statement.setInt(2, log.getFichier().getId());
            statement.setString(3, log.getAction().name());
            statement.setString(4, log.getState().name());
            statement.setString(5, createdAt.toString());

            int rows = statement.executeUpdate();

            if (rows == 1) {
                System.out.println("log ajouter avec succese");
            } else {
                System.out.println("Erreur lor l'ajoute de log");
            }

        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout du log: " + e.getMessage());
        }
    }

    @Override
    public Optional<Log> findById(int id) {

        String sql = """
                    SELECT * FROM logs
                    WHERE id = ?;
                """;

        try (PreparedStatement statement = con.prepareStatement(sql)) {

            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                int userId = result.getInt("user_id");
                // user prepare
                Optional<User> userOptional = userDao.findById(userId);

                if (userOptional.isEmpty()) {
                    System.out.println("Erreur lors trouver utilisateur");
                    return Optional.empty();
                }

                User user = userOptional.get();

                // fichier object prepare
                int fichierId = result.getInt("fichier_id");

                Optional<Fichier> fichierOptional = fichierDao.findById(fichierId);

                if (fichierOptional.isEmpty()) {
                    System.out.println("Erreur lors trouver le fichier");
                    return Optional.empty();
                }

                Fichier fichier = fichierOptional.get();

                String actionString = result.getString("action");
                ActionTypes action = ActionTypes.valueOf(actionString);

                String resultatString = result.getString("resultat");
                Status resultat = Status.valueOf(resultatString);

                // prepare date & time
                String createdAtString = result.getString("created_at");
                LocalDateTime createdAt = LocalDateTime.parse(createdAtString);
                LocalDate date = createdAt.toLocalDate();
                LocalTime time = createdAt.toLocalTime();

                // creation d'objet log
                Log log = new Log(id, date, time, user, action, fichier, resultat);

                return Optional.of(log);

            }

        } catch (SQLException e) {
            System.out.println("Erreur lors trouver log");
        }

        return Optional.empty();
    }

    public long countTotalActions() {

        String sql = """
                    SELECT count(*) AS total FROM logs;
                """;

        try (Statement statement = con.createStatement()) {

            ResultSet result = statement.executeQuery(sql);

            if (result.next()) {
                return result.getLong("total");
            }

        } catch (SQLException e) {
            System.out.println("Erreur");
        }
        return 0;
    }

    public long countRefusedAccess() {

        String sql = """
                    SELECT count(*) AS tot_refuse FROM logs
                    WHERE resultat = 'REFUSE';
                """;

        try (Statement statement = con.createStatement()) {

            ResultSet result = statement.executeQuery(sql);

            if (result.next()) {
                return result.getLong("tot_refuse");
            }

        } catch (SQLException e) {
            System.out.println("Erreur");
        }

        return 0;
    }

    public long countDistinctUsers() {

        String sql = """
                    SELECT count(DISTINCT user_id) AS total_users from logs;
                """;

        try (Statement statement = con.createStatement()) {

            ResultSet result = statement.executeQuery(sql);

            if (result.next()) {
                return result.getLong("total_users");
            }

        } catch (SQLException e) {
            System.out.println("Erreur");
        }

        return 0;
    }

    public Map<String, Long> countActionsByUser() {

        String sql = """
                    SELECT user_id, count(*) AS total_by_user FROM logs
                    GROUP BY user_id;
                """;
        Map<String, Long> actionByUser = new HashMap<>();

        try (Statement statement = con.createStatement()) {

            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {

                int userId = result.getInt("user_id");
                Long count = result.getLong("total_by_user");

                Optional<User> userOp = userDao.findById(userId);
                if (userOp.isEmpty()) {
                    continue;
                }
                User user = userOp.get();
                String userName = user.getLogin();

                actionByUser.put(userName, count);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return actionByUser;
    }

    public Map<String, Long> getTop3ConsultedFiles() {

        String sql = """
                    SELECT fichier_id, count(*) AS total_logs FROM logs
                    GROUP BY fichier_id
                    ORDER BY total_logs DESC
                    LIMIT 3;
                """;
        Map<String, Long> totalByFichier = new HashMap<>();

        try (Statement statement = con.createStatement()) {

            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {

                // fichierName prepare
                int fichierId = result.getInt("fichier_id");

                Optional<Fichier> fichierOp = fichierDao.findById(fichierId);
                if (fichierOp.isEmpty()) {
                    continue;
                }
                Fichier fichier = fichierOp.get();
                String fichierName = fichier.getName();

                Long totalFichierLogs = result.getLong("total_logs");

                totalByFichier.put(fichierName, totalFichierLogs);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return totalByFichier;
    }

    public List<Log> getRefusedAccessByUser(String name) {

        String sql = """
                    SELECT * FROM logs
                    WHERE resultat = 'REFUSE' AND user_id = ?;
                """;

        Optional<User> userOp = userDao.findByLogin(name);

        if (userOp.isEmpty()) {
            return new ArrayList<>();
        }
        User user = userOp.get();
        int userId = user.getId();

        List<Log> refusedByUser = new ArrayList<>();

        try (PreparedStatement statement = con.prepareStatement(sql)) {

            statement.setInt(1, userId);
            ResultSet result = statement.executeQuery();

            while (result.next()) {

                int id = result.getInt("id");
                int fichierId = result.getInt("fichier_id");
                String actionString = result.getString("action");
                String resultatString = result.getString("resultat");
                String createdAtString = result.getString("created_at");

                // prepare fichier
                Optional<Fichier> fichierOp = fichierDao.findById(fichierId);

                if (fichierOp.isEmpty()) {
                    continue;
                }
                Fichier fichier = fichierOp.get();

                // prepare Action
                ActionTypes action = ActionTypes.valueOf(actionString);

                // prepare Status
                Status state = Status.valueOf(resultatString);

                // prepare date and time
                LocalDateTime localDateTime = LocalDateTime.parse(createdAtString);
                LocalDate localDate = localDateTime.toLocalDate();
                LocalTime localTime = localDateTime.toLocalTime();

                Log log = new Log(id, localDate, localTime, user, action, fichier, state);

                refusedByUser.add(log);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return refusedByUser;
    }

    public Optional<String> getMostActiveUser() {

        String sql = """
                    SELECT user_id, count(*) AS total FROM logs
                    GROUP BY user_id
                    ORDER BY total DESC
                    LIMIT 1;
                """;

        try (Statement statement = con.createStatement()) {

            ResultSet result = statement.executeQuery(sql);

            if (result.next()) {
                int userId = result.getInt("user_id");
                Optional<User> userOp = userDao.findById(userId);

                if (userOp.isEmpty()) {
                    return Optional.empty();
                }
                User user = userOp.get();
                String userName = user.getLogin();

                return Optional.of(userName);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return Optional.empty();
    }

    public Map<ActionTypes, Long> countActionByType() {

        String sql = """
                    SELECT action, count(*) AS total FROM logs
                    GROUP BY action;
                """;

        Map<ActionTypes, Long> res = new HashMap<>();

        try(Statement statement = con.createStatement()){

            ResultSet result = statement.executeQuery(sql);

            while(result.next()){

                String actionString = result.getString("action");

                //prepare action
                ActionTypes action = ActionTypes.valueOf("total");

                Long count = result.getLong(actionString);

                res.put(action, count);
            }

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return res;
    }
}
