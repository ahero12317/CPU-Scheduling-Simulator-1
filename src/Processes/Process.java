package Processes;

public class Process implements IProcess {
	String mName;
	int mArrivalTime;
	int mBurstTime;
	int mWaitingTime;
	int mTurnAroundTime;
	int mRemainingTime; //for SJF and AG scheduling.
	String mColour; 	//for GUI. 
	int mPriority; 		//for priority and AG scheduling.
	int mQuantum;  		//for AG scheduling.
	
	///Constructor.
	public Process(){}
	Process(String name, int arrival, int burst, String colour, int quantum, int priority ){	
		this.mName = name;
		this.mArrivalTime = arrival;
		this.mBurstTime = burst;
		this.mColour = colour;
		this.mPriority = priority;
		this.mQuantum = quantum;
		this.mWaitingTime = -1;
		this.mRemainingTime = -1;
		this.mTurnAroundTime = -1;	
	}
	
	//Mutators and accessors.
	public String getmName() {return mName;}
	public void setmName(String mName) {this.mName = mName;}
	
	public int getmArrivalTime() {return mArrivalTime;}
	public void setmArrivalTime(int mArrivalTime) {this.mArrivalTime = mArrivalTime;}
	
	public int getmBurstTime() {return mBurstTime;}
	public void setmBurstTime(int mBurstTime) {this.mBurstTime = mBurstTime;}
	
	public int getmWaitingTime() {return mWaitingTime;}
	public void setmWaitingTime(int mWaitingTime) {this.mWaitingTime = mWaitingTime;}
	
	public int getmTurnAroundTime() {return mTurnAroundTime;}
	public void setmTurnAroundTime(int mTurnAroundTime) {this.mTurnAroundTime = mTurnAroundTime;}
	
	public int getmRemainingTime() {return mRemainingTime;}
	public void setmRemainingTime(int mRemainingTime) {this.mRemainingTime = mRemainingTime;}
	
	public String getmColour() {return mColour;}
	public void setmColour(String mColour) {this.mColour = mColour;}
	
	public int getmPriority() {return mPriority;}
	public void setmPriority(int mPriority) {this.mPriority = mPriority;}
	
	public int getmQuantum() {return mQuantum;}
	public void setmQuantum(int mQuantum) {this.mQuantum = mQuantum;}
	
	
}
