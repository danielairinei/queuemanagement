import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable {
    private final BlockingQueue<Task> tasks;
    private final AtomicInteger waitingPeriod;
    private int processedTasks = 0;
    private int totalServiceTime = 0;

    public synchronized void addTask(Task newTask) throws InterruptedException {
        tasks.put(newTask);
        waitingPeriod.getAndAdd(newTask.getServiceTime());
    }

    public Server(int taskCapacity) {
        tasks = new ArrayBlockingQueue<>(taskCapacity);
        waitingPeriod = new AtomicInteger(0);
    }

    @Override
    public void run() {
        while (true) {
            Task newTask;
            newTask = tasks.peek();
            if (newTask != null) {
                try {
                    Thread.sleep(100L * newTask.getServiceTime());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                tasks.poll();
                waitingPeriod.getAndAdd(-1* newTask.getServiceTime());
                totalServiceTime += newTask.getServiceTime();
                ++processedTasks;
            }
        }
    }

    public int getTotalServiceTime() {
        return totalServiceTime;
    }

    public int getProcessedTasks() {
        return processedTasks;
    }

    public synchronized AtomicInteger getWaitingPeriod() {
        return waitingPeriod;
    }

    public int getQueueSize() {
        return tasks.size();
    }

    public String toString() {
        String s = "";
        if (tasks.isEmpty()) {
            s += "closed";
            return s;
        }
        s += tasks.toString();
        return s;
    }
}
