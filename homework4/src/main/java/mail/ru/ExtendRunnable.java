package mail.ru;

class ExtendRunnable implements Runnable {
    private final Runnable runnable;
    private final ContextClass context;

    ExtendRunnable(Runnable runnable, ContextClass context) {
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
            ExecutionStatisticsClass statistics = (ExecutionStatisticsClass) context.getStatistics();
            statistics.addTime(endTime - startTime);
        } catch (Exception e) {
            context.exception();
        }
    }
}
