import java.util.Scanner;
import Algorithms.AG;
import Algorithms.Priority;
import Algorithms.SJF;
import Algorithms.SRTF;


public class Main {
	public static void main(String[] args) {
		boolean exit = true;
		while (exit) {
			System.out.println("Choose algorithm :");
			System.out.println("[0]Non-Preemptive shortest job first.	[1]Shortest remaining time first scheduling.");
			System.out.println("[2]Non-Preemptive priority scheduling.	[3]AG Scheduling.");
			System.out.println("[99]Exit \n");
			System.out.print("Input: ");
			
			int choice = (new Scanner(System.in)).nextInt();
			switch (choice) {
				case 0:
					new SJF().Simulate();
					break;
				case 1:
					new SRTF().Simulate();
					break;
				case 2:
					new Priority().Simulate();
					break;
				case 3:
					new AG().Simulate();
					break;
				case 99:
					exit = false;
					break;
				default:
					System.out.println("Invalid Input, please try again.");
					break;
			}
		}
	}
}
