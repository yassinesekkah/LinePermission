package ma.youcode.lineperm;

import java.util.HashMap;
import java.util.Map;

import org.mindrot.jbcrypt.BCrypt;

import ma.youcode.lineperm.model.User;
import ma.youcode.lineperm.service.UserService;
import ma.youcode.lineperm.ui.ConsoleApp;

import org.mindrot.jbcrypt.BCrypt;

public class Main {

    public static void main(String[] args) {

        // ConsoleApp app = new ConsoleApp();
        // app.run();

        UserService service = new UserService();

        service.createUser("yassine", "123");

        User user = service.getUser("yassine");

        System.out.println(user.getPasswordHash());

        System.out.println(
        BCrypt.checkpw("123", user.getPasswordHash())
);

       
    }
}
