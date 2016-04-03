import java.math.BigDecimal;
import java.util.Random;

/**
 * Created by Lanae on 3/9/2016.
 */

enum State {ARRIVED, READY, WAITING, RUNNING, EXECUTED}

public class Process
{
    private int id;
    private Double interArrival;
    private Double service;
    private Double arrivalTime;
    private Double startTime;
    private Double endTime;
    private Double waitTime;
    private Double turnTime;
    private State pState = null;

    Process() {}

    Process(int id, Double arrivalTime, Double interArrival)
    {
        this.id = id;
        this.arrivalTime = new BigDecimal(arrivalTime).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
        this.interArrival = new BigDecimal(interArrival).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    void setInterArrival(Double interArrival)
    {
        this.interArrival = new BigDecimal(interArrival).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    Double getInterArrival()
    {
        return interArrival;
    }

    void setId(int id)
    {
        this.id = id;
    }

    int getId()
    {
        return id;
    }

    void setService(Double service)
    {
        this.service = new BigDecimal(service).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    private Double getService()
    {
        return service;
    }

    void setArrivalTime(Double arrivalTime)
    {
        this.arrivalTime = new BigDecimal(arrivalTime).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    private Double getArrivalTime() {return arrivalTime;}

    void setWaitTime(Double waitTime)
    {
        this.waitTime = new BigDecimal(waitTime).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    Double getWaitTime()
    {
        return waitTime;
    }

    void setTurnTime(Double turnTime)
    {
        this.turnTime = new BigDecimal(turnTime).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    Double getTurnTime()
    {
        return turnTime;
    }

    private void setStartTime(Double startTime)
    {
        this.startTime = new BigDecimal(startTime).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    private Double getStartTime()
    {
        return startTime;
    }

    private void setEndTime(Double endTime)
    {
        this.endTime = new BigDecimal(endTime).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
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


    Double interArrival(Random random)
    {
        return new BigDecimal((-0.2 * Math.log(1 - random.nextDouble())))
                .setScale(3, BigDecimal.ROUND_HALF_UP)
                .doubleValue();
    }

    Double serviceTime(Random random)
    {
        return new BigDecimal((2 + ( (5-2) * random.nextDouble())))
                .setScale(3, BigDecimal.ROUND_HALF_UP)
                .doubleValue();
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
        if (getStartTime() == Application.preciseZero)
        {
            setStartTime(cpu.getClock());
        }
        System.out.println("Process " + getId() + " is executing in CPU. It's original start time was "
        + getStartTime() + " and it's current service time remaining is " + getService());
        while (getService() > Application.preciseZero)
        {
            setService((getService()) - Application.quantum);
        }
        System.out.println("Process " + getId() + " now has a service time of: " + getService());
        if (getService() == Application.preciseZero)
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
        return "Process id: " + getId() + "\n Arrival time : " + getArrivalTime() + "\n Service time : " + getService();
    }
}
