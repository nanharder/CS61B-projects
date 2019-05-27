package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {
    private double mean;
    private double stddev;
    private double confidenceLow;
    private double confidenceHigh;

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N < 0 || T <= 0) {
            throw new java.lang.IllegalArgumentException();
        }

        double[] numberOfOpenSites = new double[T];
        for (int i = 0; i < T; i += 1) {
            Percolation test = pf.make(N);
            while (!test.percolates()) {
                openRandomSite(N, test);
            }
            numberOfOpenSites[i] = (double) test.numberOfOpenSites() / (N * N);
        }
        mean = StdStats.mean(numberOfOpenSites);
        stddev = StdStats.stddev(numberOfOpenSites);
        confidenceLow = mean - 1.96 * stddev / Math.sqrt(T);
        confidenceHigh = mean + 1.96 * stddev / Math.sqrt(T);
    }

    private static void openRandomSite(int size, Percolation p) {
        int row, col;
        do {
            row = StdRandom.uniform(size);
            col = StdRandom.uniform(size);
        } while (p.isOpen(row, col));
        p.open(row, col);
    }

    // sample mean of percolation threshold
    public double mean() {
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        return confidenceLow;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        return confidenceHigh;
    }

}
