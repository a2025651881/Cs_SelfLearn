package hw2;

import java.lang.IllegalArgumentException;

import hw2.lib.StdRandom;
import hw2.lib.StdStats;

public class PercolationStats {
    private int count;
    private Percolation percolation;
    private int rowNum;
    private double[] datas;

    public PercolationStats(int N, int T, PercolationFactory pf) // perform T independent experiments on an N-by-N grid
    {
        if (N <= 0 || T <= 0)
            throw new IllegalArgumentException("ArgumentException!!");
        count = T;
        rowNum = N;
        datas = new double[T];
        for (int i = 0; i < T; i++) {
            percolation = pf.make(N);
            while (!percolation.percolates()) {
                // Choose a site uniformly at random among all blocked sites.
                int row, col;
                while (true) {
                    row = StdRandom.uniformInt(0, N);
                    col = StdRandom.uniformInt(0, N);
                    if (!percolation.isOpen(row, col))
                        break;
                }
                percolation.open(row, col);
            }
            datas[i] = (double) percolation.numberOfOpenSites() / (rowNum * rowNum);
        }
    }

    public double mean() // sample mean of percolation threshold
    {
        return StdStats.mean(datas);
    }

    public double stddev() // sample standard deviation of percolation threshold
    {
        return StdStats.stddev(datas);
    }

    public double confidenceLow() // low endpoint of 95% confidence interval
    {
        return mean() - 1.96 * stddev() / Math.sqrt(count);
    }

    public double confidenceHigh() // high endpoint of 95% confidence interval
    {
        return mean() + 1.96 * stddev() / Math.sqrt(count);
    }
}
