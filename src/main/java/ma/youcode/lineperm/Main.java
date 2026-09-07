package ma.youcode.lineperm;

import java.util.HashMap;
import java.util.Map;

import ma.youcode.lineperm.model.User;
import ma.youcode.lineperm.service.UserService;
import ma.youcode.lineperm.ui.ConsoleApp;

public class Main{

    public static void main(String[] args){

        // ConsoleApp app = new ConsoleApp();
        // app.run();

       User user = new User("yassine", "hash123");

       UserService userService = new UserService();

       userService.addUser(user);

       System.out.println(userService.userExists("yassine"));

       User userFound = userService.getUser("yassine");

       System.out.println(userFound.getLogin());
       System.out.println(userFound.getPasswoordHash());

       userService.createUser("ahmed@gmail.com", "1234321");

       boolean ifouf = userService.userExists("ahmed@gmail.com");

       System.out.println(ifouf);

        boolean islogin =  userService.login("ahmed@gmail.com", "1234321");

       System.out.println(islogin);
    }
}
