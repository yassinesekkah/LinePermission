package ma.youcode.lineperm.model;

public class FichierProtege{

    private final String name;
    private final String owner;
    // private final String ownerPermissions;
    // private String othersPermissions;

    private boolean ownerCanRead;
    private boolean ownerCanWrite;
    private boolean ownerCanDelete;

    private boolean othersCanRead;
    private boolean othersCanWrite;
    private boolean othersCanDelete;

    public FichierProtege(String name, String owner){

        this.name = name;
        this.owner = owner;

        ownerCanRead = true;
        ownerCanWrite = true;
        ownerCanDelete = true;

        othersCanRead = false;
        othersCanWrite = false;
        othersCanDelete = false;

    }

    public String getName(){
        return name;
    }

    public String getOwner(){
        return owner;
    }

    public boolean getOwnerCanRead(){
        return ownerCanRead;
    }

    public boolean getOwnerCanWrite(){
        return ownerCanWrite;
    }

    public boolean getOwenerCanDelete(){
        return ownerCanDelete;
    }

    public boolean getOthersCanRead(){
        return othersCanRead;
    }

    public boolean getOthersCanWrite(){
        return othersCanWrite;
    }

    public boolean getOthersCanDelete(){
        return othersCanDelete;
    }

    public void setOtherCanRead(boolean othersCanRead){
        this.othersCanRead = othersCanRead;
    }

    public void setOthersCanWrite(boolean othersCanWrite){
        this.othersCanWrite = othersCanWrite;
    }

    public void setOthersCanDelete(boolean othersCanDelete){
        this.othersCanDelete = othersCanDelete;
    }

    public String getPermissionsDisplay(){

        String ownerPermission = 
            (ownerCanRead ? "r" : "-") + 
            (ownerCanWrite ? "w" : "-") +
            (ownerCanDelete ? "d" : "-");

        String othersPermission = 
            (othersCanRead ? "r" : "-") + 
            (othersCanWrite ? "w" : "-") +
            (othersCanDelete ? "d" : "-"); 

        return ownerPermission + "|" + othersPermission;
    }

    
}
