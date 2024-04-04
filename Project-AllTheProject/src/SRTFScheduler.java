import java.util.*;
/**
 * Implements the Shortest Remaining Time First (SRTF) scheduling algorithm for a set of processes.
 *
 * @author waleed
 */
public class SRTFScheduler {
    private Process[] ReadyQueue;               // Array called ReadyQueue of type Process that store all process
    private final Set<Integer> processIDs;      // Hash Set called ProcessIDs of type Integer that store all process ID's
    private int NumOfProcess;                   // Number of process in the ReadyQueue
    public double AverageTurnAroundTime, AverageWaitingTime, AverageResponseTime;   // Three variable to calculated average times

    /**
     * Constructs a new SRTF (Shortest Remaining Time First) scheduler with an empty ready queue
     * and no processes. The ready queue is initially allocated with space one process.
     * Processes can be added to the scheduler using the AddProcess() method.
     */
    public SRTFScheduler() {
        ReadyQueue = new Process[10];
        processIDs = new HashSet<>();
        NumOfProcess = 0;
    }

    /**
     * Re-sorts the ready queue based on process arrival time, using a simple selection sort algorithm.
     */
    public void ReSortBasedOnArriveTime() {
        Process temp1;
        for (int i = 0; i < NumOfProcess; i++)
            for (int j = i+1; j < NumOfProcess; j++)
                if (ReadyQueue[i].getArrivalTime() > ReadyQueue[j].getArrivalTime()){
                    temp1 = ReadyQueue[i];
                    ReadyQueue[i]= ReadyQueue[j];
                    ReadyQueue[j]=temp1;
                }
    }

    /**
     * Re-sorts the ready queue based on process ID, using a simple selection sort algorithm.
     */
    public void ReSortBasedOnProcessID() {
        Process temp1;
        for (int i = 0; i < NumOfProcess; i++)
            for (int j = i+1; j < NumOfProcess; j++)
                if (ReadyQueue[i].getProcessID() > ReadyQueue[j].getProcessID()){
                    temp1 = ReadyQueue[i];
                    ReadyQueue[i]= ReadyQueue[j];
                    ReadyQueue[j]=temp1;
                }
    }

    /**
     * Re-sorts the given list of processes based on remaining time, using a simple bubble sort algorithm.
     *
     * @param list the list of processes to be sorted
     */
    public void ReSortBasedOnRemainingTIme(ArrayList<Process> list) {
        int n = list.size();
        for (int i = 0; i < n - 1; i++)
            for (int j = 0; j < n - i - 1; j++)
                if (list.get(j).getRemainingTime() > list.get(j + 1).getRemainingTime()) {
                    Process temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
        }
    }

    /**
     * Checks if a given process ID is valid (i.e., not already in the ready queue).
     *
     * @param id the process ID to check
     * @return true if the given ID is valid, false otherwise
     */
    public boolean ValidID(int id) {
        return !processIDs.contains(id);
    }

    /**
     * Returns true if the ready queue is empty (i.e., there are no processes in the queue), false otherwise.
     *
     * @return true if the ready queue is empty, false otherwise.
     */
    public boolean isEmpty(){
        return NumOfProcess == 0;
    }

    /**
     * Adds a new process to the ready queue with the given ID, arrival time, and burst time.
     *
     * @param id the ID of the new process
     * @param at the arrival time of the new process
     * @param bt the burst time of the new process
     */
    public void AddProcess(int id, int at, int bt) {
        if (ReadyQueue.length==NumOfProcess)
            ReadyQueue = Arrays.copyOf(ReadyQueue,ReadyQueue.length*2);

        ReadyQueue[NumOfProcess] = new Process(id, at, bt);
        NumOfProcess++;
        processIDs.add(id);
    }

