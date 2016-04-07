import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Lanae on 3/29/2016.
 */
class CPU
{
    private double clock = 0.0;
    Queue<Process> readyQueue = new LinkedList<Process>();
    private  boolean available = true;
    private boolean finished = false;

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
        this.clock = clock;
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
            readyQueue.remove().run(this, scheduler);
        }
        else
            setFinished(true);
    }

    private void addContextSwitch()
    {
        setClock(getClock() + Application.contextSwitch);
    }

    void run(Scheduler scheduler)
    {
        while (!finished)
        {
            incrementClock(scheduler);
            addContextSwitch();
        }
    }
}
