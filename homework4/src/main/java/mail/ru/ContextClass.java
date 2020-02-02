package mail.ru;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Future;

public class ContextClass<executorService> implements Context {
    private boolean isInterrupt;
    private int executedCount;
    private int successCount;
    private int exceptionCount;
    private Future[] futures;
    private final ExecutionStatisticsClass statistic = new ExecutionStatisticsClass();

    public void setFutures(Future[] futures) {
        this.futures = futures;
    }

    @Override
    public int getCompletedTaskCount() {
        return successCount;
    }

    @Override
    public int getFailedTaskCount() {
        return exceptionCount;
    }

    @Override
    public int getInterruptedTaskCount() {
        return isInterrupt ? futures.length - executedCount : 0;
    }

    @Override
    public void interrupt() {
        Arrays.stream(futures)
                .forEach(s->s.cancel(false));
        isInterrupt = true;
    }

    @Override
    public boolean isFinished() {
        return executedCount == futures.length;
    }

    @Override
    public void onFinish(Runnable callback)  {
        synchronized (this){
            while (executedCount < futures.length){
                try {
                    this.wait();
                } catch (InterruptedException ignored) { }
            }
            callback.run();
        }
    }

    @Override
    public ExecutionStatistics getStatistics() {
        return statistic;
    }

    @Override
    public void awaitTermination() {
        synchronized (this){
            while (executedCount < futures.length){
                try {
                    this.wait();
                } catch (InterruptedException ignored) { }
            }
        }
    }

    synchronized public void success() {
        this.successCount++;
    }

    synchronized public void exception() {
        this.exceptionCount++;
    }

    synchronized public void executed() {
        this.executedCount++;
        if(this.executedCount == this.futures.length){
            this.notifyAll();
        }
    }
}
