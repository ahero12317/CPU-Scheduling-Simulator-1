package Algorithms;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

import Processes.Process;

public class AG extends Algorithm {
	Scanner mInput;
	ArrayList<Process> mProcesses;
	ArrayList<Process> mTemp;
	ArrayList<Process> mOutput;
	ArrayList<Process> mDeadList;
	Queue<Process> readyQueue;
	int mMaxTime;
	int mTotalQuantum;

	public AG() {
		this.mInput = new Scanner(System.in);
		this.mProcesses = new ArrayList<>();
		this.mTemp = new ArrayList<>();
		this.mOutput = new ArrayList<>();
		this.mDeadList = new ArrayList<>();
		this.readyQueue = new LinkedList<>();
		this.mMaxTime = 0;
		this.mTotalQuantum = 0;

		getInput();
	}

	public void getInput() {
		int numOfProcesses;
		System.out.println("Enter number of process.");
		numOfProcesses = this.mInput.nextInt();

		for (int i = 0; i < numOfProcesses; i++) {
			System.out.println("Enter process " + i + " name.");
			String processName = this.mInput.next();
			System.out.println("Enter arrival Time of process " + i + ".");
			int processAT = this.mInput.nextInt();
			System.out.println("Enter burst Time of process " + i + ".");
			int processBT = this.mInput.nextInt();
			this.mMaxTime += processBT;
			System.out.println("Enter quantum Time of process " + i + ".");
			int processQT = this.mInput.nextInt();
			this.mTotalQuantum += processQT;
			System.out.println("Enter priority of process " + i + ".");
			int processPriority = this.mInput.nextInt();
			System.out.println("Enter process " + i + " colour.");
			String processColour = this.mInput.next();
			System.out.print("\n");

			Process p = new Process(processName, processAT, processBT, processColour, processQT, processPriority);
			p.setmAGFactor(processAT + processBT + processPriority);
			p.setmRemainingTime(processBT);
			this.mProcesses.add(p);
			this.mTemp.add(p);

			if (this.mMaxTime < processBT + processAT)
				this.mMaxTime = processBT + processAT;
		}
	}
	
	public void printUpdates(int time) {
		System.out.print("[Time: " + time + "] -> ");
		System.out.print("Quantum ( ");
		for (int i = 0; i < this.mProcesses.size(); i++)
			System.out.print(this.mProcesses.get(i).getmQuantum() + " ");
		System.out.print(") -> ceil(50%) = ( ");
		for (int i = 0; i < this.mProcesses.size(); i++)
			System.out.print((int) Math.ceil((this.mProcesses.get(i).getmQuantum()) * 0.5) + " ");
		System.out.print(")		");
	}
	
	public void sortProcesses(int time) {
		this.mTemp.sort((o1, o2) -> {
			if (o1.getmArrivalTime() <= time && o2.getmArrivalTime() <= time) {
				if (o1.getmAGFactor() < o2.getmAGFactor())
					return -1;
				else
					return 1;
			} else if (o1.getmArrivalTime() < o2.getmArrivalTime())
				return -1;
			else
				return 1;
		});
	}
	
	@Override
	public void Simulate() {
		
		
	}
}
