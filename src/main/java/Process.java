/**
 * Created by Lanae on 3/9/2016.
 */

enum State {ARRIVED, READY, WAITING, RUNNING, EXECUTED}

class Process
{
     int id;
     double interArrivalTime = 0.0;
     double origServiceTime = 0.0;
     double currentServiceTime = 0.0;
     double arrivalTime = 0.0;
     double startTime = 0.0;
     double endTime = 0.0;
     double waitTime = 0.0;
     double turnTime = 0.0;
     State pState = null;

    Process() {}

    Process(int id, double arrivalTime, double interArrivalTime)
    {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.interArrivalTime = interArrivalTime;
    }

    void setId(int id)
    {
        this.id = id;
    }

    int getId()
    {
        return id;
    }

    void setInterArrivalTime(double interArrivalTime)
    {
        this.interArrivalTime = interArrivalTime;
    }

    double getInterArrivalTime()
    {
        return interArrivalTime;
    }

     void setCurrentServiceTime(double currentServiceTime)
    {
        this.currentServiceTime = currentServiceTime;
    }

     double getCurrentServiceTime()
    {
        return currentServiceTime;
    }

    void setOrigServiceTime(double origServiceTime)
    {
        this.origServiceTime = origServiceTime; 
        this.setCurrentServiceTime(getOrigServiceTime());
    }

     double getOrigServiceTime()
    {
        return origServiceTime;
    }

    void setArrivalTime(double arrivalTime)
    {
        this.arrivalTime = arrivalTime; //Math.floor(arrivalTime * Application.precisionNumber) / Application.precisionNumber;
    }

     double getArrivalTime() {return arrivalTime;}

     void setWaitTime(double waitTime)
    {
        this.waitTime = waitTime; //Math.floor(waitTime * Application.precisionNumber) / Application.precisionNumber;
    }

     double getWaitTime()
    {
        return waitTime;
    }

     void setTurnTime(double turnTime)
    {
        this.turnTime = turnTime; //Math.floor(turnTime * Application.precisionNumber) / Application.precisionNumber;
    }

     double getTurnTime()
    {
        return turnTime;
    }

     void setStartTime(double startTime)
    {
        this.startTime = startTime; //Math.floor(startTime * Application.precisionNumber) / Application.precisionNumber;
    }

     double getStartTime()
    {
        return startTime;
    }

     void setEndTime(double endTime)
    {
        this.endTime = endTime; //Math.floor(endTime * Application.precisionNumber) / Application.precisionNumber;
    }

     double getEndTime()
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

    void run(CPU cpu, Scheduler scheduler)
    {
        cpu.setAvailable(false);
        setpState(State.RUNNING);

        if (getStartTime() == 0 && getId() != 0)
        {
            setStartTime(cpu.getClock());
        }

        System.out.println("Process " + getId() + " is executing in CPU. It's original start time was "
                + getStartTime() + " and its current service time remaining is " + getCurrentServiceTime());

        if (Application.remQuantum > 0)
        {
            if (getCurrentServiceTime() > Application.remQuantum)
            {
                setCurrentServiceTime(getCurrentServiceTime() - Application.remQuantum);
                cpu.setClock(cpu.getClock() + Application.remQuantum);
                Application.remQuantum = 0;
            }
            else
            {
                double temp = Application.remQuantum - getCurrentServiceTime();
                setCurrentServiceTime(0);
                cpu.setClock(cpu.getClock() + temp);
                Application.remQuantum =- temp;
            }

        }

        if (getCurrentServiceTime() >= 1)
        {
            setCurrentServiceTime((getCurrentServiceTime()) - Application.quantum);
            cpu.setClock(cpu.getClock() + Application.quantum);
            System.out.println("After last quantum, Process " + getId() + " now has a service time of: " + getCurrentServiceTime());
            System.out.println("After last quantum, Clock time is now : " + cpu.getClock());
        }

        if (getCurrentServiceTime() < 1 && getCurrentServiceTime() > 0)
        {
            cpu.setClock(cpu.getClock() + getCurrentServiceTime());
            Application.remQuantum = getCurrentServiceTime();
            setCurrentServiceTime(0);
        }

        if (getCurrentServiceTime() == 0)
        {
            setEndTime(cpu.getClock());
            setTurnTime(getEndTime() - getStartTime());
            setWaitTime(getTurnTime() - getOrigServiceTime());
            setpState(State.EXECUTED);
            scheduler.addToFinishedList(this);
            System.out.println("Process " + getId() + " has finished executing. Its end time is " + getEndTime());
        }
        else
        {
            setpState(State.READY);
            //scheduler.addTopList(this);
            cpu.readyQueue.add(this);
            System.out.println("Process " + getId() + " has not finished executing. " +
                    "It is being added back to the ready queue");
        }

        cpu.setAvailable(true);
    }

    @Override
    public String toString()
    {
        return "Process id: " + getId() + "\n Interarrival Time : " + getInterArrivalTime() +
                "\n Arrival time : " + getArrivalTime() + "\n Service time : " + getOrigServiceTime()
                + "\n Start time : " + getStartTime() + "\n End time : " + getEndTime() + "\n Turnaround time: " + getTurnTime()
                + "\n Wait time: " + getWaitTime();
    }
}