package hw2.test;

import hw2.PercolationFactory;
import hw2.PercolationStats;

public class PercolationStatsTest {
    public static void main(String[] arg) {
        PercolationFactory pf = new PercolationFactory();
        PercolationStats ps = new PercolationStats(20, 500, pf);
        System.out.println(ps.mean());
    }
}
