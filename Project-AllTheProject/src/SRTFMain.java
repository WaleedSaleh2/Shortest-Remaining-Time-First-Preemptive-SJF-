/*
 Name: Waleed  Saleh Ali Saleh              ID: 202006448
 Name: Mahmood Husain Ebrahim               ID: 202008990
 Name: Mohammed Hussain Mahdi               ID: 20194444
 Sec: 1
 */
import java.util.*;
/**
 * SRTFMain class contains the main method to run the SRTF (Shortest Remaining Time First) scheduling algorithm.
 *
 * @author Mohammed
 */
public class SRTFMain {
    /**
     * The main method is the entry point of the program that runs the SRTF (Shortest Remaining Time First) scheduling
     * algorithm. It takes input from the user about the process IDs, arrival times, and burst times and then prints a
     * Gantt chart to show the execution sequence of the processes. It also displays the process details and averages
     * after the scheduling is completed.
     *
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SRTFScheduler.printWelcomeScreen();

        String ScheduleAgain;
        do {
            SRTFScheduler scheduler = new SRTFScheduler();
            int inputCounter = 0;
            System.out.println("\nEnter the Information About Processes: (Enter '0 0 0' To Exit)");
            System.out.println("ID AT BT");

            while (true) {
                inputCounter++;
                int id, arrivalTime, burstTime;
                try {
                    id = scanner.nextInt();
                    arrivalTime = scanner.nextInt();
                    burstTime = scanner.nextInt();
                } catch (InputMismatchException e) {
                    System.out.printf("Error at input No. %d: Invalid input format.\n", inputCounter);
                    scanner.nextLine(); // consume the invalid input to avoid an infinite loop
                    System.out.println("Enter the Information About Processes: (Enter '0 0 0' to Exit)");
                    System.out.println("ID AT BT");
                    continue; // go back to the beginning of the loop to read the next input
                }

                if ((id + arrivalTime + burstTime) == 0 && inputCounter == 1) {
                    break;
                } else if ((id + arrivalTime + burstTime) == 0) {
                    break;
                }

                boolean validID = scheduler.ValidID(id);
                boolean isValidInput = arrivalTime >= 0 && burstTime > 0;

                if (!validID || !isValidInput) {
                    System.out.printf("Error at input No. %d has incorrect values: ", inputCounter);
                    if (!validID)
                        System.out.print("( The ID is already taken by another Process )\n");
                    if (arrivalTime < 0)
                        System.out.print("( The Arrival time is not valid 'less than 0' )\n");
                    if (burstTime <= 0)
                        System.out.print("( The burst time is not valid 'less than or equaled 0' )\n");
                    System.out.println("\nEnter the Information About Processes: (Enter '0 0 0' to Exit)");
                    System.out.println("ID AT BT");
                } else {
                    scheduler.AddProcess(id, arrivalTime, burstTime);
                }
            }
            if (inputCounter == 1 || scheduler.isEmpty()) {
                System.out.println("<<<There Are No Processes>>");
            } else {
                System.out.println("<<All Process Added Successfully>>\n");
                System.out.println("======================================================================");
                System.out.println("=             Gantt Chart For SJF Scheduling-Preemptive:             =");
                System.out.println("======================================================================");
                System.out.println("Note: (NP Means There is no Process executed At This Time)\n");
                System.out.print(" ");
                if (scheduler.SchedulingAlgorithm()) {
                    System.out.println("\n======================================================================");
                    scheduler.PrintProcessesDetails();
                    scheduler.PrintProcessesAverages();
                }
            }

            do {
                System.out.println("\nDo you want to enter a new set of processes for scheduling? (Y or N): ");
                ScheduleAgain = scanner.next();
            }while (!(ScheduleAgain.equalsIgnoreCase("y") || ScheduleAgain.equalsIgnoreCase("N")));

        } while (ScheduleAgain.equalsIgnoreCase("Y"));
        scanner.close();
    }
}