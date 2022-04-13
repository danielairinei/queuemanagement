import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.Font;
import java.util.*;
import java.awt.event.*;
import java.util.List;

//TODO
public class SimulationFrame extends JFrame {

    List<JLabel> labelList = new ArrayList<>();
    List<JTextField> inputList = new ArrayList<>();
    JButton timeStrategyButton, shortestQueueStrategyButton, startButton;
    public SelectionPolicy newSelection;
    JTextField simulationTimeText, noOfClientsText, noOfServersText, maximumArrivalTimeText, minimumArrivalTimeText, maximumServiceTimeText, minimumServiceTimeText;
    JLabel simulationTime, noOfClients, noOfServers, maximumArrivalTime, minimumArrivalTime, maximumServiceTime, minimumServiceTime;
    protected JPanel bottomPanel = new JPanel();
    protected JTextArea textArea = new JTextArea();
    protected JScrollPane scrollPane = new JScrollPane(textArea);

    public SimulationFrame() {
        //Buttons declaration
        Color myColor = new Color(229, 228, 231);

        //define size of the main window
        this.setBounds(50, 50, 700, 610);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);
        setTitle("Queue Manangement");

        {
            timeStrategyButton = new JButton("Time Strategy");
            timeStrategyButton.setBounds(350, 80, 120, 50);
            getContentPane().add(timeStrategyButton);
            timeStrategyButton.setFont(new Font("Bahnschrift", Font.PLAIN, 15));
            timeStrategyButton.setMargin(new Insets(0, 0, 0, 0));
            timeStrategyButton.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
            timeStrategyButton.setBackground(myColor);
            timeStrategyButton.setFocusPainted(false);
            shortestQueueStrategyButton = new JButton("<html><style>p{text-align :center;}</style><p>Shortest Queue<br />Strategy</p></html>");
            shortestQueueStrategyButton.setBounds(480, 80, 120, 50);
            getContentPane().add(shortestQueueStrategyButton);
            shortestQueueStrategyButton.setFont(new Font("Bahnschrift", Font.PLAIN, 15));
            shortestQueueStrategyButton.setMargin(new Insets(0, 0, 0, 0));
            shortestQueueStrategyButton.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
            shortestQueueStrategyButton.setBackground(myColor);
            shortestQueueStrategyButton.setFocusPainted(false);
            startButton = new JButton("Start Simulation");
            startButton.setBounds(350, 140, 250, 50);
            getContentPane().add(startButton);
            startButton.setFont(new Font("Bahnschrift", Font.PLAIN, 15));
            startButton.setMargin(new Insets(0, 0, 0, 0));
            startButton.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
            startButton.setBackground(myColor);
            startButton.setFocusPainted(false);

        }
        //Label creation
        {
            simulationTime = new JLabel("Simulation Time = ");
            simulationTime.setBounds(20, 5, 100, 30);
            getContentPane().add(simulationTime);
            labelList.add(simulationTime);
            noOfClients = new JLabel("Number of Clients = ");
            noOfClients.setBounds(20, 35, 100, 30);
            getContentPane().add(noOfClients);
            labelList.add(noOfClients);
            noOfServers = new JLabel("Number of Servers = ");
            noOfServers.setBounds(20, 65, 100, 30);
            getContentPane().add(noOfServers);
            labelList.add(noOfServers);
            minimumArrivalTime = new JLabel("Minimum Arrival Time = ");
            minimumArrivalTime.setBounds(20, 95, 100, 30);
            getContentPane().add(minimumArrivalTime);
            labelList.add(minimumArrivalTime);
            maximumArrivalTime = new JLabel("Maximum Arrival Time = ");
            maximumArrivalTime.setBounds(20, 125, 100, 30);
            getContentPane().add(maximumArrivalTime);
            labelList.add(maximumArrivalTime);
            minimumServiceTime = new JLabel("Minimum Service Time = ");
            minimumServiceTime.setBounds(20, 155, 100, 30);
            getContentPane().add(minimumServiceTime);
            labelList.add(minimumServiceTime);
            maximumServiceTime = new JLabel("Maximum Service Time = ");
            maximumServiceTime.setBounds(20, 185, 100, 30);
            getContentPane().add(maximumServiceTime);
            labelList.add(maximumServiceTime);

        }

        //TextField creation
        {
            simulationTimeText = new JTextField("");
            simulationTimeText.setBounds(145, 28, 0, 0);
            getContentPane().add(simulationTimeText);
            inputList.add(simulationTimeText);
            noOfClientsText = new JTextField("");
            noOfClientsText.setBounds(160, 60, 0, 0);
            getContentPane().add(noOfClientsText);
            inputList.add(noOfClientsText);
            noOfServersText = new JTextField("");
            noOfServersText.setBounds(165, 90, 0, 0);
            getContentPane().add(noOfServersText);
            inputList.add(noOfServersText);
            minimumArrivalTimeText = new JTextField("");
            minimumArrivalTimeText.setBounds(185, 120, 0, 0);
            getContentPane().add(minimumArrivalTimeText);
            inputList.add(minimumArrivalTimeText);
            maximumArrivalTimeText = new JTextField("");
            maximumArrivalTimeText.setBounds(190, 150, 0, 0);
            getContentPane().add(maximumArrivalTimeText);
            inputList.add(maximumArrivalTimeText);
            minimumServiceTimeText = new JTextField("");
            minimumServiceTimeText.setBounds(190, 180, 0, 0);
            getContentPane().add(minimumServiceTimeText);
            inputList.add(minimumServiceTimeText);
            maximumServiceTimeText = new JTextField("");
            maximumServiceTimeText.setBounds(190, 210, 0, 0);
            getContentPane().add(maximumServiceTimeText);
            inputList.add(maximumServiceTimeText);
        }
        //Label edit
        {

            for (JLabel x : labelList) {
                int newX = x.getX() - 10;
                x.setBounds(newX, x.getY(), 250, 70);
                x.setFont(new Font("Bahnschrift", Font.PLAIN, 15));
                x.setBorder(BorderFactory.createEmptyBorder(15, 10, 10, 10));
                x.setBackground(myColor);
            }
        }

        //TextField edit
        {
            for (JTextField x : inputList) {
                x.setBounds(x.getX(), x.getY(), 70, 25);
                x.setFont(new Font("Bahnschrift", Font.PLAIN, 17));
                x.setBackground(myColor);
                x.setMargin(new Insets(0, 0, 0, 0));
                x.setBorder(BorderFactory.createEmptyBorder(0, 10, -7, 0));
                x.setCaretColor(myColor);
            }
        }
        bottomPanel.setBorder(new TitledBorder(new EtchedBorder(), "Display Area"));
        textArea.setEditable(false);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        textArea.setFont(new Font("Bahnschrift", Font.PLAIN, 14));
        scrollPane.setBounds(15, 250, 660, 320);
        bottomPanel.add(scrollPane);
        getContentPane().add(scrollPane);

    }

    public void timeStrategyButton(final ActionListener actionListener) {
        timeStrategyButton.addActionListener(actionListener);
    }

    public void shortestQueueStrategyButton(final ActionListener actionListener) {
        shortestQueueStrategyButton.addActionListener(actionListener);
    }

    public void chooseStrategy(String chosenStrategy) {
        if (Objects.equals(chosenStrategy, "TimeStrategy")) {
            newSelection = SelectionPolicy.SHORTEST_TIME;
        }
        if (Objects.equals(chosenStrategy, "ShortestQueue")) {
            newSelection = SelectionPolicy.SHORTEST_QUEUE;
        }
    }
}
