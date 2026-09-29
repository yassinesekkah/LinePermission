// package ma.youcode.lineperm.test;

// import java.util.List;
// import java.util.Optional;

// import ma.youcode.lineperm.dao.FichierDao;
// import ma.youcode.lineperm.dao.UserDao;
// import ma.youcode.lineperm.model.Fichier;
// import ma.youcode.lineperm.model.User;

// public class FichierDaoTest {

//     public static void main(String[] args) {

//         UserDao userDao = new UserDao();
//         FichierDao fichierDao = new FichierDao();

//         userDao.save(new User("aissam@gmail.com", "123"));

//         Optional<User> outmaneOp = userDao.findByLogin("outmane@gmail.com");

//         if (outmaneOp.isEmpty()) {
//             System.out.println("user not saved");
//         } else {
//             User outmane = outmaneOp.get();
//             fichierDao.save(new Fichier("321", outmane));
//         }

//         Optional<Fichier> foundByidOp = fichierDao.findById(4);

//         if (foundByidOp.isPresent()) {

//             Fichier foundById = foundByidOp.get();

//             System.out.println("found by id : " + foundById.getName());
//         } else {
//             System.out.println("file not found");
//         }

//         List<Fichier> fbo = fichierDao.findByOwner(2);

//         System.out.println("les fichier de utilisateur 2 est : ");
//         for (Fichier ff : fbo) {

//             System.out.println("    " + ff.getName());
//         }

//         fichierDao.updatePermissions(9, "rwd|r--");

//     }
// }
