package mail.ru;

import java.util.Arrays;
import java.util.concurrent.*;

public class ExecutionManagerClass implements ExecutionManager {
    private final ExecutorService executorService;

    public ExecutionManagerClass(int maxThreads) {
        this.executorService = Executors.newFixedThreadPool(maxThreads);
    }

    @Override
    public Context execute(Runnable... tasks) {
        ContextClass contextClass = new ContextClass();
        ExtendRunnable[] extendRunnables = Arrays.stream(tasks)
                .map(s -> new ExtendRunnable(s, contextClass))
                .toArray(ExtendRunnable[]::new);
        Future[] futures = Arrays.stream(extendRunnables).
                map(executorService::submit).
                toArray(Future[]::new);
        contextClass.setFutures(futures);
        executorService.shutdown();
        return contextClass;
    }
}
