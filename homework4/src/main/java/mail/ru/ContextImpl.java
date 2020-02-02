package mail.ru;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class ContextImpl implements Context {
    private boolean isInterrupt;
    private int executedCount;
    private int successCount;
    private int exceptionCount;
    private CompletableFuture[] futures;
    private final ExecutionStatisticsImpl statistic = new ExecutionStatisticsImpl();

    void setFutures(CompletableFuture[] futures) {
        this.futures = futures;
    }

    @Override
    public synchronized int getCompletedTaskCount() {
        return successCount;
    }

    @Override
    public synchronized int getFailedTaskCount() {
        return exceptionCount;
    }

    @Override
    public synchronized int getInterruptedTaskCount() {
        return isInterrupt ? futures.length - executedCount : 0;
    }

    @Override
    public void interrupt() {
        Arrays.stream(futures)
                .forEach(s -> s.cancel(false));
        isInterrupt = true;
    }

    @Override
    public synchronized boolean isFinished() {
        //в задании написано, когда выполнятся (то есть бее исключений), поэтому второе условие такое
        return executedCount == futures.length && executedCount == successCount + exceptionCount
                + getInterruptedTaskCount();
    }

    @Override
    public synchronized void onFinish(Runnable callback) {
        CompletableFuture end = CompletableFuture.allOf(futures);
        end.thenRun(callback);
    }

    @Override
    public ExecutionStatistics getStatistics() {
        return statistic;
    }

    @Override
    public void awaitTermination() throws InterruptedException {
        synchronized (this) {
            while (this.successCount < this.futures.length) {
                this.wait();
            }
        }
    }

    synchronized void success() {
        this.successCount++;
        if (this.successCount == this.futures.length) {
            this.notifyAll();
        }
    }

    synchronized void exception() {
        this.exceptionCount++;
    }

    synchronized void executed() {
        this.executedCount++;
    }
}
