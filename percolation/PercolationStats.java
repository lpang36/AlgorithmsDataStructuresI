import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
//import java.lang.*;
//import java.util.*;

public class PercolationStats {
  private double[] values;
  private int T;
  public PercolationStats(int n, int trials) {
    if (n<1)
      throw new java.lang.IllegalArgumentException();
    if (trials<1)
      throw new java.lang.IllegalArgumentException();
    T = trials;
    values = new double[trials];
    for (int i = 0; i<trials; i++) {
      Percolation P = new Percolation(n);
      for (int j = 0; j>-1; j++) {
        int row = StdRandom.uniform(1,n+1);
        int col = StdRandom.uniform(1,n+1);
        P.open(row,col);
        if (P.percolates()) {
          values[i] = P.numberOfOpenSites()/Math.pow(n,2);
          break;
        }
      }
    }
  }// perform trials independent experiments on an n-by-n grid
  public double mean() {
    return StdStats.mean(values);
  }// sample mean of percolation threshold
  public double stddev() {
    return StdStats.stddev(values);
  }// sample standard deviation of percolation threshold
  public double confidenceLo() {
    return this.mean()-1.96*this.stddev()/Math.sqrt(T);
  }// low  endpoint of 95% confidence interval
  public double confidenceHi() {
    return this.mean()+1.96*this.stddev()/Math.sqrt(T);
  }// high endpoint of 95% confidence interval
  public static void main(String[] args) {
    PercolationStats PS = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
    System.out.println("mean                    = "+PS.mean());
    System.out.println("stddev                  = "+PS.stddev());
    System.out.println("95% confidence interval = ["+PS.confidenceLo()+", "+PS.confidenceHi()+"]");
  }// test client (described below)
}