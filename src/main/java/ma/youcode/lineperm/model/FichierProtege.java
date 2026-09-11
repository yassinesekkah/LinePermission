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

        this(name, owner, true, true, true, false, false, false);

    }

    public FichierProtege(String name, String owner, boolean ownerCanRead, boolean ownerCanWrite, boolean ownerCanDelete
                            ,boolean othersCanRead, boolean othersCanWrite, boolean otherCanDelete
    ){
        this.name = name;
        this.owner = owner;
        this.ownerCanRead = ownerCanRead;
        this.ownerCanWrite = ownerCanWrite;
        this.ownerCanDelete = ownerCanDelete;
        this.othersCanRead = othersCanRead;
        this.othersCanWrite = othersCanWrite;
        this.othersCanDelete = otherCanDelete;
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
