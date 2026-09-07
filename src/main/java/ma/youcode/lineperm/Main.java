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
    }
}
