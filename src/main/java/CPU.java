import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Lanae on 3/29/2016.
 */
class CPU
{
    private double clock = 0.0; //Application.preciseZero;
    Queue<Process> readyQueue = new LinkedList<Process>();
    private  boolean available = true;
    private boolean finished = false;
    private Process currentProcess = null;

    CPU() {}

    boolean isAvailable()
    {
        return available;
    }

    void setAvailable(boolean available)
    {
        this.available = available;
    }

    private void setFinished(boolean finished)
    {
        this.finished = finished;
    }

    void setClock(double clock)
    {
        this.clock = clock; //(double)Math.floor(clock * Application.precisionNumber) / Application.precisionNumber;
    }

    double getClock()
    {
        return clock;
    }

    private void incrementClock(Scheduler scheduler)
    {
        scheduler.checkArrival(this);
        if (readyQueue.size() != 0)
        {
            currentProcess = readyQueue.remove();
            System.out.println("The current process about to run in the CPU is Process " + currentProcess.getId());
            currentProcess.run(this, scheduler);
        }
        else
            setFinished(true);
    }

    private void addContextSwitch()
    {
        System.out.println("Incrementing current clock " + getClock() + " by context switch " + Application.contextSwitch);
        setClock(getClock() + Application.contextSwitch);
        System.out.println("After last context switch, Clock time now " + getClock());
    }

    void run(Scheduler scheduler)
    {
        System.out.println("CPU clock starts.");

        while (!finished)
        {
            incrementClock(scheduler);
            addContextSwitch();
        }
        if (finished)
        {
            System.out.println("Finished processes: ");
            for (Process p : scheduler.getFinishedProcesses())
            {
                System.out.println("\n" + p);
            }
        }
    }
}
