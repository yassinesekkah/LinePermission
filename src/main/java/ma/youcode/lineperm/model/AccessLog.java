package ma.youcode.lineperm.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class AccessLog {

    private LocalDate date;
    private LocalTime time;
    private String user;
    private ActionTypes action;
    private String fichier;
    private Status state;
    

    public AccessLog(LocalDate date, LocalTime time, String user, ActionTypes action, String fichier, Status state){
        this.date = date;
        this.time = time;
        this.user = user;
        this.action = action;
        this.fichier = fichier;
        this.state = state;
    }

    public LocalDate getDate(){
        return this.date;
    }
    public LocalTime getTime(){
        return this.time;
    }
    public String getUser(){
        return user;
    }
    public ActionTypes getAction(){
        return action;
    }
    public String  getFichier(){
        return fichier;
    }
    public Status getState(){
        return state;
    }
}
