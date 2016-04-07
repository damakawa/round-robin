import java.util.*;

/**
 * Programmer: Lanae Blethen Kawa
 * Creation Date: 3/9/16
 * Date Last Modified: 4/6/16
 * CSC440 Operating Systems
 */

public class Application
{
    static final double quantum = 1.0;
    static double remQuantum = 0.0;
    static final double contextSwitch = 0.0;

    public static void main(String[] args)
    {
        int maxProcesses = 100;
        ArrayList<Process> pList = new ArrayList<Process>();
        Process firstProcess = new Process(0, 0.0, 0.0);
        firstProcess.setOrigServiceTime(genServiceTime());
        pList.add(0, firstProcess);

        for (int i = 1; i < maxProcesses; i++)
        {
            Process process = new Process();
            process.setInterArrivalTime(genInterArrivalTime());
            process.setOrigServiceTime(genServiceTime());
            process.setId(i);
            pList.add(i, process);
            process.setArrivalTime(cleanDouble(pList.get(i-1).getArrivalTime() + process.getInterArrivalTime()));
        }

        Scheduler scheduler = new Scheduler(pList);
        CPU cpu = new CPU();

        cpu.run(scheduler);

        for (int i = 0; i <20; i++)
        {
            printStartEndWait(scheduler.getFinishedProcesses().get(i));
        }

        printAverages(scheduler.getFinishedProcesses());

    }

    static double cleanDouble(double dirtyDouble)
    {
        int temp = (int)(dirtyDouble * 100.0);
        return ((double)temp)/100.0;
    }

    private static double genInterArrivalTime()
    {
        return cleanDouble(-0.2 * Math.log(1 - Math.random() ));
    }

    private static double genServiceTime()
    {
        return cleanDouble(2 + ( (5-2) * Math.random() ) );
    }

    private static double averageServiceTime(ArrayList<Process> finishedProcesses)
    {
        double sum = 0.0;
        double count = 0.0;
        for (Process p : finishedProcesses)
        {
            sum += p.getOrigServiceTime();
            count++;
        }
        return cleanDouble(sum / count);
    }

    private static double averageWaitTime(ArrayList<Process> finishedProcesses)
    {
        double sum = 0.0;
        double count = 0.0;
        for (Process p : finishedProcesses)
        {
            sum += p.getWaitTime();
            count++;
        }
        return cleanDouble(sum / count);
    }

    private static double averageTurnaroundTime(ArrayList<Process> finishedProcesses)
    {
        double sum = 0.0;
        double count = 0.0;
        for (Process p : finishedProcesses)
        {
            sum += p.getTurnTime();
            count++;
        }
        return cleanDouble(sum / count);
    }

    private static void printStartEndWait(Process p)
    {
        System.out.println(p);
/*        System.out.println("Process " + p.getId() + " Start time: " + p.getStartTime() + " End time: "
            + p.getEndTime() + " Wait time: " + p.getWaitTime());*/
    }

    private static void printAverages(ArrayList<Process> pList)
    {
        System.out.println("For " + pList.size() + " processes\n Average service time was "
                + averageServiceTime(pList) + "\n Average wait time: " + averageWaitTime(pList)
                + "\n Average turnaround time: " + averageTurnaroundTime(pList));
    }

}