    /**
     * Implements the Shortest Remaining Time First (SRTF) scheduling algorithm.
     *
     * @return true if the scheduling algorithm was successful, false otherwise
     */
    public boolean SchedulingAlgorithm() {
        if (NumOfProcess <= 0) return false;

        ArrayList<Process> WaitingArray = new ArrayList<>(NumOfProcess);
        ReSortBasedOnArriveTime();

        int TotalTime = 0;
        for (int i=0; i<NumOfProcess; i++)
            TotalTime += ReadyQueue[i].getBurstTime();

        int CurrentProcessID = -1, NewProcessID = -1 , counter=0;
        for (int CurrentTime = 0; CurrentTime < TotalTime; CurrentTime++) {
            // Add new processes to the waiting array based on:
            // 1. arrival time for the process is less than or equal CurrentTime
            // 2. process not terminate yet
            for (int i=0; i<NumOfProcess; i++) {
                if (ReadyQueue[i].getArrivalTime() <= CurrentTime && !ReadyQueue[i].isTerminate())
                    if (ReadyQueue[i].getRemainingTime() == 0 && ReadyQueue[i].getExecutedTime()==0)
                        WaitingArray.add(ReadyQueue[i]);
            }
            // Update the remaining time of waiting processes
            for (Process Pro: WaitingArray)
                Pro.setRemainingTime();

            // If a process has finished execution, move it from the waiting array to the ready queue
            if (!WaitingArray.isEmpty() && WaitingArray.get(0).getRemainingTime()==0){
                ReadyQueue[counter]= WaitingArray.remove(0);
                counter++;
            }

            // Sort the waiting array based on remaining time
            ReSortBasedOnRemainingTIme(WaitingArray);

            // Determine the next process to execute
            if(WaitingArray.size() > 0){
                NewProcessID = WaitingArray.get(0).getProcessID();
            }

            // Execute the next process
            if (NewProcessID == -1) {
                System.out.print(CurrentTime + " | NP | ");
                TotalTime++;
                NewProcessID =-10;
            }
            else if (NewProcessID == -10){
                TotalTime++;
            }
            else if (NewProcessID >= 0 && NewProcessID != CurrentProcessID) {
                System.out.print(CurrentTime + " | P" + NewProcessID + " | ");
                CurrentProcessID = NewProcessID;
                WaitingArray.get(0).setExecutedTime();
                if (WaitingArray.get(0).getStartingTime() == -1) {
                    WaitingArray.get(0).setStartingTime(CurrentTime);
                }
            }
            else if (CurrentProcessID == NewProcessID) {
                WaitingArray.get(0).setExecutedTime();
            }

            // If a process has finished execution, update its finishing time and mark it as terminated
            if (NewProcessID >= 0 && WaitingArray.get(0).isTerminate()){
                WaitingArray.get(0).setFinishing_time((CurrentTime) + 1);
                NewProcessID = -1;
            }

            // If all processes have finished execution, calculate the average turnaround time, waiting time, and response time
            if (CurrentTime + 1 == TotalTime) {
                ReadyQueue[counter]= WaitingArray.remove(0);
                System.out.println(TotalTime);
                ReSortBasedOnProcessID();
                for (int i=0; i<NumOfProcess; i++) {
                    ReadyQueue[i].setResponse_time(ReadyQueue[i].getStartingTime() - ReadyQueue[i].getArrivalTime());
                    ReadyQueue[i].setTurnAroundTime(ReadyQueue[i].getFinishingTime() - ReadyQueue[i].getArrivalTime());
                    ReadyQueue[i].setWaitingTime(ReadyQueue[i].getTurnAroundTime() - ReadyQueue[i].getBurstTime());
                }
            }

        }
        return true;
    }

