package ma.youcode.lineperm.access;

import ma.youcode.lineperm.model.FichierProtege;
import ma.youcode.lineperm.service.FileService;

public class ControleAcces {

    // FileService fileService = new FileService();
    
    public static boolean estAutorise(String login, FichierProtege fichier, char droit){

        boolean isOwner = login.equals(fichier.getOwner());

        if(isOwner){
            switch (droit){
                case 'r':
                    return fichier.getOwnerCanRead();

                case 'w':
                    return fichier.getOwnerCanWrite();

                case 'd':
                    return fichier.getOwenerCanDelete();

                default:
                    return false;
            }
        }

        switch(droit){
            case 'r':
                return fichier.getOthersCanRead();
            
            case 'w':
                return fichier.getOthersCanWrite();

            case 'd':
                return fichier.getOthersCanDelete();
            
            default:
                return false;
        }
    }
}
