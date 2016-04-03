import java.math.BigDecimal;
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
    public static final Double quantum = new BigDecimal(0.001)
            .setScale(3, BigDecimal.ROUND_HALF_DOWN)
            .doubleValue();
    public static final Double preciseZero = new BigDecimal(0)
            .setScale(3, BigDecimal.ROUND_HALF_DOWN)
            .doubleValue();

    public static void main(String[] args)
    {
        Random random = new Random();
        int maxProcesses = 5;
        double initTime = preciseZero;
        ArrayList<Process> pList = new ArrayList<Process>();
        Process firstProcess = new Process(0, preciseZero, preciseZero);
        firstProcess.setService(firstProcess.serviceTime(random));
        pList.add(0, firstProcess);

        for (int i = 1; i < maxProcesses; i++)
        {
            Process process = new Process();
            process.setInterArrival(process.interArrival(random));
            process.setService(process.serviceTime(random));
            process.setId(i);

            initTime += process.getInterArrival();

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
