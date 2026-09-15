package ma.youcode.lineperm.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import ma.youcode.lineperm.model.ActionTypes;
import ma.youcode.lineperm.model.Status;

public class LogService {

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

    public boolean logAction(String user, String fichier, ActionTypes action, Status status){

        String line = buildLogLine(user, fichier, action, status);

        return writeLog(line);
    }

}
