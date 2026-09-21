package ma.youcode.lineperm.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Log {

    private int id;
    private final LocalDate date;
    private final LocalTime time;
    private final User user;
    private final ActionTypes action;
    private final Fichier fichier;
    private final Status state;
    

    public Log(LocalDate date, LocalTime time, User user, ActionTypes action, Fichier fichier, Status state){
        this.date = date;
        this.time = time;
        this.user = user;
        this.action = action;
        this.fichier = fichier;
        this.state = state;
    }

    public Log(int id, LocalDate date, LocalTime time, User user, ActionTypes action, Fichier fichier, Status state){
        this(date, time, user, action, fichier, state);
        this.id = id;
    }

    public int getId(){
        return id;
    }
    public LocalDate getDate(){
        return this.date;
    }
    public LocalTime getTime(){
        return this.time;
    }
    public User getUser(){
        return user;
    }
    public ActionTypes getAction(){
        return action;
    }
    public Fichier  getFichier(){
        return fichier;
    }
    public Status getState(){
        return state;
    }
}
