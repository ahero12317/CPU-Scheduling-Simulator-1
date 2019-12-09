package Processes;

public interface IProcess {
	public String getmName();
	public void setmName(String mName);
	
	public int getmArrivalTime();
	public void setmArrivalTime(int mArrivalTime);
	
	public int getmBurstTime();
	public void setmBurstTime(int mBurstTime);
	
	public int getmWaitingTime();
	public void setmWaitingTime(int mWaitingTime);
	
	public int getmTurnAroundTime();
	public void setmTurnAroundTime(int mTurnAroundTime);
	
	public int getmCompletionTime();
	public void setmCompletionTime(int mCompletionTime);
	
	public int getmRemainingTime();
	public void setmRemainingTime(int mRemainingTime);
	
	public String getmColour();
	public void setmColour(String mColour);
	
	public int getmPriority();
	public void setmPriority(int mPriority);
	
	public int getmQuantum();
	public void setmQuantum(int mQuantum);
}
