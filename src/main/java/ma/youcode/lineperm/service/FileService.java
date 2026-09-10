package ma.youcode.lineperm.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import ma.youcode.lineperm.model.File;

public class FileService {

    private Map<String, File> files = new HashMap<>();

    public boolean fileExists(String name){
        return files.containsKey(name);
    }

    public File getFile(String name){
        
        return files.get(name);
    }

    public boolean createFile(String name, String owner){

        if(fileExists(name)){
            return false;
        }

        File file = new File(name, owner);

        files.put(name, file);

        return true;

    }

    public Collection<File> getAllFiles(){

        return files.values();
    }

    public boolean canRead(File file, String login){

        String owner = file.getOwner();
        String otherPermissions = file.getOthersPermissions();

        if(login.equals(owner)){
            return true;
        }

        if(otherPermissions.contains("r")){
            return true;
        }
        
        return false;
    }

    public boolean canWrite(File file, String login){

        String fileOwner = file.getOwner();

        String otherPermissions = file.getOthersPermissions();

        
        if(login.equals(fileOwner)){
            return true;
        }

        if(otherPermissions.contains("w")){
            return true;
        }

        return false;
    }

    public boolean writeFile(String fileName, String login, String newContent){

        File file = getFile(fileName);

        if(file == null){
            return false;
        }

        boolean canWrite = canWrite(file, login);

        if(!canWrite){
            return false;
        }

        file.setContent(newContent);

        return true;

    }
    
}
