package ma.youcode.lineperm;

import java.sql.Connection;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import ma.youcode.lineperm.dao.FichierDao;
import ma.youcode.lineperm.dao.LogDao;
import ma.youcode.lineperm.dao.UserDao;
import ma.youcode.lineperm.database.DBConnection;
import ma.youcode.lineperm.model.ActionTypes;
import ma.youcode.lineperm.model.Fichier;
import ma.youcode.lineperm.model.Log;
import ma.youcode.lineperm.model.Status;
import ma.youcode.lineperm.model.User;
import ma.youcode.lineperm.ui.ConsoleApp;

public class Main {

    public static void main(String[] args) {

        // ConsoleApp app = new ConsoleApp();
        // app.run();

        UserDao userDao = new UserDao();
        FichierDao fichierDao = new FichierDao();
        LogDao logDao = new LogDao();

        userDao.save(new User("ismail", "123"));

        Optional<User> ismailOp = userDao.findByLogin("ismail");

        if(ismailOp.isEmpty()){
             Optional.empty();
             return;
        }

        User ismail = ismailOp.get();

        fichierDao.save(new Fichier("ismailFichier1", ismail));

        List<Fichier> fichOp = fichierDao.findByOwner(ismail.getId());
        System.out.println("les fichier de ismail sont : ");

        Fichier ismFichier = null;
        for(Fichier fich : fichOp){
            ismFichier = fich;
        }

        System.out.println(ismFichier.getName());

        logDao.save(new Log(LocalDate.now(), LocalTime.now(), ismail, ActionTypes.LECTURE, ismFichier, Status.OK));

        Optional<Log> logOp = logDao.findById(1);

        if(logOp.isEmpty()){
            Optional.empty();
            return;
        }
        Log lll = logOp.get();

        System.out.println(lll.getUser().getLogin());

        
        System.out.println(logDao.countTotalActions());
    }
}
