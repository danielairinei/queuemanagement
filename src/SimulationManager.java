import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class SimulationManager implements Runnable, ActionListener {
    public int timeLimit;
    public int maxProcessingTime;
    public int minProcessingTime;
    public int minArrivalTime;
    public int maxArrivalTime;
    public int numberOfServers;
    public int numberOfClients;
    public int peakHourSize = 0;
    public int peakHour = 0;
    public SelectionPolicy selectionPolicy = SelectionPolicy.SHORTEST_TIME;

    private Scheduler scheduler;
    private final SimulationFrame frame;
    private List<Task> generatedTasks;
    private final List<Task> removeTasks = new ArrayList<>();

    public SimulationManager() {
        frame = new SimulationFrame();
        frame.startButton.addActionListener(this);
        frame.setVisible(true);
    }

    public void generateNRandomTasks() {
        generatedTasks = new ArrayList<>();
        for (int i = 0; i < numberOfClients; i++) {
            int randomProcessingTime = ThreadLocalRandom.current().nextInt(minProcessingTime, maxProcessingTime + 1);
            int randomArrivalTime = ThreadLocalRandom.current().nextInt(minArrivalTime, maxArrivalTime);
            Task temporaryTask = new Task(i + 1, randomArrivalTime, randomProcessingTime);
            generatedTasks.add(temporaryTask);
        }
        Collections.sort(generatedTasks);
    }

    @Override
    public void run() {
        generateNRandomTasks();
        String output;
        int currentTime = 1, tasksAdded = 0, totalWaitingTime = 0, totalServiceTime = 0, totalProcessedTasks = 0;
        FileWriter fileResult = null;
        try {
            fileResult = new FileWriter("results.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        scheduler = new Scheduler(numberOfServers, numberOfClients);
        scheduler.changeStrategy(selectionPolicy);
        while (currentTime <= timeLimit) {
            int totalQueuesSize = 0;
            for (Task element : generatedTasks) {
                if (element.getArrivalTime() == currentTime) {
                    try {
                        totalWaitingTime += scheduler.dispatchTask(element) + element.getServiceTime();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    ++tasksAdded;
                    removeTasks.add(element);
                }
            }
            for (Task element : removeTasks) {
                generatedTasks.remove(element);
            }
            for (int i = 0; i < this.numberOfServers; i++) {
                totalQueuesSize += scheduler.getServers().get(i).getQueueSize();
                totalServiceTime += scheduler.getServers().get(i).getTotalServiceTime();
                totalProcessedTasks += scheduler.getServers().get(i).getProcessedTasks();
            }
            if (totalQueuesSize > peakHourSize) {
                peakHourSize = totalQueuesSize;
                peakHour = currentTime;
            }
            output = "Time " + currentTime + "\n";
            output += toString();
            frame.textArea.setText(frame.textArea.getText() + output);
            currentTime++;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        String stats = "Average service time is : " + totalServiceTime / totalProcessedTasks + "\n";
        stats += "Peak hour is : " + peakHour + "\n";
        stats += "Average waiting time is : " + totalWaitingTime / tasksAdded + "\n";
        frame.textArea.setText(frame.textArea.getText() + stats);
        try {
            assert fileResult != null;
            fileResult.write(frame.textArea.getText());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fileResult.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent e) {
        Initialize();
        dataParse();
        Thread thread = new Thread(this);
        thread.start();
    }

    private void dataParse() {
        if (frame.newSelection == SelectionPolicy.SHORTEST_QUEUE) {
            selectionPolicy = SelectionPolicy.SHORTEST_QUEUE;
        }
        if (frame.newSelection == SelectionPolicy.SHORTEST_TIME) {
            selectionPolicy = SelectionPolicy.SHORTEST_TIME;
        }
        maxArrivalTime = Integer.parseInt(frame.maximumArrivalTimeText.getText());
        minArrivalTime = Integer.parseInt(frame.minimumArrivalTimeText.getText());
        maxProcessingTime = Integer.parseInt(frame.maximumServiceTimeText.getText());
        minProcessingTime = Integer.parseInt(frame.minimumServiceTimeText.getText());
        timeLimit = Integer.parseInt(frame.simulationTimeText.getText());
        numberOfServers = Integer.parseInt(frame.noOfServersText.getText());
        numberOfClients = Integer.parseInt(frame.noOfClientsText.getText());
    }

    private void Initialize() {
        frame.timeStrategyButton(e -> frame.chooseStrategy("TimeStrategy"));
        frame.shortestQueueStrategyButton(e -> frame.chooseStrategy("ShortestQueue"));
    }

    public static void main(String[] args) {
        new SimulationManager();
    }

    public String toString() {
        StringBuilder s = new StringBuilder("Waiting clients :   " + generatedTasks.toString() + "\n");
        for (int i = 0; i < numberOfServers; i++) {
            s.append("Queue ").append(i).append(": ").append(scheduler.getServers().get(i).toString()).append("\n");
        }
        s.append("\n");
        return s.toString();
    }
}
