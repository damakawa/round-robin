import java.math.BigDecimal;
import java.util.Random;

/**
 * Created by Lanae on 3/9/2016.
 */

enum State {ARRIVED, READY, WAITING, RUNNING, EXECUTED}

public class Process
{
    private int id;
    private Double interArrivalTime;
    private Double serviceTime;
    private Double arrivalTime;
    private Double startTime = Application.preciseZero;
    private Double endTime = Application.preciseZero;
    private Double waitTime = Application.preciseZero;
    private Double turnTime = Application.preciseZero;
    private State pState = null;

    Process() {}

    Process(int id, Double arrivalTime, Double interArrivalTime)
    {
        this.id = id;
        this.arrivalTime = Math.floor(arrivalTime * 10) / 10;
        this.interArrivalTime = Math.floor(interArrivalTime * 10) / 10;
    }

    void setInterArrivalTime(Double interArrivalTime)
    {
        this.interArrivalTime = Math.floor(interArrivalTime * 10) / 10;
    }

    Double getInterArrivalTime()
    {
        return interArrivalTime;
    }

    void setId(int id)
    {
        this.id = id;
    }

    int getId()
    {
        return id;
    }

    void setServiceTime(Double serviceTime)
    {
        this.serviceTime = Math.floor(serviceTime * 10) / 10;
    }

    private Double getServiceTime()
    {
        return serviceTime;
    }

    void setArrivalTime(Double arrivalTime)
    {
        this.arrivalTime = Math.floor(arrivalTime * 10) / 10;
    }

    private Double getArrivalTime() {return arrivalTime;}

    void setWaitTime(Double waitTime)
    {
        this.waitTime = Math.floor(waitTime * 10) / 10;
    }

    Double getWaitTime()
    {
        return waitTime;
    }

    void setTurnTime(Double turnTime)
    {
        this.turnTime = Math.floor(turnTime * 10) / 10;
    }

    Double getTurnTime()
    {
        return turnTime;
    }

    private void setStartTime(Double startTime)
    {
        this.startTime = Math.floor(startTime * 10) / 10;
    }

    private Double getStartTime()
    {
        return startTime;
    }

    private void setEndTime(Double endTime)
    {
        this.endTime = Math.floor(endTime * 10) / 10;
    }

    private Double getEndTime()
    {
        return endTime;
    }

    void setpState(State pState)
    {
        this.pState = pState;
    }

    State getpState()
    {
        return pState;
    }


    Double interArrivalTime(Random random)
    {
        return Math.floor( (-0.2 * Math.log(1 - random.nextDouble() ) ) * 10) / 10;
    }

    Double serviceTime(Random random)
    {
        return Math.floor( (2 + ( (5-2) * random.nextDouble() ) ) * 10) / 10;
    }

    boolean isReady(CPU cpu)
    {
        if ((getArrivalTime() <= cpu.getClock() ))
        {
            setpState(State.ARRIVED);
            return true;
        }
        else
        {
            return false;
        }
    }

    public void run(CPU cpu, Scheduler scheduler)
    {
        cpu.setAvailable(false);
        setpState(State.RUNNING);
        if (getStartTime().equals(Application.preciseZero) && getId() != 0)
        {
            setStartTime(cpu.getClock());
        }
        System.out.println("Process " + getId() + " is executing in CPU. It's original start time was "
                + getStartTime() + " and it's current service time remaining is " + getServiceTime());
        if (getServiceTime() > Application.preciseZero)
        {
            setServiceTime((getServiceTime()) - Application.quantum);
        }
        System.out.println("Process " + getId() + " now has a service time of: " + getServiceTime());
        if (getServiceTime().equals(Application.preciseZero))
        {
            setEndTime(cpu.getClock());
            setpState(State.EXECUTED);
            System.out.println("Process " + getId() + " has finished executing. It's end time is " + getEndTime());
        }
        else
        {
            setpState(State.READY);
            scheduler.addTopList(this);
            //cpu.readyQueue.add(this);
            System.out.println("Process " + getId() + " has not finished executing. " +
                    "It is being added back to the ready queue");
        }
        cpu.setAvailable(true);
    }

    @Override
    public String toString()
    {
        return "Process id: " + getId() + "\n Arrival time : " + getArrivalTime() + "\n Service time : " + getServiceTime();
    }
}