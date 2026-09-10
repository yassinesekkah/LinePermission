package ma.youcode.lineperm.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import ma.youcode.lineperm.model.FichierProtege;
import ma.youcode.lineperm.access.ControleAcces;

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

    public boolean writeFile(String fileName, String login, String newContent){

        FichierProtege fichier = getFile(fileName);

        if(fichier == null){
            return false;
        }

        if(!ControleAcces.estAutorise(login, fichier, 'w')){
            return false;
        }

        Path dataDir = Path.of("data");
        Path filePath = dataDir.resolve(fileName);

        try{
            Files.writeString(filePath, newContent);
            return true;
        }
        catch(IOException e){
            return false;
        }

    }

    public String readFile(String fileName, String login){

        FichierProtege fichier = getFile(fileName);

        if(fichier == null){
            return null;
        }

        if(!ControleAcces.estAutorise(login, fichier, 'r')){
            return null;
        }

        Path dataDir = Path.of("data");
        Path filePath = dataDir.resolve(fileName);

        try{
            return Files.readString(filePath);
        }
        catch(IOException e){
            return null;
        }
    }
    
}
