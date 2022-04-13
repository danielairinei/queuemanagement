import java.util.ArrayList;

public class Scheduler {
    private final ArrayList<Server> servers;
    private Strategy strategy;

    public Scheduler(int maxNoServers, int maxTaskPerServer) {
        servers = new ArrayList<>();
        for (int i = 0; i < maxNoServers; i++) {
            Server server = new Server(maxTaskPerServer);
            servers.add(server);
            Thread t = new Thread(server);
            t.start();
        }
    }

    public void changeStrategy(SelectionPolicy policy) {
        if (policy == SelectionPolicy.SHORTEST_QUEUE) {
            strategy = new ShortestQueueStrategy();
        }
        if (policy == SelectionPolicy.SHORTEST_TIME) {
            strategy = new TimeStrategy();
        }
    }

    public int dispatchTask(Task task) throws InterruptedException {
        return strategy.addTask(servers, task);
    }

    public ArrayList<Server> getServers() {
        return servers;
    }
}
