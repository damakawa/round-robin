import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.SynchronousQueue;

/**
 * Programmer: Lanae Blethen Kawa
 * Creation Date: 3/9/16
 * Date Last Modified: 4/2/16
 * CSC440 Operating Systems
 */

public class Application
{
    public static final Double quantum = Math.ceil(.1 * 10) / 10;
    public static final Double preciseZero = Math.floor(0) / 10;

    public static void main(String[] args)
    {
        Random random = new Random();
        int maxProcesses = 5;
        double initTime = preciseZero;
        ArrayList<Process> pList = new ArrayList<Process>();
        Process firstProcess = new Process(0, preciseZero, preciseZero);
        firstProcess.setServiceTime(firstProcess.serviceTime(random));
        pList.add(0, firstProcess);

        for (int i = 1; i < maxProcesses; i++)
        {
            Process process = new Process();
            process.setInterArrivalTime(process.interArrivalTime(random));
            process.setServiceTime(process.serviceTime(random));
            process.setId(i);

            initTime += process.getInterArrivalTime();

            process.setArrivalTime(initTime);
            pList.add(i, process);
        }

        for (Process p: pList)
        {
            System.out.println(p.toString());
        }

        Scheduler scheduler = new Scheduler(pList, maxProcesses);
        CPU cpu = new CPU();

        cpu.run(scheduler);
    }
}
