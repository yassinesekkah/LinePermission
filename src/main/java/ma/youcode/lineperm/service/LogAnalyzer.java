package ma.youcode.lineperm.service;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import ma.youcode.lineperm.model.AccessLog;
import ma.youcode.lineperm.model.ActionTypes;
import ma.youcode.lineperm.model.Status;

public class LogAnalyzer {

    private List<AccessLog> logs;

    public LogAnalyzer(List<AccessLog> logs) {
        this.logs = logs;
    }

    public long countTotalActions() {
        return logs.stream()
                .count();
    }

    public long countRefusedAccess() {

        return logs.stream()
                .filter(log -> log.getState() == Status.REFUSE)
                .count();
    }

    public long countDistinctUsers() {

        return logs.stream()
                .map(log -> log.getUser())
                .distinct()
                .count();
    }

    public Map<String, Long> countActionsByUser() {

        return logs.stream()
                .collect(Collectors.groupingBy(
                        log -> log.getUser(),
                        Collectors.counting()));
    }

    public Map<String, Long> getTop3ConsultedFiles() {

        Comparator<Map.Entry<String, Long>> comparator = Map.Entry.comparingByValue();

        return logs.stream()
                .collect(Collectors.groupingBy(
                        log -> log.getFichier(),
                        Collectors.counting()))
                .entrySet()
                .stream()
                .sorted(comparator.reversed())
                .limit(3)
                .collect(Collectors.toMap(
                        entry -> entry.getKey(),
                        entry -> entry.getValue(),
                        (e1, e2) -> e1,
                        LinkedHashMap::new));

    }

    public List<AccessLog> getRefusedAccessByUser(String name) {

        return logs.stream()
                .filter(log -> log.getUser().equals(name))
                .filter(log -> log.getState() == Status.REFUSE)
                .toList();
    }

    public Optional<String> getMostActiveUser() {

        Map<String, Long> first = logs.stream()
                .collect(Collectors.groupingBy(
                        log -> log.getUser(),
                        Collectors.counting()));

        return first.entrySet().stream()
                    .max(Map.Entry.comparingByValue())
                    .map(entry -> entry.getKey());

    }

    public Map<ActionTypes, Long> countActionByType(){

        return logs.stream()
                .collect(Collectors.groupingBy(
                    log -> log.getAction(),
                    Collectors.counting()
                ));
    }

}
