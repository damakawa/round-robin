import java.util.ArrayList;

/**
 * Created by Lanae on 3/31/2016.
 */
public class Scheduler
{
    private  ArrayList<Process> pList = null;
    private int num;

    Scheduler(ArrayList<Process> pList, int num)
    {
        this.num = num;
        this.pList = pList;
    }

    public void addTopList(Process process)
    {
        pList.add(process);
    }

    public void checkArrival(CPU cpu)
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
                System.out.println("Process " + i + " is now ready. It will be added to the ready queue.");
                cpu.readyQueue.add(pList.get(i));
                pList.remove(i);
            }
        }
    }
}
