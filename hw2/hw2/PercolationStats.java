package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;


public class PercolationStats {
    private final int size;
    private final int times;
    private final double[] data;

    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0) {
            throw new IllegalArgumentException("N must larger than 0, and the input is " + N);
        }
        if (T <= 0) {
            throw new IllegalArgumentException("T must larger than 0, and the input is " + T);
        }
        size = N;
        times = T;
        data = new double[times];
        simulation(pf);
    }

    public double mean() {
        return StdStats.mean(data);
    }

    public double stddev() {
        return StdStats.stddev(data);
    }

    public double confidenceLow() {
        return mean() - (1.96 * stddev() / Math.sqrt(times));
    }

    public double confidenceHigh() {
        return mean() + (1.96 * stddev() / Math.sqrt(times));
    }

    /**
     * simulate for T times
     * store these data in the array
     */
    private void simulation(PercolationFactory pf) {
        for (int i = 0; i < times; i++) {
            Percolation percolation = pf.make(size);
            while (!percolation.percolates()) {
                int row = StdRandom.uniform(0, size);
                int col = StdRandom.uniform(0, size);
                while (percolation.isOpen(row, col)) {
                    row = StdRandom.uniform(0, size);
                    col = StdRandom.uniform(0, size);
                }
                percolation.open(row, col);
            }
            data[i] = (double) percolation.numberOfOpenSites() / size * size;
        }
    }
}
