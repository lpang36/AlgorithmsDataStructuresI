import edu.princeton.cs.algs4.WeightedQuickUnionUF;
//import java.lang.*;
//import java.util.*;

public class Percolation {
  private int counter;
  private int dim;
  private int[][] grid;
  private WeightedQuickUnionUF conn;
  public Percolation(int n) {
    counter = 0;
    if (n < 1)
      throw new java.lang.IllegalArgumentException();
    dim = n;
    grid = new int[n][n];
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        grid[i][j] = 0;
      }
    }
    conn = new WeightedQuickUnionUF((int)Math.pow(n,2)+2);  
    /*
    for (int i = 1; i<n+1; i++)
    {
      conn.union(0,i);
      conn.union((int)Math.pow(n,2)+1,(int)Math.pow(n,2)+1-i);
    }
    */
  }// create n-by-n grid, with all sites blocked
  public    void open(int row, int col) { 
    if (row < 1 || row > dim || col < 1 || col > dim)
      throw new java.lang.IndexOutOfBoundsException();
    if (grid[row-1][col-1]==0) 
      grid[row-1][col-1] = 1;
    else
      return;
    if (row == 1)
      conn.union(0,(row-1)*dim+col);
    if (row == dim)
      conn.union((int)Math.pow(dim,2)+1,(row-1)*dim+col);
    if (col-1 > 0 && grid[row-1][col-2] != 0)
      conn.union((row-1)*dim+col-1,(row-1)*dim+col);
    if (col < dim && grid[row-1][col] != 0)
      conn.union((row-1)*dim+col+1,(row-1)*dim+col);
    if (row-1 > 0 && grid[row-2][col-1] != 0)
      conn.union((row-2)*dim+col,(row-1)*dim+col);
    if (row < dim && grid[row][col-1] != 0)
      conn.union((row)*dim+col,(row-1)*dim+col);
    counter++;
  }// open site (row, col) if it is not open already
  public boolean isOpen(int row, int col) {
    if (row < 1 || row > dim || col < 1 || col > dim)
      throw new java.lang.IndexOutOfBoundsException();
    return grid[row-1][col-1]!=0;
  }// is site (row, col) open?
  public boolean isFull(int row, int col) {
    if (row < 1 || row > dim || col < 1 || col > dim)
      throw new java.lang.IndexOutOfBoundsException();
    return conn.connected(0,(row-1)*dim+col);
  }// is site (row, col) full?
  public int numberOfOpenSites() {
    return counter;
  }// number of open sites
  public boolean percolates() {
    return conn.connected(0,(int)Math.pow(dim,2)+1);
  }// does the system percolate?
}