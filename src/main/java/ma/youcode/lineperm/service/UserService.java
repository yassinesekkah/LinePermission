package ma.youcode.lineperm.service;

import java.util.HashMap;
import java.util.Map;

import ma.youcode.lineperm.model.User;

public class UserService {
    
    private final Map<String, User> users = new HashMap<>();

    public boolean userExists(String login){
        return users.containsKey(login);
    }

    public void addUser(User user){
        users.put(user.getLogin(), user);
    }

    public User getUser(String login){
        return users.get(login);
    }
}
