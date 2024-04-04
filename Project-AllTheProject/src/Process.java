/**
 * Represents a process with an ID, arrival time, and burst time.
 * Keeps track of the process's execution and scheduling information.
 *
 * @author Mahmood
 */
public class Process {
    private final int ProcessID;
    private final int ArrivalTime;
    private final int BurstTime;

    private int ResponseTime = -1;
    private int TurnAroundTime = -1;
    private int WaitingTime = 0;

    private boolean Terminate = false;
    private int ExecutedTime = 0;
    private int StartingTime = -1;
    private int FinishingTime;
    private int RemainingTime;

    /**
     * Constructs a new process with the specified ID, arrival time, and burst time.
     *
     * @param ID the process ID
     * @param ArrivalTime the arrival time of the process
     * @param BurstTime the burst (execution) time of the process
     */
    public Process(int ID, int ArrivalTime, int BurstTime){
        ProcessID = ID;
        this.ArrivalTime = ArrivalTime;
        this.BurstTime = BurstTime;
    }

    /**
     * Returns the remaining time (burst time - executed time) of the process.
     *
     * @return the remaining time of the process
     */
    public int getRemainingTime() {return RemainingTime;}

    /**
     * Returns the starting time of the process.
     *
     * @return the starting time of the process
     */
    public int getStartingTime() {return StartingTime;}

    /**
     * Returns the ID of the process.
     *
     * @return the ID of the process
     */
    public int getProcessID() {return ProcessID;}

    /**
     * Returns the arrival time of the process.
     *
     * @return the arrival time of the process
     */
    public int getArrivalTime(){return ArrivalTime;}

    /**
     * Returns the burst time of the process.
     *
     * @return the burst time of the process
     */
    public int getBurstTime() {return BurstTime;}

    /**
     * Returns the executed time of the process.
     *
     * @return the executed time of the process
     */
    public int getExecutedTime() {return ExecutedTime;}

    /**
     * Returns the turn-around time of the process.
     *
     * @return the turn-around time of the process
     */
    public int getTurnAroundTime() {return TurnAroundTime;}

    /**
     * Returns the waiting time of the process.
     *
     * @return the waiting time of the process
     */
    public int getWaitingTime() {return WaitingTime;}

    /**
     * Returns the finishing time of the process.
     *
     * @return the finishing time of the process
     */
    public int getFinishingTime() {return FinishingTime;}

    /**
     * Returns the response time of the process.
     *
     * @return the response time of the process
     */
    public int getResponseTime() {return ResponseTime;}

    /**
     * Sets the response time of the process.
     *
     * @param response_time the response time of the process
     */
    public void setResponse_time(int response_time) {this.ResponseTime = response_time;}

    /**
     * Sets the turn-around time of the process.
     *
     * @param turnAroundTime the turn-around time of the process
     */
    public void setTurnAroundTime(int turnAroundTime) {this.TurnAroundTime = turnAroundTime;}

    /**
     * Sets the waiting time of the process.
     *
     * @param waitingTime the waiting time of the process
     */
    public void setWaitingTime(int waitingTime) {this.WaitingTime = waitingTime;}

    /**
     * Sets the finishing time of the process.
     *
     * @param finishing_time the finishing time of the process
     */
    public void setFinishing_time(int finishing_time) {this.FinishingTime = finishing_time;}

    /**
     * Sets the starting time of the process.
     *
     * @param start the starting time of the process
     */
    public void setStartingTime(int start) {this.StartingTime = start;}

    /**
     * Sets the remaining time (burst time - executed time) of the process.
     */
    public void setRemainingTime() {RemainingTime = getBurstTime() - getExecutedTime();}

    /**
     * Returns a boolean indicating whether the process has terminated (i.e., executed for its entire burst time).
     *
     * @return true if the process has terminated, false otherwise
     */
    public boolean isTerminate() {return Terminate;}

    /**
     * Increments the executed time of the process by 1, and sets the process to terminate if the executed time equals the burst time.
     */
    public void setExecutedTime() {
        ExecutedTime++;
        if (ExecutedTime == getBurstTime()) Terminate = true;
    }
}