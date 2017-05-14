import java.util.Iterator;
import java.lang.Math;
import java.util.NoSuchElementException;
import java.util.Comparator;
import java.util.Scanner;

public class Board {
  private int[][] grid;
  private int zeroi;
  private int zeroj;
  private int size;
  
  public Board(int[][] blocks) {
    size = blocks.length;
    grid = new int[size][size];
    outerloop:
    for (int i = 0; i<size; i++) {
      for (int j = 0; j<size; j++) {
        grid[i][j] = blocks[i][j];
        if (blocks[i][j]==0) {
          zeroi = i;
          zeroj = j;
          //break outerloop;
        }
      }
    }
  }// construct a board from an n-by-n array of blocks
  /*                                         // (where blocks[i][j] = block in row i, column j)
  private Board(Board b) {
    grid = b.grid.clone();
    zeroi = b.zeroi;
    zeroj = b.zeroj;
    size = b.size;
  }
  */
  public int dimension() {
    return size;
  }// board dimension n
  public int hamming() {
    int sum = 0;
    for (int i = 0; i<size; i++) {
      for (int j = 0; j<size; j++) {
        if (grid[i][j]!=0&&grid[i][j]!=(i*size+j+1)%(size*size))
          sum++;
      }
    }
    return sum;
  }// number of blocks out of place
  public int manhattan() {
    int sum = 0;
    for (int i = 0; i<size; i++) {
      for (int j = 0; j<size; j++) {
        if (grid[i][j]!=0) {
          int idist = Math.abs(i-(grid[i][j]-1)/size);
          int jdist = Math.abs(j-(grid[i][j]-1)%size);
          //System.out.println(idist+" "+jdist);
          sum = sum+idist+jdist;
        }
      }
    }
    return sum;
  }// sum of Manhattan distances between blocks and goal
  public boolean isGoal() {
    return manhattan()==0;
  }// is this board the goal board?
  public Board twin() {
    /*
    if (size>2) {
      int count = 0;
      for (int i = 0; i<size-1; i++) {
        if (grid[i][0]!=0&&grid[i+1][0]!=0) {
          count = i;
          exch(i,0,i+1,0);
          break;
        }
      }
      Board B = new Board(grid);
      exch(count,0,count+1,0);
      return B;
    }
    */
    //else {
      if (grid[0][0]!=0&&grid[0][1]!=0) {
        exch(0,0,0,1);
        Board B = new Board(grid);
        exch(0,0,0,1);
        return B;
      }
      else {
        exch(1,0,1,1);
        Board B = new Board(grid);
        exch(1,0,1,1);
        return B;
      }
    //}
  }// a board that is obtained by exchanging any pair of blocks
  public boolean equals(Object y) {
    if (y==null) {
      return false;
    }
    if (y instanceof String) {
      return y.equals(this.toString());
    }
    Board by = (Board)y;
    if (by.size!=this.size) {
      return false;
    }
    for (int i = 0; i<size; i++) {
      for (int j = 0; j<size; j++) {
        if (grid[i][j]!=by.grid[i][j])
          return false;
      }
    }
    return true;
  }// does this board equal y?
  /*
  public Comparable<Board> order() {
    return new Comparable<Board>() {
      public Comparator<Board> comparator() {
        return new Comparator<Board>() {
          public int compareTo(Board B) {
            return this.hamming()-B.hamming();
          }
        };
      }
    };
  }
  */
  public Iterable<Board> neighbors() {
    return new Iterable<Board>() {
      public Iterator<Board> iterator() {
        return new Iterator<Board>() {
          private int pos = 0;
          private int count = 0;
          private Board[] list = new Board[4];
          //private int pos=0;
          {
            //count = 0;
            //list = new Board[4];
            try {
              exch(zeroi,zeroj,zeroi+1,zeroj);
              list[count] = new Board(grid);
              //System.out.println(list[count].toString());
              exch(zeroi,zeroj,zeroi+1,zeroj);
              //System.out.println(list[count].toString());
              count++;
            }
            catch (ArrayIndexOutOfBoundsException e) {};
            try {
              exch(zeroi,zeroj,zeroi-1,zeroj);
              list[count] = new Board(grid);
              exch(zeroi,zeroj,zeroi-1,zeroj);
              count++;
            }
            catch (ArrayIndexOutOfBoundsException e) {};
            try {
              exch(zeroi,zeroj,zeroi,zeroj+1);
              list[count] = new Board(grid);
              exch(zeroi,zeroj,zeroi,zeroj+1);
              count++;
            }
            catch (ArrayIndexOutOfBoundsException e) {};
            try {
              exch(zeroi,zeroj,zeroi,zeroj-1);
              list[count] = new Board(grid);
              exch(zeroi,zeroj,zeroi,zeroj-1);
              count++;
            }
            catch (ArrayIndexOutOfBoundsException e) {};
            //System.out.println(count+" "+pos);
            //System.out.println(list[1].toString());
            /*
            for (int i = 0; i<list.length; i++) {
              if (list[i]==null) {
                System.out.println("null");
                continue;
              }
              System.out.println (list[i].toString());
            }
            */
          }
          public boolean hasNext() {
            return pos<count&&list[pos]!=null;
          }
          public Board next() {
            if (!hasNext()) {
              throw new NoSuchElementException();
            }
            Board B = list[pos];
            pos++;
            //System.out.println(pos);
            return B;
          }
        };
      }
    };
  }// all neighboring boards
  public String toString() {
    String s = "\n"+size+"\n";
    for (int i = 0; i<size; i++) {
      for (int j = 0; j<size; j++) {
        s = s+" "+grid[i][j];
      }
      s = s+"\n";
    }
    return s;
  }// string representation of this board (in the output format specified below)

    private void exch (int a, int b, int c, int d) {
      int temp = grid[a][b];
      grid[a][b] = grid[c][d];
      grid[c][d] = temp;
    }
    
    private Board copy () {
      return new Board (grid);
    }
    public static void main(String[] args) {
      Scanner s = new Scanner(System.in);
     int size = s.nextInt();
    int[][] grid = new int[size][size];
    for (int i = 0; i<size; i++) {
      for (int j = 0; j<size; j++) {
        grid[i][j] = s.nextInt();
      }
    }
    Board B = new Board(grid);
    System.out.println(B.isGoal());
          Iterator<Board> N = B.neighbors().iterator();
          //for (int i = 0; i<4; i++) {
              for (int i = 0; i<4; i++) { 
          try {
          Board C = N.next();
          System.out.println(C.toString());

      }
      catch (NoSuchElementException e) {};
          }
    }
}