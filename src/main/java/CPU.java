import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Lanae on 3/29/2016.
 */
public class CPU
{
    public Double clock = Application.preciseZero;
    public Queue<Process> readyQueue = new LinkedList<Process>();
    private  boolean available = true;
    public boolean finished = false;
    public Process currentProcess = null;

    CPU() {}

    public boolean isAvailable()
    {
        return available;
    }

    public void setAvailable(boolean available)
    {
        this.available = available;
    }

    private void setFinished(boolean finished)
    {
        this.finished = finished;
    }

    public Double getClock()
    {
        return new BigDecimal(clock)
                .setScale(3, BigDecimal.ROUND_HALF_DOWN)
                .doubleValue();
    }

    public void incrementClock(Scheduler scheduler)
    {
        scheduler.checkArrival(this);
        if (readyQueue.size() != 0)
        {
            currentProcess = readyQueue.remove();
            System.out.println("The current process about to run in the CPU is Process " + currentProcess.getId());
            currentProcess.run(this, scheduler);
            clock += Application.quantum;
            System.out.println("Clock tick: " + clock);
        }
        else
            setFinished(true);
    }

    public void run(Scheduler scheduler)
    {
        System.out.println("CPU clock starts.");

        while (!finished)
        {
            incrementClock(scheduler);
        }
    }
}
