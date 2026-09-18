package ma.youcode.lineperm.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import ma.youcode.lineperm.model.AccessLog;
import ma.youcode.lineperm.model.ActionTypes;
import ma.youcode.lineperm.model.Status;

public class LogService {

    private List<AccessLog> logs = new ArrayList<>();

    public LogService(){
        loadLogs();
    }

    public List<AccessLog> getLogs(){
        return logs;
    }

    public String buildLogLine(String user, String fichier, ActionTypes action, Status status) {

        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String time = LocalTime.now().format(formatter);

        StringBuilder line = new StringBuilder();

        line.append(date);
        line.append(";");
        line.append(time);
        line.append(";");
        line.append(user);
        line.append(";");
        line.append(action);
        line.append(";");
        line.append(fichier);
        line.append(";");
        line.append(status);

        return line.toString();
    }

    public boolean writeLog(String line) {

        Path path = Path.of("access.log");

        try {
            Files.writeString(
                    path,
                    line + "\n",
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND);

            return true;

        } catch (IOException e) {
            return false;
        }
    }

    public boolean logAction(String user, String fichier, ActionTypes action, Status status) {

        String line = buildLogLine(user, fichier, action, status);

        return writeLog(line);
    }

    public Optional<AccessLog> parseLine(String line) {

        String[] parts = line.split(";");

        if (parts.length != 6) {
            return Optional.empty();
        }

        try {

            // date
            String strDate = parts[0];
            LocalDate date = LocalDate.parse(strDate);

            // time
            String strTime = parts[1];
            LocalTime time = LocalTime.parse(strTime);

            // user
            String user = parts[2];

            // actionType enum
            String strAction = parts[3];
            ActionTypes actionType = ActionTypes.valueOf(strAction);

            // fichier
            String fichier = parts[4];

            // status enum
            String strState = parts[5];
            Status state = Status.valueOf(strState);

            // cration d'objet
            AccessLog accessLog = new AccessLog(date, time, user, actionType, fichier, state);

            return Optional.of(accessLog);

        } catch (Exception e) {
            return Optional.empty();
        }

    }

    public List<AccessLog> loadLogs(){

        try{
            Path accessPath = Path.of("access.log");
            List<String> lines = Files.readAllLines(accessPath);

            List<AccessLog> loadedLogs = lines.stream()
                .map(line -> parseLine(line))
                .filter(optional -> optional.isPresent())
                .map(optional -> optional.get())
                .toList();

            logs = loadedLogs;

            return logs;
            
        }
        catch(IOException e){
            logs = new ArrayList<>();
            return logs;
        }
    }


}
