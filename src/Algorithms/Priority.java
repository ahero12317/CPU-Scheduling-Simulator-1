package Algorithms;
import Processes.Process;
import java.util.*;

public class Priority extends Algorithm {
	int n;
	String name;
	int Atime;
	int Btime;
	int prio;
	int Initial_time=0;
	int Completed_time;
	
	Scanner sc=new Scanner(System.in);
	Scanner sc1=new Scanner(System.in);
	
class Mycomparator implements Comparator<Object>
{
	public int compare(Object o1,Object o2)
	{
		Process p1=(Process)o1;
		Process p2=(Process)o2;
		if(p1.getmArrivalTime()<p2.getmArrivalTime())
			return (-1);
		else if(p1.getmArrivalTime()==p2.getmArrivalTime() && p1.getmPriority()<p2.getmPriority())
			return (-1);
		else
			return (1);
	}
	
}

	@Override
	public void Simulate() {
		TreeSet<Process> queue=new TreeSet<Process>(new Mycomparator());
		System.out.println("enter no. of processes");
		n=sc.nextInt();
		for(int i=0;i<n;i++)
		{
			Process p=new Process();
			System.out.println("enter process name, arrival time, burst time, prio");
			name=sc1.nextLine();
			Atime=sc.nextInt();
			Btime=sc.nextInt();
			prio=sc.nextInt();
			p.setmName(name);
			p.setmArrivalTime(Atime);
			p.setmBurstTime(Btime);
			p.setmPriority(prio);
			queue.add(p);	
		}
		
		ArrayList<Process> result=new ArrayList<Process>();
		Iterator<Process> it = queue.iterator();
		Initial_time = ((Process)queue.first()).getmArrivalTime();
		while(it.hasNext())
		{
			Process p1 = (Process)it.next();
			Initial_time+=p1.getmBurstTime();
			Completed_time=Initial_time;
			p1.setmTurnAroundTime(Completed_time-p1.getmArrivalTime());
			p1.setmWaitingTime(p1.getmTurnAroundTime()-p1.getmBurstTime());
			result.add(p1);
			
		}
		int sumWT=0;
		int sumTAT=0;
		int avgWT=0;
		int avgTAT=0;
		System.out.println("\n\nProcess \t Burst Time \t Wait Time \t Turn Around Time   Priority \n");
		for(int i=0;i<result.size();i++)
		{
			
			sumWT=sumWT+result.get(i).getmWaitingTime();
			sumTAT=sumTAT+result.get(i).getmTurnAroundTime();
			System.out.print("\n   "+result.get(i).getmName()+"\t\t   "+result.get(i).getmBurstTime()+"\t\t     "+result.get(i).getmWaitingTime()+"\t\t     "+result.get(i).getmTurnAroundTime()+"\t\t     "+result.get(i).getmPriority()+"\n");
		}
		avgWT=sumWT/n;
		avgTAT=sumTAT/n;
		System.out.print("average waiting time: " + avgWT + "\n");
		System.out.print("average turn around time: " + avgTAT + "\n");
	}
}
