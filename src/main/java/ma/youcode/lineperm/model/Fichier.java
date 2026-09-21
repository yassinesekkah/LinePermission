    package ma.youcode.lineperm.model;

    public class Fichier{

        private int id;

        private final String name;
        private final User owner;
        // private final String ownerPermissions;
        // private String othersPermissions;

        private boolean ownerCanRead;
        private boolean ownerCanWrite;
        private boolean ownerCanDelete;

        private boolean othersCanRead;
        private boolean othersCanWrite;
        private boolean othersCanDelete;

        public Fichier(String name, User owner){

            this(name, owner, true, true, true, false, false, false);

        }

        public Fichier(String name, User owner, boolean ownerCanRead, boolean ownerCanWrite, boolean ownerCanDelete
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

        public Fichier(int id, String name, User owner, boolean ownerCanRead, boolean ownerCanWrite, boolean ownerCanDelete
                                ,boolean othersCanRead, boolean othersCanWrite, boolean otherCanDelete
        ){
            this(name, owner, ownerCanRead, ownerCanWrite, ownerCanDelete, othersCanRead, othersCanWrite, otherCanDelete);
            this.id = id;
        }

        public int getId(){
            return id;
        }

        public String getName(){
            return name;
        }

        public User getOwner(){
            return owner;
        }

        public boolean getOwnerCanRead(){
            return ownerCanRead;
        }

        public boolean getOwnerCanWrite(){
            return ownerCanWrite;
        }

        public boolean getOwnerCanDelete(){
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

        public void setOthersCanRead(boolean othersCanRead){
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
