package Algorithms;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import Processes.Process;
public class SJF extends Algorithm {
    int n,NCompleted_process=0,system_time=0;
    String process_name;
    int arrival_time;
    int burst_time;
    int waiting_time;
    int turnAround_time;
    int complete_time;
    float avgWait_time=0;
    float avgTurnAround_time=0;
    ArrayList<Process> list = new ArrayList<Process>();
	Scanner sc = new Scanner(System.in);
	Scanner nameSc = new Scanner(System.in);
	@Override
    public void Simulate() {
    	System.out.println("Enter number of processes:");
		n = sc.nextInt();
		for (int i = 0; i < n; i++) {
			Process P = new Process();
			System.out.println("Enter process name,  arrival time, burst time");
			process_name = nameSc.nextLine();
			arrival_time = sc.nextInt();
			burst_time = sc.nextInt();

			P.setmArrivalTime(arrival_time);
			P.setmBurstTime(burst_time);
			P.setmName(process_name);
			P.setmWaitingTime(waiting_time);

			list.add(P);
		}
		int completed[]=new int [n];
		
		for(int i=0;i<n;i++)
		{
			completed[i]=0;
		}
	    while (true) {
	    	int x=n,minimum=1000;
	    	if(NCompleted_process==n)
	    		break;
	    	for(int i=0;i<n;i++) 
	    	{
	    		if(completed[i]==0 && list.get(i).getmArrivalTime()<=system_time && list.get(i).getmBurstTime()<minimum)
	    		{
	    			minimum=list.get(i).getmBurstTime();
	    			x=i;
	    		}
	    	}
	    	if(x==n)
	    		system_time++;
	    	else 
	    	{
	    		int a=(system_time)+(list.get(x).getmBurstTime());
	    		list.get(x).setmCompletionTime(a);
	    		system_time+=list.get(x).getmBurstTime();
	    		list.get(x).setmTurnAroundTime(list.get(x).getmCompletionTime() - list.get(x).getmArrivalTime());
	    		list.get(x).setmWaitingTime(list.get(x).getmTurnAroundTime() - list.get(x).getmBurstTime());
	    		completed[x]=1;
	    		NCompleted_process++;
	    	}
	    }
	    System.out.println("\nprocessName  arrival_time  brust_time  complete_time turnAround_time waiting_time");
	    for(int i=0;i<n;i++) 
	    {
	    	avgWait_time+=list.get(i).getmWaitingTime();
    		avgTurnAround_time+=list.get(i).getmWaitingTime();
    		System.out.println(list.get(i).getmName() + "           " + list.get(i).getmArrivalTime() + "       "
					+ list.get(i).getmBurstTime() + "        " + list.get(i).getmCompletionTime() + "           "
					+ list.get(i).getmTurnAroundTime() + "           " + list.get(i).getmWaitingTime());	    
	    }
    	System.out.println("\n average waiting time : "+(float)(avgWait_time/n));
	    System.out.println("\n average turn time : "+(float)(avgTurnAround_time/n));
	    sc.close();
	    nameSc.close();
    }
}
