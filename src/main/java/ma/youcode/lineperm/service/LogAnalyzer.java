package ma.youcode.lineperm.service;

import java.util.List;

import ma.youcode.lineperm.model.AccessLog;

public class LogAnalyzer {

    private List<AccessLog> logs;

    public LogAnalyzer(List<AccessLog> logs){
        this.logs = logs;
    }

}
