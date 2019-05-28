import edu.princeton.cs.introcs.StdRandom;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

/**
 * Created by hug.
 */
public class Experiments {
    public static void experiment1() {
        BST<Double> bst = new BST();
        ExperimentHelper help = new ExperimentHelper();
        double[] xData = new double[5000];
        double[] yData = new double[5000];
        double[] optimalyData = new double[5000];
        for (int i = 0; i < 5000; i += 1) {
            double item = StdRandom.uniform();
            bst.add(item);
            xData[i] = (double) i;
            yData[i] = bst.getAverageDepth();
            optimalyData[i] = help.optimalAverageDepth(i + 1);
        }
        XYChart chart = QuickChart.getChart("Average depth depend on the number of items", "Number of items", "Average Depth", "average depth", xData, optimalyData);
        chart.addSeries("experiment", xData, yData);
        new SwingWrapper(chart).displayChart();
    }

    public static void experiment2() {
    }

    public static void experiment3() {
    }

    public static void main(String[] args) {
        experiment1();
    }
}
