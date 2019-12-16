package mail.ru;

class StatisticsRunnable implements Runnable {
    private final Runnable runnable;
    private final ContextImpl context;

    StatisticsRunnable(Runnable runnable, ContextImpl context) {
        this.runnable = runnable;
        this.context = context;
    }

    public void run() {
        try {
            int startTime = (int) System.currentTimeMillis();
            context.executed();
            runnable.run();
            int endTime = (int) System.currentTimeMillis();
            context.success();
            ExecutionStatisticsImpl statistics = (ExecutionStatisticsImpl) context.getStatistics();
            statistics.addTime(endTime - startTime);
        } catch (Exception e) {
            context.exception();
        }
    }
}
