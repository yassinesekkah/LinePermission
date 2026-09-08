package ma.youcode.lineperm.model;

public class File{

    private final String name;
    private String content;
    private final String owner;
    private final String ownerPermissions;
    private String othersPermissions;

    public File(String name, String owner){

        this.name = name;
        this.owner = owner;

        this.ownerPermissions = "rwd";
        this.othersPermissions = "---";
        this.content = "";
    }

    public String getName(){
        return name;
    }

    public String getContent(){
        return content;
    }

    public String getOwner(){
        return owner;
    }

    public String getOwnerPermissions(){
        return ownerPermissions;
    }

    public String getOthersPermissions(){
        return othersPermissions;
    }

    public void setContent(String content){
        this.content = content;
    }

    public void setOthersPermissions(String othersPermissions){
        this.othersPermissions = othersPermissions;
    }

    
}
