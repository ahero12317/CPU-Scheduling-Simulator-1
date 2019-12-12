package Algorithms;

import java.util.ArrayList;
import java.util.Scanner;

import Processes.Process;

public class SRTF extends Algorithm {
	String PName;
	int noOfProcesses;
	int arrivalTime;
	int burstTime;
	int remTime;
	int compTime;
	int turnATime;
	int overheadTime, overheadCounter = 0;
	int min, i = 0, total = 0, t = 0, c;
	float avgwt = 0, avgta = 0;
	ArrayList<Process> list = new ArrayList<Process>();
	ArrayList<Process> last = new ArrayList<Process>();
	Scanner sc = new Scanner(System.in);
	Scanner nameSc = new Scanner(System.in);

	@Override
	public void Simulate() {
		System.out.println("Enter number of processes ,context switch overhead time:");
		noOfProcesses = sc.nextInt();
		overheadTime = sc.nextInt();
		for (int i = 0; i < noOfProcesses; i++) {
			Process P = new Process();
			System.out.println("Enter process name,  arrival time, burst time:");
			PName = nameSc.nextLine();
			arrivalTime = sc.nextInt();
			burstTime = sc.nextInt();

			P.setmArrivalTime(arrivalTime);
			P.setmBurstTime(burstTime);
			P.setmRemainingTime(burstTime);
			P.setmName(PName);
			P.setmCompletionTime(0);

			list.add(P);
		}

		while (true) {
			min = 100;
			c = noOfProcesses;
			if (total == noOfProcesses) {
				System.out.println("Saving data of " + last.get(i - 1).getmName() + " in PCB");
				break;
			}
			// Find process with minimum burst time
			for (int j = 0; j < list.size(); j++) {
				if ((list.get(j).getmArrivalTime() <= t) && (list.get(j).getmRemainingTime() > 0)
						&& (list.get(j).getmRemainingTime() < min)) {
					min = list.get(j).getmRemainingTime();
					last.add(i, list.get(j));
					c = j;
				}
			}

			// if no processes arrived yet
			if (c == noOfProcesses) {
				t++;
			} else {
				if (i == 0) {
					list.get(c).setmRemainingTime((list.get(c).getmRemainingTime() - 1));
					System.out.println("Loading data of " + list.get(c).getmName() + " from PCB");
					list.get(c).setmCompletionTime(list.get(c).getmCompletionTime() + overheadTime);
					System.out.println(list.get(c).getmName() + " is executing");
					t++;
					i++;
				} else if ((last.get(i - 1).getmName().equals(list.get(c).getmName()))) {
					list.get(c).setmRemainingTime((list.get(c).getmRemainingTime() - 1));
					t++;
					i++;

				} else {
					list.get(c).setmRemainingTime((list.get(c).getmRemainingTime() - 1));
					overheadCounter += (overheadTime * 2);
					System.out.println("Saving data of " + last.get(i - 1).getmName() + " in PCB");
					System.out.println("Loading data of " + list.get(c).getmName() + " from PCB");
					System.out.println(list.get(c).getmName() + " is executing");
					t++;
					i++;
				}

				if (list.get(c).getmRemainingTime() == 0) {
					list.get(c).setmCompletionTime(list.get(c).getmCompletionTime() + t + overheadCounter);
					total++;
				}
			}

		}

		for (int y = 0; y < list.size(); y++) {
			list.get(y).setmTurnAroundTime(list.get(y).getmCompletionTime() - list.get(y).getmArrivalTime());
			list.get(y).setmWaitingTime(list.get(y).getmTurnAroundTime() - list.get(y).getmBurstTime());
			avgwt += list.get(y).getmWaitingTime();
			avgta += list.get(y).getmTurnAroundTime();
		}

		System.out.println("P       arr      bur      comp       tat        wait");

		for (int y = 0; y < list.size(); y++) {
			System.out.println(list.get(y).getmName() + "       " + list.get(y).getmArrivalTime() + "       "
					+ list.get(y).getmBurstTime() + "        " + list.get(y).getmCompletionTime() + "           "
					+ list.get(y).getmTurnAroundTime() + "           " + list.get(y).getmWaitingTime());
		}

		System.out.println("average wait time = " + avgwt / noOfProcesses + "  average turn around time = "
				+ avgta / noOfProcesses);
	}

}
