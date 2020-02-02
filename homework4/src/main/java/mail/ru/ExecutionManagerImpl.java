package mail.ru;

import java.util.Arrays;
import java.util.concurrent.*;

public class ExecutionManagerImpl implements ExecutionManager {
    private final ExecutorService executorService;

    public ExecutionManagerImpl(int maxThreads) {
        this.executorService = Executors.newFixedThreadPool(maxThreads);
    }

    @Override
    public Context execute(Runnable... tasks) {
        ContextImpl contextClass = new ContextImpl();
        StatisticsRunnable[] statisticsRunnables = Arrays.stream(tasks)
                .map(s -> new StatisticsRunnable(s, contextClass))
                .toArray(StatisticsRunnable[]::new);
//        Future[] futures = Arrays.stream(extendRunnables).
//                map(executorService::submit).
//                toArray(Future[]::new);
        CompletableFuture[] futures= Arrays.stream(statisticsRunnables)
                .map(s->CompletableFuture.runAsync(s, executorService))
                .toArray(CompletableFuture[]::new);
        contextClass.setFutures(futures);
        executorService.shutdown();
        return contextClass;
    }
}
