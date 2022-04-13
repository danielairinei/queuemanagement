public class Task implements Comparable<Task>{
    private final int ID;
    private final Integer arrivalTime;
    private final int serviceTime;

    public Task(int ID, int arrivalTime, int serviceTime) {
        this.ID = ID;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    @Override
    public int compareTo(Task newTask) {
        return arrivalTime.compareTo(newTask.arrivalTime);
    }

    public String toString() {
        return "(" + "ID: " + ID + ", " + "A: " + arrivalTime + ", " + "S: " + serviceTime + ")";
    }
}
