// package ma.youcode.lineperm.test;

// import java.util.Optional;

// import ma.youcode.lineperm.dao.UserDao;
// import ma.youcode.lineperm.model.User;

// public class UserDaoTest {

//     public static void main(String[] args){

//         User user = new User("ismail@gmail.com", "123");

//         UserDao userDao = new UserDao();

//         userDao.save(user);

//         Optional<User> op = userDao.findById(1);
//         User userFound = op.get();

//         System.out.println(userFound.getLogin() + " password : " + userFound.getPasswordHash());

//         Optional<User> oop = userDao.findByLogin("ismail@gmail.com");

//         if(oop.isEmpty()){
//             System.out.println("user not found");

//         }
//         else{
//             User foundByL = oop.get();
//             System.out.println("with email : " + foundByL.getLogin() + " pass: " + foundByL.getPasswordHash());
//         }

//         userDao.delete(33);
//         userDao.delete(1);
    
//     }
// }
