package ma.youcode.lineperm;

import ma.youcode.lineperm.ui.ConsoleApp;


public class Main {

    public static void main(String[] args) {

        ConsoleApp app = new ConsoleApp();
        app.run();

        // UserDao userDao = new UserDao();
        // FichierDao fichierDao = new FichierDao();
        // LogDao logDao = new LogDao();

        // userDao.save(new User("ismail", "123"));
        // userDao.save(new User("aissam", "3333"));

        // Optional<User> ismailOp = userDao.findByLogin("ismail");
        // Optional<User> aissamOp = userDao.findByLogin("aissam");

        // if(ismailOp.isEmpty()){
        //      Optional.empty();
        //      return;
        // }
        //  if(aissamOp.isEmpty()){
        //      Optional.empty();
        //      return;
        // }
        // User aissam = aissamOp.get();
        // User ismail = ismailOp.get();

        // fichierDao.save(new Fichier("ismailFichier1", ismail));
        // fichierDao.save(new Fichier("aissamFile", aissam));

        // List<Fichier> fichOp = fichierDao.findByOwner(ismail.getId());
        // List<Fichier> fichOp2 = fichierDao.findByOwner(aissam.getId());
        // System.out.println("les fichier de ismail sont : ");

        // Fichier ismFichier = null;
        // for(Fichier fich : fichOp){
        //     ismFichier = fich;
        // }

        // Fichier aissaFich = null;
        // for(Fichier fich : fichOp2){
        //     aissaFich = fich;
        // }

        // // System.out.println(ismFichier.getName());

        // logDao.save(new Log(LocalDate.now(), LocalTime.now(), ismail, ActionTypes.LECTURE, ismFichier, Status.REFUSE));
        // logDao.save(new Log(LocalDate.now(), LocalTime.now(), ismail, ActionTypes.LECTURE, ismFichier, Status.REFUSE));
        // logDao.save(new Log(LocalDate.now(), LocalTime.now(), aissam, ActionTypes.LECTURE, aissaFich, Status.REFUSE));
        // logDao.save(new Log(LocalDate.now(), LocalTime.now(), aissam, ActionTypes.LECTURE, aissaFich, Status.OK));

        // Optional<Log> logOp = logDao.findById(1);

        // if(logOp.isEmpty()){
        //     Optional.empty();
        //     return;
        // }
        // Log lll = logOp.get();

        // // System.out.println(logDao.countActionsByUser());

        // System.out.println(logDao.getTop3ConsultedFiles());
        

    }
}
