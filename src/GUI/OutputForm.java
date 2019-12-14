package GUI;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.IntervalCategoryDataset;
import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.GanttCategoryDataset;
import org.jfree.data.gantt.TaskSeriesCollection;
import org.jfree.data.time.SimpleTimePeriod;

import Processes.Process;

public class OutputForm extends JFrame {

	private static final long serialVersionUID = 1L;
	private ArrayList<Process> mProcesses;
	private ArrayList<Integer> mTimeline;
	private ArrayList<Process> mOrder;

	public OutputForm(ArrayList<Process> Processes, ArrayList<Process> Order, ArrayList<Integer> Timeline) {
		super("CPU-Scheduling Simulator");

		this.mOrder = Order;
		this.mProcesses = Processes;
		this.mTimeline = Timeline;

		// Create dataset
		final GanttCategoryDataset dataset = getCategoryDataset();

		// Create chart
		JFreeChart chart = ChartFactory.createGanttChart("CPU-Scheduling Simulator", // Chart title
				"Processes", // X-Axis Label
				"Timeline", // Y-Axis Label
				dataset);
		CategoryPlot plot = chart.getCategoryPlot();
		DateAxis axis = (DateAxis) plot.getRangeAxis();
		axis.setDateFormatOverride(new SimpleDateFormat("SSS"));
		axis.setMaximumDate(new Date(50));

		ChartPanel panel = new ChartPanel(chart);
		setContentPane(panel);
	}

	private GanttCategoryDataset getCategoryDataset() {
		TaskSeriesCollection dataset = new TaskSeriesCollection();
		for (int i = 0; i < this.mProcesses.size(); i++) {
			TaskSeries series1 = new TaskSeries("P" + (i + 1)); 
			Task t1 = new Task(" ", new SimpleTimePeriod(this.mTimeline.get(2 * i), this.mTimeline.get(2 * i + 1)));
			ArrayList<Task> temp = new ArrayList<>();
			temp.clear();
			for (int j = 0; j < this.mOrder.size() - 1; j++) {
				if (mOrder.get(j).getmName().equals("p" + String.valueOf(i + 1))) {
					temp.add(new Task(" ",
							new SimpleTimePeriod(this.mTimeline.get(2 * j), this.mTimeline.get(2 * j + 1))));
				}
			}
			for (int j = 0; j < temp.size(); j++) {
				t1.addSubtask(temp.get(j));
			}
			series1.add(t1);
			dataset.add(series1);
		}
		return dataset;
	}

}