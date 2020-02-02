package mail.ru;

import java.util.ArrayList;

public class ExecutionStatisticsClass implements ExecutionStatistics {
    private final ArrayList<Integer> timesList = new ArrayList<>();

    synchronized public void addTime(int time){
        timesList.add(time);
    }

    @Override
    public int getMinExecutionTimeInMs() {
        return timesList.stream().min(Integer::compare).orElse(0);
    }

    @Override
    public int getMaxExecutionTimeInMs() {
        return timesList.stream().max(Integer::compare).orElse(0);
    }

    @Override
    public int getAverageExecutionTimeInMs() {
        return (int) timesList.stream().mapToInt(Integer::intValue).average().orElse(0);
    }
}
