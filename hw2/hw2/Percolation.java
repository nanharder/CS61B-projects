package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int size;
    private int numberofopensites;
    private WeightedQuickUnionUF gridindex;
    private WeightedQuickUnionUF gridindexWithoutBottom;
    private int topindex;
    private int bottomindex;
    private boolean[] grid;

    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N) {
        if (N <= 0) {
            throw new java.lang.IllegalArgumentException("N is illegal");
        } else {
            size = N;
            numberofopensites = 0;
            gridindex = new WeightedQuickUnionUF(size * size + 2);
            gridindexWithoutBottom = new WeightedQuickUnionUF(size * size + 1);
            topindex = size * size;
            bottomindex = size * size + 1;
            grid = new boolean[size * size];
        }
    }

    private boolean checkPosition(int row, int col) {
        return (row >= 0 & row < size & col >= 0 & col < size);
    }

    // translate xy into index of sets
    private int xyToindex(int x, int y) {
        if (checkPosition(x, y)) {
            return x * size + y;
        } else {
            throw new java.lang.IndexOutOfBoundsException();
        }
    }

    private void connectToOpenNeighbor(int cur, int row, int col) {
        if (checkPosition(row, col)) {
            if (isOpen(row, col)) {
                int neighbor = xyToindex(row, col);
                gridindex.union(cur, neighbor);
                gridindexWithoutBottom.union(cur, neighbor);
            }
        }
    }

    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        int index = xyToindex(row, col);
        if (!grid[index]) {
            grid[index] = true;
            numberofopensites += 1;
            connectToOpenNeighbor(index, row - 1, col);
            connectToOpenNeighbor(index, row + 1, col);
            connectToOpenNeighbor(index, row, col - 1);
            connectToOpenNeighbor(index, row, col + 1);
        }

        if (row == 0) {
            gridindex.union(index, topindex);
            gridindexWithoutBottom.union(index, topindex);
        }

        if (row == size - 1) {
            gridindex.union(index, bottomindex);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        int index = xyToindex(row, col);
        return grid[index];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        int index = xyToindex(row, col);
        return isOpen(row, col) & gridindexWithoutBottom.connected(topindex, index);
    }

    // number of open sites
    public int numberOfOpenSites() {
        return numberofopensites;
    }

    // does the system percolate?
    public boolean percolates() {
        return  gridindex.connected(topindex, bottomindex);
    }

    // use for unit testing (not required, but keep this here for the autograder)
    public static void main(String[] args) {
        Percolation test = new Percolation(3);
        test.open(2, 1);
        test.isFull(2, 1);
    }

}
