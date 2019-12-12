package Algorithms;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

import Processes.Process;

public class AG extends Algorithm {
	Scanner mInput;
	int mNumOfProcesses;
	ArrayList<Process> mProcesses;
	ArrayList<Process> mTemp;
	ArrayList<Process> mOutput;
	ArrayList<Process> mDeadList;
	Queue<Process> readyQueue;
	int mMaxTime;
	int mTotalQuantum;

	public AG() {
		this.mInput = new Scanner(System.in);
		this.mNumOfProcesses = 0;
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
		System.out.println("Enter number of process.");
		this.mNumOfProcesses = this.mInput.nextInt();

		for (int i = 0; i < this.mNumOfProcesses; i++) {
			System.out.println("Enter process " + (i + 1) + " name.");
			String processName = this.mInput.next();
			System.out.println("Enter arrival Time of process " + (i + 1) + ".");
			int processAT = this.mInput.nextInt();
			System.out.println("Enter burst Time of process " + (i + 1) + ".");
			int processBT = this.mInput.nextInt();
			this.mMaxTime += processBT;
			System.out.println("Enter quantum Time of process " + (i + 1) + ".");
			int processQT = this.mInput.nextInt();
			this.mTotalQuantum += processQT;
			System.out.println("Enter priority of process " + (i + 1) + ".");
			int processPriority = this.mInput.nextInt();
			System.out.println("Enter process " + (i + 1) + " colour.");
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

	public void isFinished() {
		boolean x = true;
		while (x) {
			for (int i = 0; i < this.mTemp.size(); i++) {
				if (this.readyQueue.peek().getmAGFactor() == this.mTemp.get(i).getmAGFactor())
					x = false;
			}

			if (x)
				this.readyQueue.remove();
		}
	}

	public void printExecutionOrder() {
		System.out.println("\n[Processes execution order]");
		for (int i = 0; i < this.mOutput.size(); i++)
			System.out.print(this.mOutput.get(i).getmName() + "	");
		System.out.print("\n\n");
	}

	@Override
	public void Simulate() {
		boolean queue = false;
		int lastProcess = 0; /// Store the last process info to prevent duplication
		for (int time = 0; time <= this.mMaxTime;) {
			/// Sorting the remaining processes according to their AG-Factor.
			int _time = time;
			sortProcesses(_time);

			/// Printing the quantum time of each process.
			printUpdates(time);

			/// (ceil(50% of the QT))->[Non-Preemptive AG]
			if (this.mTemp.size() != 0 && this.mTemp.get(0).getmArrivalTime() <= time && !queue) {
				/// Creating a temporary process that holds all the info of the running process.
				Process tempProcess = new Process(this.mTemp.get(0).getmName(), this.mTemp.get(0).getmArrivalTime(),
						this.mTemp.get(0).getmBurstTime(), this.mTemp.get(0).getmColour(),
						this.mTemp.get(0).getmQuantum(), this.mTemp.get(0).getmPriority());

				tempProcess.setmAGFactor(this.mTemp.get(0).getmAGFactor());
				tempProcess.setmRemainingTime(this.mTemp.get(0).getmRemainingTime());
				tempProcess.setmTurnAroundTime(this.mTemp.get(0).getmTurnAroundTime());
				tempProcess.setmWaitingTime(this.mTemp.get(0).getmWaitingTime());

				System.out.println("Process " + tempProcess.getmName() + " running.");
				/// Adding the current process to the output list
				///// TODO: Handle the exception to compare strings/////
				/*
				 * if (!lastProcess.equals(tempProcess.getmName())) {
				 * this.mOutput.add(tempProcess); lastProcess = tempProcess.getmName(); }
				 */

				if (lastProcess != tempProcess.getmAGFactor()) {
					this.mOutput.add(tempProcess);
					lastProcess = tempProcess.getmAGFactor();
				}

				/// Updating the waiting time and the remaining time of the current process,
				tempProcess.setmWaitingTime(time - tempProcess.getmArrivalTime() + tempProcess.getmRemainingTime()
						- tempProcess.getmBurstTime());
				time += Math.min(tempProcess.getmRemainingTime(), (int) Math.ceil(tempProcess.getmQuantum() * 0.5));
				tempProcess.setmRemainingTime(tempProcess.getmRemainingTime()
						- Math.min((int) Math.ceil(tempProcess.getmQuantum() * 0.5), tempProcess.getmRemainingTime()));

				/// The rest of the QT ->[Preemptive AG]
				int halfQT = (int) Math.floor(tempProcess.getmQuantum() * 0.5);
				for (int i = 0; i < halfQT; i++) {

					if (tempProcess.getmRemainingTime() != 0) { /// Testing if the process finished its job.
						Boolean flag = false;
						for (Process process : this.mTemp) {
							if (process.getmAGFactor() < tempProcess.getmAGFactor()
									&& process.getmArrivalTime() <= time) {
								flag = true;
								break;
							}
						}

						/// If there is a process with smaller AG factor.
						if (flag) {
							this.readyQueue.add(tempProcess);
							/// Changing the QT of the process
							/// Case 1: The running process used all its quantum time and it still have job
							/// to do.
							if (i == halfQT - 1) {
								int mean = ((int) Math.ceil((this.mTotalQuantum / this.mNumOfProcesses) * 0.1));
								tempProcess.setmQuantum(tempProcess.getmQuantum() + mean);
								this.mTotalQuantum += mean;
							}
							/// Case 2: The running process didn’t use all its quantum time based on another
							/// process converted from ready to running
							else {
								int remainingQT = halfQT - i;
								tempProcess.setmQuantum(tempProcess.getmQuantum() + remainingQT);
								this.mTotalQuantum += remainingQT;
								break;
							}
						}

						/// If there isn't any process with smaller AG factor.
						else {
							/// Case 1: The running process used all its quantum time and it still have job
							/// to do.
							if (i == halfQT - 1) {
								int mean = ((int) Math.ceil((this.mTotalQuantum / this.mNumOfProcesses) * 0.1));
								tempProcess.setmQuantum(tempProcess.getmQuantum() + mean);
								this.mTotalQuantum += mean;
								this.readyQueue.add(tempProcess);
								queue = true;
							}
							tempProcess.setmRemainingTime(tempProcess.getmRemainingTime() - 1);
							time++;
						}
					}

					/// if the process finished its job.
					else {
						tempProcess.setmTurnAroundTime(time - tempProcess.getmArrivalTime());
						mTotalQuantum -= tempProcess.getmQuantum();
						tempProcess.setmQuantum(0);
						this.mDeadList.add(tempProcess);
						this.mTemp.remove(0);
						queue = true;
						break;
					}
				}

				for (int i = 0; i < this.mProcesses.size(); i++) {
					if (this.mProcesses.get(i).getmName().equals(tempProcess.getmName())) {
						/*
						 * this.mProcesses.get(i).setmQuantum(tempProcess.getmQuantum());
						 * this.mProcesses.get(i).setmRemainingTime(tempProcess.getmRemainingTime());
						 * this.mProcesses.get(i).setmTurnAroundTime(tempProcess.getmTurnAroundTime());
						 * this.mProcesses.get(i).setmWaitingTime(tempProcess.getmWaitingTime());
						 */
						this.mProcesses.set(i, tempProcess);
					}
				}

				/// Updating the current process in the temporary arrayList.
				for (int i = 0; i < this.mTemp.size(); i++) {
					if (this.mTemp.get(i).getmName().equals(tempProcess.getmName())) {
						this.mTemp.set(i, tempProcess);
					}
				}
			}

			/// Job queue.
			else if (!readyQueue.isEmpty() && queue) {
				/// Check whether the processes in the queue have finished their job or not.
				/// and remove them from the queue if finished.
				isFinished();
				Process tempProcess = this.readyQueue.peek(); /// Creating a temporary process that holds all the info
																/// of
																/// the running process.
				System.out.println("Process " + tempProcess.getmName() + " running.");
				///// TODO: handle the exception to compare strings.
				/*
				 * if (!tempProcess.getmName().equals(lastProcess)) {
				 * this.mOutput.add(tempProcess); lastProcess = tempProcess.getmName(); }
				 */
				if (lastProcess != tempProcess.getmAGFactor()) {
					this.mOutput.add(tempProcess);
					lastProcess = tempProcess.getmAGFactor();
				}

				tempProcess.setmWaitingTime(time - tempProcess.getmArrivalTime() + tempProcess.getmRemainingTime()
						- tempProcess.getmBurstTime());
				time += Math.min(tempProcess.getmRemainingTime(), (int) Math.ceil(tempProcess.getmQuantum() * 0.5));
				tempProcess.setmRemainingTime(tempProcess.getmRemainingTime()
						- Math.min((int) Math.ceil(tempProcess.getmQuantum() * 0.5), tempProcess.getmRemainingTime()));

				/// The rest of the QT ->[Preemptive AG]
				int halfQT = (int) Math.floor(tempProcess.getmQuantum() * 0.5);
				for (int i = 0; i < halfQT; i++) {

					/// process finished its job.
					if (tempProcess.getmRemainingTime() == 0) {
						tempProcess.setmTurnAroundTime(time - tempProcess.getmArrivalTime());
						mTotalQuantum -= tempProcess.getmQuantum();
						tempProcess.setmQuantum(0);
						this.mDeadList.add(tempProcess);
						this.readyQueue.remove();
						for (int j = 0; j < this.mTemp.size(); j++) {
							if (this.mTemp.get(j).getmAGFactor() == tempProcess.getmAGFactor()) {
								this.mTemp.remove(j);
								break;
							}
						}
						break;
					} else {
						Boolean flag = false;
						for (Process process : this.mTemp) {
							if (process.getmAGFactor() < tempProcess.getmAGFactor()
									&& process.getmArrivalTime() <= time) {
								flag = true;
								break;
							}
						}
						if (flag) {
							this.readyQueue.remove();
							this.readyQueue.add(tempProcess);
							// Changing the QT of the process
							/// Case 1: The running process used all its quantum time and it still have job
							/// to do.
							if (i == halfQT - 1) {
								int mean = ((int) Math.ceil((this.mTotalQuantum / this.mNumOfProcesses) * 0.1));
								tempProcess.setmQuantum(tempProcess.getmQuantum() + mean);
								this.mTotalQuantum += mean;
							}
							/// Case 2: The running process didn’t use all its quantum time based on another
							/// process converted from ready to running
							else {
								int remainingQT = halfQT - i;
								tempProcess.setmQuantum(tempProcess.getmQuantum() + remainingQT);
								this.mTotalQuantum += remainingQT;
								queue = false;
								break;
							}
						} else {
							/// Case 1: The running process used all its quantum time and it still have job
							/// to do.
							if (i == halfQT - 1) {
								int mean = ((int) Math.ceil((this.mTotalQuantum / this.mNumOfProcesses) * 0.1));
								tempProcess.setmQuantum(tempProcess.getmQuantum() + mean);
								this.mTotalQuantum += mean;
								this.readyQueue.add(tempProcess);
								queue = true;
							}
							tempProcess.setmRemainingTime(tempProcess.getmRemainingTime() - 1);
							time++;
						}
					}
				}

				for (int i = 0; i < this.mProcesses.size(); i++) {
					if (this.mProcesses.get(i).getmName().equals(tempProcess.getmName())) {
						this.mProcesses.set(i, tempProcess);
					}
				}

				for (int i = 0; i < this.mTemp.size(); i++) {
					if (this.mTemp.get(i).getmName().equals(tempProcess.getmName())) {
						this.mTemp.set(i, tempProcess);
					}
				}

			} else if (this.mTemp.size() == 1 && this.readyQueue.size() == 0) {
				System.out.println("Process " + this.mTemp.get(0).getmName() + " running.");
				if (this.mTemp.get(0).getmRemainingTime() == 0) {
					this.mTemp.get(0).setmTurnAroundTime(time - this.mTemp.get(0).getmArrivalTime());
					mTotalQuantum -= this.mTemp.get(0).getmQuantum();
					this.mTemp.get(0).setmQuantum(0);
					this.mDeadList.add(this.mTemp.get(0));
					for (int j = 0; j < this.mTemp.size(); j++) {
						if (this.mTemp.get(j).getmAGFactor() == this.mTemp.get(0).getmAGFactor()) {
							this.mTemp.remove(j);
							break;
						}
					}
				} else {
					time += this.mTemp.get(0).getmQuantum();

					this.mTemp.get(0).setmWaitingTime(time - this.mTemp.get(0).getmArrivalTime()
							+ this.mTemp.get(0).getmRemainingTime() - this.mTemp.get(0).getmBurstTime());
					this.mTemp.get(0).setmRemainingTime(this.mTemp.get(0).getmRemainingTime() - Math.min(
							(int) Math.ceil(this.mTemp.get(0).getmQuantum()), this.mTemp.get(0).getmRemainingTime()));
					int mean = ((int) Math.ceil((this.mTotalQuantum / this.mNumOfProcesses) * 0.1));
					this.mTemp.get(0).setmQuantum(this.mTemp.get(0).getmQuantum() + mean);
					this.mTotalQuantum += mean;

					if (lastProcess != this.mTemp.get(0).getmAGFactor()) {
						this.mOutput.add(this.mTemp.get(0));
						lastProcess = this.mTemp.get(0).getmAGFactor();
					}
				}
			} else {
				time++;
				System.out.print("\n");
			}
		}
		printExecutionOrder();
	}
}