    /**
     This method calculates and prints the average turnaround time, response time, and waiting time for all processes in the ReadyQueue.
     The average response time, turnaround time, and waiting time are calculated by dividing the total values by the total number of processes.
     The calculated average values are rounded to three decimal places using the Math.round() method.
     The method then generates a formatted string containing the calculated average values and prints it to the console.
     */
    public void PrintProcessesAverages() {
        if (NumOfProcess <= 0) return;
        double WaitingTime = 0, TurnAroundTime = 0, ResponseTime = 0;
        for (int i=0; i<NumOfProcess; i++) {
            WaitingTime += ReadyQueue[i].getWaitingTime();
            TurnAroundTime += ReadyQueue[i].getTurnAroundTime();
            ResponseTime += ReadyQueue[i].getResponseTime();
        }

        AverageTurnAroundTime = TurnAroundTime / NumOfProcess;
        AverageWaitingTime = WaitingTime / NumOfProcess;
        AverageResponseTime = ResponseTime / NumOfProcess;
        AverageResponseTime = Math.round(AverageResponseTime * 1000.0) / 1000.0;
        AverageTurnAroundTime = Math.round(AverageTurnAroundTime * 1000.0) / 1000.0;
        AverageWaitingTime = Math.round(AverageWaitingTime * 1000.0) / 1000.0;

        String Print = "\n\n======================================================================\n" +
                "=                  Average Times For All Process:                    =\n" +
                "======================================================================\n" +
                String.format("%-2s %-2s %-40s %-2s\n",
                        "=", "Average Turnaround Time:", AverageTurnAroundTime + " ms", "=") +
                String.format("%-2s %-2s %-40s %-2s\n",
                        "=", "Average Response Time  :", AverageResponseTime + " ms", "=") +
                String.format("%-2s %-2s %-40s %-2s\n",
                        "=", "Average Waiting Time   :", AverageWaitingTime + " ms", "=") +
                "======================================================================\n";
        System.out.print(Print);
    }

    /**
     This method prints the details of all processes in the ReadyQueue to the console.
     The method iterates through each process in the ReadyQueue and generates a formatted
     string containing the process ID, waiting time, turnaround time, and response time for each process.
     */
    public void PrintProcessesDetails() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n\n======================================================================\n");
        sb.append("=                   The Details For All Processes                    =\n");
        sb.append("======================================================================\n");
        sb.append(String.format("%-2s %-11s %-2s %-13s %-2s %-16s %-2s %-2s %-1s\n",
                "=","Process ID","=","Waiting Time","=","Turnaround Time","=", "Response Time",'='));
        sb.append("======================================================================\n");
        for (int i=0; i<NumOfProcess; i++) {
            sb.append(String.format(
                            "%-6s %-7s %-7s %-8s %-9s %-9s %-8s %-7s %-1s\n",
                            "=", ReadyQueue[i].getProcessID(),       "=",
                            ReadyQueue[i].getWaitingTime()+" ms",    "=",
                            ReadyQueue[i].getTurnAroundTime()+" ms", "=",
                            ReadyQueue[i].getResponseTime()+" ms",   "="));
        }
        sb.append("======================================================================\n");
        System.out.print(sb);
    }

     /**
      This method is responsible for printing the welcome screen of the SRTF Scheduler program to the console.
      The welcome screen consists of a title, section, and a list of names prepared by the team.
     */
    public static void printWelcomeScreen() {
        final String TITLE = "Project ITCS325: SRTF Scheduler";
        final String SECTION = "Section: 1";
        final String []  NAMES = {
                " Prepared by:",
                " Name: Waleed  Saleh Ali Saleh              ID: 202006448",
                " Name: Mahmood Husain Ebrahim               ID: 202008990",
                " Name: Mohammed Hussain Mahdi               ID: 20194444 "
        };
        int z = 0;
        int numRows = 10;
        int numCols = 60;
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                if (i==0 || i==2 || i==numRows-3 || i==numRows-1)
                    System.out.print('=');
                else if(i==1 && j==59) {
                    System.out.print("=\t\t\t  "+TITLE+"\t\t\t   =");
                }
                else if(i==8 && j==59){
                    System.out.print("=                        "+SECTION+"                        =");
                }
                else if (i>2 && i<7 && j==59){
                    System.out.print("="+NAMES[z]);
                    z++;
                    if (i==3)
                        System.out.print("                                             ");
                    else{
                        System.out.print(" ");
                    }
                    System.out.print("=");
                }
            }
            System.out.println();
        }
    }
}