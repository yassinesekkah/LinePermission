package ma.youcode.lineperm.service;

import java.util.HashMap;
import java.util.Map;

import ma.youcode.lineperm.model.File;

public class FileService {

    private Map<String, File> files = new HashMap<>();

    public boolean fileExists(String name){
        return files.containsKey(name);
    }

    public boolean createFile(String name, String owner){

        if(fileExists(name)){
            return false;
        }

        File file = new File(name, owner);

        files.put(name, file);

        return true;

    }
    
}
