import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Lanae on 3/31/2016.
 */
class Scheduler
{
    private  ArrayList<Process> pList = null;
    private Queue<Process> finishedProcesses= new LinkedList<Process>();

    Scheduler(ArrayList<Process> pList)
    {
        this.pList = pList;
    }

    void addTopList(Process process)
    {
        pList.add(process);
    }

    void addToFinishedList(Process process) {finishedProcesses.add(process);}

    Queue<Process> getFinishedProcesses()
    {
        return finishedProcesses;
    }

    void checkArrival(CPU cpu)
    {
        if (pList == null)
        {
            return;
        }

        for (int i = 0; i < pList.size(); i++)
        {
            if (pList.get(i).isReady(cpu))
            {
                pList.get(i).setpState(State.READY);
                cpu.readyQueue.add(pList.get(i));
                pList.remove(i);
            }
        }
        for (int j = 0; j < pList.size(); j++) //clean up empty spots
        {
            if (pList.get(j) == null)
            {
                if ( (j + 1) < pList.size())
                {
                    pList.add(j, pList.get(j + 1));
                }
                else
                {
                    pList.remove(j);
                }
            }
        }

    }
}
