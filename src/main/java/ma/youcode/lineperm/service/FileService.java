package ma.youcode.lineperm.service;

import java.io.EOFException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import ma.youcode.lineperm.model.FichierProtege;

public class FileService {

    private Map<String, FichierProtege> files = new HashMap<>();

    public boolean fileExists(String name){
        return files.containsKey(name);
    }

    public FichierProtege getFile(String name){
        
        return files.get(name);
    }

    public boolean isFileNameValid(String name){
        if(name.contains("/") || name.contains("\\") || name.contains("..")){
            return false;
        }
        return true;
    }
    

    public boolean createFile(String name, String owner){

        if(fileExists(name)){
            return false;
        }

        if(!isFileNameValid(name)){
            return false;
        }

        Path dataDir = Path.of("data");
        Path filePath = dataDir.resolve(name);

        try{
            Files.createDirectories(dataDir);
            Files.createFile(filePath);

            FichierProtege fichier = new FichierProtege(name, owner);
            files.put(name, fichier);

            return true;

        }catch(IOException e){
            return false;
        }

    }

    public Collection<FichierProtege> getAllFiles(){

        return files.values();
    }

    public boolean canRead(FichierProtege file, String login){

        String owner = file.getOwner();
        // String otherPermissions = file.getOthersPermissions();

        if(login.equals(owner)){
            return true;
        }

        if(file.getOthersCanRead()){
            return true;
        }
        
        return false;
    }

    public boolean canWrite(FichierProtege fichier, String login){

        String fileOwner = fichier.getOwner();

        // String otherPermissions = file.getOthersPermissions();

        
        if(login.equals(fileOwner)){
            return true;
        }

        if(fichier.getOthersCanWrite()){
            return true;
        }

        return false;
    }

    public boolean writeFile(String fileName, String login, String newContent){

        FichierProtege fichier = getFile(fileName);

        if(fichier == null){
            return false;
        }

        boolean canWrite = canWrite(fichier, login);

        if(!canWrite){
            return false;
        }

        fichier.setContent(newContent);

        return true;

    }
    
}
