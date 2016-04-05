import java.util.*;

/**
 * Programmer: Lanae Blethen Kawa
 * Creation Date: 3/9/16
 * Date Last Modified: 4/3/16
 * CSC440 Operating Systems
 */


/***************************why is Arrival time not calculating correctly?????**********************/
public class Application
{
    //static final int precisionNumber = 1;
    static final double quantum = 1.0; //Math.ceil(1 * precisionNumber) / precisionNumber;
    static double remQuantum = 0.0;
    //static final long preciseZero = Math.floor(0); // precisionNumber;
    // some weird bug happens here when you change value from 0.1 to anything else
    static final double contextSwitch = 0.0;

    public static void main(String[] args)
    {
/*        for (int i=0; i <10; i++)
        {System.out.println("Interarrival time " + i + ": " + genInterArrivalTime());}
        for (int i=0; i <10; i++)
        {System.out.println("service time " + i + ": " + genServiceTime());}*/


        //Random random = new Random();
        int maxProcesses = 3;
        ArrayList<Process> pList = new ArrayList<Process>();
        Process firstProcess = new Process(0, 0.0, 0.0);
        firstProcess.setOrigServiceTime(genServiceTime());
        pList.add(0, firstProcess);

        for (int i = 1; i < maxProcesses; i++)
        {
            Process process = new Process();
            process.setInterArrivalTime(genInterArrivalTime());
            System.out.println("Process " + i + " interarrival time : " + process.getInterArrivalTime());

            process.setOrigServiceTime(genServiceTime());
            process.setId(i);
            pList.add(i, process);
            process.setArrivalTime(pList.get(i-1).getArrivalTime() + process.getInterArrivalTime());
            System.out.println("Process " + i + " arrival time : " + process.getArrivalTime());
            System.out.println(process.toString());
        }

        Scheduler scheduler = new Scheduler(pList);
        CPU cpu = new CPU();

        cpu.run(scheduler);
    }

    private static double genInterArrivalTime()
    {
        double rand = (-0.2 * Math.log(1 - Math.random() ));
        int temp = (int)(rand * 100.0);
        return ((double)temp)/100.0;
    }

    private static double genServiceTime()
    {
        double rand = (2 + ( (5-2) * Math.random() ) );
        int temp = (int)(rand * 100.0);
        return ((double)temp)/100.0;
    }

}
