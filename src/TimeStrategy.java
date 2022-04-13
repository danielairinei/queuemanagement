import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class TimeStrategy implements Strategy {
    @Override
    public synchronized int addTask(List<Server> servers, Task task) throws InterruptedException {
        AtomicInteger minValue = new AtomicInteger(9999);
        Server minServer = null;
        for (Server newServer : servers) {
            AtomicInteger auxValue = newServer.getWaitingPeriod();
            if (auxValue.get() < minValue.get()) {
                minValue = auxValue;
                minServer = newServer;
            }
        }
        minServer.addTask(task);

        return minServer.getWaitingPeriod().intValue();
    }
}
