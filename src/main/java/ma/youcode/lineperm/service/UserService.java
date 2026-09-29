package ma.youcode.lineperm.service;

import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;

import ma.youcode.lineperm.dao.UserDao;
import ma.youcode.lineperm.model.User;

public class UserService {

    private final UserDao userDao = new UserDao();


    public boolean userExists(String login) {

        return userDao.findByLogin(login).isPresent();
    }


    public void addUser(User user) {

        userDao.save(user);
    }


    public Optional<User> getUser(String login) {

        return userDao.findByLogin(login);

    }


    public boolean createUser(String login, String password) {

        if (userExists(login)) {
            return false;
        }

        String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());

        User user = new User(login, passwordHash);

        addUser(user);

        return true;
    }

    
    public boolean login(String login, String password) {

        Optional<User> userOp = getUser(login);

        if (userOp.isEmpty()) {
            return false;
        }
        User user = userOp.get();

        return BCrypt.checkpw(password, user.getPasswordHash());
    }
}
