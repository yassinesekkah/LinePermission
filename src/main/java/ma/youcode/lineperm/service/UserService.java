package ma.youcode.lineperm.service;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.mindrot.jbcrypt.BCrypt;

import ma.youcode.lineperm.model.User;

public class UserService {

    private final Map<String, User> users = new HashMap<>();

    public UserService(){
        loadUsers();
    }

    public boolean userExists(String login) {
        return users.containsKey(login);
    }

    public void addUser(User user) {
        users.put(user.getLogin(), user);
    }

    public User getUser(String login) {
        return users.get(login);
    }

    public boolean createUser(String login, String password) {

        if (userExists(login)) {
            return false;
        }

        String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());

        User user = new User(login, passwordHash);

        addUser(user);

        saveUsers();

        return true;
    }

    public boolean login(String login, String password) {

        if (!userExists(login)) {
            return false;
        }

        User user = getUser(login);

        return BCrypt.checkpw(password, user.getPasswordHash());
    }

    private void saveUsers() {

        Path dataDir = Path.of("data");
        Path usersFile = Path.of("data/users.txt");

        try {
            if (!Files.exists(dataDir)) {
                Files.createDirectories(dataDir);
            }

            List<String> lines = new ArrayList<>();

            for (User user : users.values()) {
                String line = user.getLogin() + ":" + user.getPasswordHash();
                lines.add(line);
            }

            Files.write(usersFile, lines);

        } catch (IOException e) {

        }
    }

    public void loadUsers(){

        Path userFile = Path.of("data/users.txt");

        if(!Files.exists(userFile)){
            return;
        }
        
        try{
            List<String> lines = Files.readAllLines(userFile);

            for(String line : lines){

                String[] parts = line.split(":");

                String login = parts[0];
                String hashedPassword = parts[1];

                User user = new User(login, hashedPassword);

                addUser(user);
            }



        }catch(IOException e){

        }

    }
}
