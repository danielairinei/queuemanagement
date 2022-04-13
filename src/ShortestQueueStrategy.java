import java.util.List;

public class ShortestQueueStrategy implements Strategy {
    @Override
    public synchronized int addTask(List<Server> servers, Task task) throws InterruptedException {
        int minValue = 99999;
        Server minServer = null;
        for (Server newServer : servers) {
            int auxValue = newServer.getQueueSize();
            if (auxValue < minValue) {
                minValue = auxValue;
                minServer = newServer;
            }
        }
        minServer.addTask(task);

        return minServer.getWaitingPeriod().intValue();
    }
}
