package ma.youcode.lineperm.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ma.youcode.lineperm.model.FichierProtege;
import ma.youcode.lineperm.access.ControleAcces;

public class FileService {

    public FileService(){
        loadFiles();
    }

    private Map<String, FichierProtege> files = new HashMap<>();
    private final Path metadataPath = Path.of("data", "files.txt");

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

            saveFiles();

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

    public boolean canWriteFile(String fileName, String login){

        FichierProtege fichier = getFile(fileName);

        if(fichier == null){
            return false;
        }

        if(!ControleAcces.estAutorise(login, fichier, 'w')){
            return false;
        }

        return true;
    }

    public boolean changePermission(String fileName, String login, String permission){

        FichierProtege fichier = getFile(fileName);

        if(fichier == null){
            return false;
        }

        if(!fichier.getOwner().equals(login)){

            return false;
        }

        if(!permission.equals("r") &&
            !permission.equals("w") &&
            !permission.equals("d") &&
            !permission.equals("-r") &&
            !permission.equals("-w") &&
            !permission.equals("-d")
        ){
            return false;
        }

        boolean remove = permission.startsWith("-");
        char permi;

        if(remove){
            permi = permission.charAt(1);
        }
        else{
            permi = permission.charAt(0);
        }

        switch (permi) {
            case 'r':
                if(!remove && fichier.getOthersCanRead()){
                    return false;
                }
                if(remove && !fichier.getOthersCanRead()){
                    return false;
                }
                fichier.setOtherCanRead(!remove);
                break;

            case 'w':
                if(!remove && fichier.getOthersCanWrite()){
                    return false;
                }
                if(remove && !fichier.getOthersCanWrite()){
                    return false;
                }
                fichier.setOthersCanWrite(!remove);
                break;
            
            case 'd':
                if(!remove && fichier.getOthersCanDelete()){
                    return false;
                }
                if(remove && !fichier.getOthersCanDelete()){
                    return false;
                }
                fichier.setOthersCanDelete(!remove);
                break;
        
            default:
                break;
        }
        saveFiles();

        return true;
    }
    
    private void saveFiles(){

        StringBuilder data = new StringBuilder();

        for(FichierProtege fichier : files.values()){

            data.append(fichier.getName())
                .append(";")
                .append(fichier.getOwner())
                .append(";")
                .append(fichier.getPermissionsDisplay())
                .append("\n");
        }

        try{
            Files.createDirectories(Path.of("data"));
            Files.writeString(metadataPath, data.toString());
        }
        catch(IOException e){

        }
    }

    private void loadFiles(){
        if(!Files.exists(metadataPath)){
            return;
        }
        List<String> lines;
        try{
            lines = Files.readAllLines(metadataPath);
        }
        catch(IOException e){
            return;
        }

        for(String line : lines){
            String[] parts = line.split(";");

            if(parts.length != 3){
                continue;
            }

            String fileName = parts[0];
            String owner = parts[1];
            String[] permission = parts[2].split("\\|");

            if(permission.length != 2){
                continue;
            }

            String ownerPermission = permission[0];
            String otherPermission = permission[1];

            boolean ownerCanRead = ownerPermission.charAt(0) == 'r';
            boolean ownerCanWrite = ownerPermission.charAt(1) == 'w';
            boolean ownerCanDelete = ownerPermission.charAt(2) == 'd';

            boolean othersCanRead = otherPermission.charAt(0) == 'r';
            boolean othersCanWrite = otherPermission.charAt(1) == 'w';
            boolean othersCanDelete = otherPermission.charAt(2) == 'd';

            FichierProtege fichier = new FichierProtege(fileName, owner, ownerCanRead, ownerCanWrite, ownerCanDelete,
                                                            othersCanRead, othersCanWrite, othersCanDelete
            );

            files.put(fileName, fichier);
        }
    }
}
