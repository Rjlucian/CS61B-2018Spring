package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int num;
    private final boolean[][] grid;
    // used for check percolated or not
    private final WeightedQuickUnionUF ufForPercolated;
    // used for prevent "backwash"
    private final WeightedQuickUnionUF ufForConnected;

    /**
     * @param N size of the grid is N * N
     * there is another two virtual grid "at" the top and bottom
     * so the size of uf is N * N + 2
     */
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException(" N must larger than 0");
        }
        num = 0;
        grid = new boolean[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                grid[i][j] = false;
            }
        }
        ufForPercolated = new WeightedQuickUnionUF(N * N + 2);
        ufForConnected = new WeightedQuickUnionUF(N * N + 1);
    }

    /**
     * open a site, update its state
     * if this site's state change to full
     * update the state of sites near this site
     * @param row index of site's row
     * @param col index of sire's column
     */
    public void open(int row, int col) {
        validate(row);
        validate(col);
        if (!grid[row][col]) {
            // add num only one time for every site
            num++;
        }
        grid[row][col] = true;
        updateSelf(row, col);
        updateSurrounding(row, col);
    }

    /**
     * @param row index of site's row
     * @param col index of site's column
     * @return  site(row, column) is open or not
     */
    public boolean isOpen(int row, int col) {
        validate(row);
        validate(col);
        return grid[row][col];
    }

    /**
     * @param row index of site's row
     * @param col index of site's column
     * @return site(row, column) is open or not
     */
    public boolean isFull(int row, int col) {
        if (!isOpen(row, col)) {
            return false;
        } else {
            return ufForPercolated.connected(0, xyTo1D(row, col))
                    && ufForConnected.connected(0, xyTo1D(row, col));
        }
    }

    /**
     * @return the number of open sites in grid
     */
    public int numberOfOpenSites() {
        return num;
    }

    public boolean percolates() {
        return ufForPercolated.connected(0, grid.length * grid.length + 1);
    }

    /**
     * @param row index of site's row
     * @param column index of site's column
     * @return map a site in grids to uf
     */
    private int xyTo1D(int row, int column) {
        return row * grid.length + column + 1;
    }

    /**
     * @param n the index of row or col
     * if n is illegal, throwing an exception
     */
    private void validate(int n) {
        if (n < 0 || n >= grid.length) {
            throw new IndexOutOfBoundsException("the index should between 0 and "
                    + (grid.length - 1)
                    + ", but input index is " + n);
        }
    }

    /**
     * @param row index of site's row
     * @param col index of site's column
     * @return site(row, col) is in the bound or not
     */
    private boolean inBound(int row, int col) {
        return row >= 0 && row < grid.length && col >= 0 && col < grid.length;
    }

    /**
     * change site(row, col) only!!!
     * @param row index of site's row
     * @param col index of site's column
     */
    private void updateSelf(int row, int col) {
        if (row == 0) {
            // if row equals to 0, connect it with 0th element immediately
            ufForPercolated.union(0, xyTo1D(row, col));
            ufForConnected.union(0, xyTo1D(row, col));
            return;
        }
        if (inBound(row, col - 1) && isFull(row, col - 1)) { //left
            ufForPercolated.union(0, xyTo1D(row, col));
            ufForConnected.union(0, xyTo1D(row, col));
            return;
        }
        if (inBound(row - 1, col) && isFull(row - 1, col)) { //up
            ufForPercolated.union(0, xyTo1D(row, col));
            ufForConnected.union(0, xyTo1D(row, col));
            return;
        }
        if (inBound(row, col + 1) && isFull(row, col + 1)) { //right
            ufForPercolated.union(0, xyTo1D(row, col));
            ufForConnected.union(0, xyTo1D(row, col));
            return;
        }
        if (inBound(row + 1, col) && isFull(row + 1, col)) { //down
            ufForPercolated.union(0, xyTo1D(row, col));
            ufForConnected.union(0, xyTo1D(row, col));
        }
    }

    /**
     * change the state of sites near the site(row col)
     * @param row index of site's row
     * @param col index of site's column
     *
     */
    private void updateSurrounding(int row, int col) {
        if (inBound(row, col - 1) && isOpen(row, col - 1)) {
            // left
            ufForPercolated.union(xyTo1D(row, col), xyTo1D(row, col - 1));
            ufForConnected.union(xyTo1D(row, col), xyTo1D(row, col - 1));
        }
        if (inBound(row - 1, col) && isOpen(row - 1, col)) {
            // up
            ufForPercolated.union(xyTo1D(row, col), xyTo1D(row - 1, col));
            ufForConnected.union(xyTo1D(row, col), xyTo1D(row - 1, col));
        }
        if (inBound(row, col + 1) && isOpen(row, col + 1)) {
            // right
            ufForPercolated.union(xyTo1D(row, col), xyTo1D(row, col + 1));
            ufForConnected.union(xyTo1D(row, col), xyTo1D(row, col + 1));
        }
        if (inBound(row + 1, col) && isOpen(row + 1, col)) {
            // down
            ufForPercolated.union(xyTo1D(row, col), xyTo1D(row + 1, col));
            ufForConnected.union(xyTo1D(row, col), xyTo1D(row + 1, col));
        }
        if (row == grid.length - 1) {
            ufForPercolated.union(grid.length * grid.length + 1, xyTo1D(row, col));
        }
    }

    public static void main(String[] args) {
        Percolation percolation = new Percolation(3);
        percolation.open(0, 0);
        percolation.open(1, 0);
        percolation.open(2, 0);
        percolation.open(2, 1);
        System.out.print(percolation.percolates());
    }
}
