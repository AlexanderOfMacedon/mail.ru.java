package mail.ru;

public interface ExecutionManager {
    Context execute(Runnable... tasks);
}
