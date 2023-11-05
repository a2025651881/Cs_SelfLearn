package hw2;

import hw2.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF unionUF;
    private boolean[] state;
    private int rowNum;
    private int size;

    public Percolation(int N) // create N-by-N grid, with all sites initially blocked
    {
        unionUF = new WeightedQuickUnionUF(N * N);
        state = new boolean[N * N];
        rowNum = N;
        size = 0;
    }

    public void open(int row, int col) // open the site (row, col) if it is not open already
    {
        int index = rowNum * row + col;
        if (!isOpen(row, col))
            size++;
        // not the first row and the upper site is open
        if (row > 0 && isOpen(row - 1, col))
            unionUF.union(index, index - rowNum);
        // not the last row and the below site is open
        if (row < rowNum - 1 && isOpen(row + 1, col))
            unionUF.union(index, index + rowNum);
        // not the first column and the left site is open
        if (col > 0 && isOpen(row, col - 1))
            unionUF.union(index, index - 1);
        // not the last column and the right site is open
        if (col < rowNum - 1 && isOpen(row, col + 1))
            unionUF.union(index, index + 1);
        state[index] = true;
    }

    public boolean isOpen(int row, int col) // is the site (row, col) open?
    {
        return state[rowNum * row + col];
    }

    public boolean isFull(int row, int col) // is the site (row, col) full?
    {
        for (int i = 0; i < rowNum; i++)
            if (isOpen(row, col) && unionUF.find(i) == unionUF.find(row * rowNum + col)) {
                return true;
            }
        return false;
    }

    public int numberOfOpenSites() // number of open sites
    {
        return size;
    }

    public boolean percolates() // does the system percolate?
    {
        for (int i = 0; i < rowNum - 1; i++)
            if (isFull(rowNum - 1, i))
                return true;
        return false;
    }
}
