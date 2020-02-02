package mail.ru;

import org.junit.Test;

import java.util.concurrent.ExecutionException;

public class ExecutionManagerClassTest {
    private ExecutionManagerImpl executionManager = new ExecutionManagerImpl(2);
    private Runnable runnable1 = () -> {
        System.out.println("runnable1 do");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ignored) {
        }
    };
    private Runnable runnable2 = () -> {
        System.out.println("runnable2 do");
        try {
            Thread.sleep(7000);
        } catch (InterruptedException ignored) {
        }
    };
    private Runnable runnable3 = () -> {
        System.out.println("runnable3 do");
        int i = 1 / 0;
    };

    Runnable runnable4 = () -> {
        System.out.println("finish runnable");
    };

    @Test
    public void statisticTest() {
        Context context = executionManager.execute(runnable1, runnable2, runnable3, runnable2, runnable3, runnable1);
        ExecutionStatistics statistics = context.getStatistics();
        for (int i = 0; i < 9; i++) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ignored) {
            }
            System.out.println("SuccessCount: " + context.getCompletedTaskCount());
            System.out.println("ExceptionCount: " + context.getFailedTaskCount());
            System.out.println("MinStatisticTime: " + statistics.getMinExecutionTimeInMs());
            System.out.println("MaxStatisticTime: " + statistics.getMaxExecutionTimeInMs());
            System.out.println("MeanStatisticTime: " + statistics.getAverageExecutionTimeInMs());

        }
    }

    @Test
    public void interruptTest() {

        Context context = executionManager.execute(runnable1, runnable2, runnable2, runnable1);
        System.out.println("before SuccessCount: " + context.getCompletedTaskCount());
        System.out.println("before ExceptionCount: " + context.getFailedTaskCount());
        System.out.println("before InterruptionCount: " + context.getInterruptedTaskCount());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ignored) {
        }
        context.interrupt();
        System.out.println("after SuccessCount: " + context.getCompletedTaskCount());
        System.out.println("after ExceptionCount: " + context.getFailedTaskCount());
        System.out.println("after InterruptionCount: " + context.getInterruptedTaskCount());
    }

    @Test
    public void terminateTest() throws InterruptedException {
        Context context = executionManager.execute(runnable1, runnable2, runnable2, runnable1);
        System.out.println("before ifFinish: " + context.isFinished());
        context.onFinish(runnable4);
        context.awaitTermination();
        System.out.println("after ifFinish: " + context.isFinished());
    }
}