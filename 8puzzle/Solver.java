import edu.princeton.cs.algs4.*;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Iterator;
import java.util.Comparator;

public class Solver {
  private MinPQ<Move> pq;
  private MinPQ<Move> pqtwin;
  private Board[] list;
  //int moves;
  private int count;
  private int size;
  private boolean unsolvable;
  public Solver(Board initialB) {
    //Move prev = null;
    //Move prevtwin = null;
    list = new Board[1];
    size = 1;
    //moves = 0;
    count = 0;
    unsolvable = false;
    /*
    Comparable<Board> comp = new Comparable<Board> () {
      Comparator<Board> Comparator() {
        return new Comparator<Board>() {
          int compare (Board a, Board b) {
            return b.hamming()-a.hamming();
          }
        };
      }
    };
    */
    Move initial = new Move(initialB);
    Move twin = new Move(initialB.twin());
    //System.out.println(initial.board.toString());
    pq = new MinPQ<Move>();
    pqtwin = new MinPQ<Move>();
    pq.insert(initial);
    pqtwin.insert(twin);
    while (true) {
      Move dq = pq.delMin();
      Move dqtwin = pqtwin.delMin();
      //System.out.println(dq.board.toString());
      list[count] = dq.board; //POINTER ERRORS!
      count++;
      if (count==size) {
        Board[] listcopy = new Board[size*2];
        for (int i = 0; i<size; i++) {
          listcopy[i] = list[i];
        }
        size = size*2;
        list = listcopy;
      }
      if (dq.board.isGoal()) {
        break;
      }
      if (dqtwin.board.isGoal()) {
        unsolvable = true;
        break;
      }
      Iterator<Board> N = dq.board.neighbors().iterator(); 
              for (int i = 0; i<4; i++) { 
                try {
                  Board B = N.next();
                  //System.out.println(B.toString());
                  /*
                  if (prev==null) {
                    pq.insert(new Move(B,prev));
                  }
                  */
                  if (dq.prev==null||!B.equals(dq.prev.board)) {
                    Move M = new Move(B,dq);
                    //if (M==null||B==null)
                      //System.out.println("null");
                    pq.insert(M);
                  }
                }
                catch (NoSuchElementException e) {};
                //catch (NullPointerException e) {};
              }
      Iterator<Board> Ntwin = dqtwin.board.neighbors().iterator(); 
              for (int i = 0; i<4; i++) {
                try {
                  Board B = Ntwin.next();
                  /*
                  if (prevtwin==null) {
                    pqtwin.insert(new Move(B));
                  }
                  */
                  if (dqtwin.prev==null||!B.equals(dqtwin.prev.board)) {
                    pqtwin.insert(new Move(B,dqtwin));
                  }
                }
                      catch (NoSuchElementException e) {};
                      //catch (NullPointerException e) {};
              }
      //prev = dq;
      //prevtwin = dqtwin;
      
    }
  }// find a solution to the initial board (using the A* algorithm)
  public boolean isSolvable() {
    return !unsolvable;
  }// is the initial board solvable?
  public int moves() {
    if (unsolvable) 
      return -1;
    return count-1;
  }// min number of moves to solve initial board; -1 if unsolvable
  /*
  private Comparable<Board> order() {
    return new Comparable<Board> () {
      Comparator<Board> comparator() {
        return new comparator<Board>() {
          int compare (Board a, Board b) {
            return b.hamming()-a.hamming();
          }
        };
      }
    };
  }
  */
  private class Move implements Comparable<Move> {
    private Board board;
    private Move prev;
    private int numMoves;
    public Move(Board b) {
      board = b;
      numMoves = 0;
    }
    public Move(Board b, Move p) {
      board = b;
      prev = p;
      if (p==null) {
        numMoves = 0;
      }
      else {
        numMoves = p.numMoves+1;
      }
    }
    public int compareTo(Move m) {
      return board.manhattan()-m.board.manhattan()+numMoves-m.numMoves;
    }
  }
  public Iterable<Board> solution() {
    if (unsolvable)
      return null;
    else {
      return new Iterable<Board>() {
        public Iterator<Board> iterator() {
          return new Iterator<Board>() {
            int current = 0;
            public boolean hasNext() {
              return current<count;
            }
            public Board next() {
              if (!hasNext()) {
                throw new NoSuchElementException();
              }
              Board B = list[current]; 
              current++;
              return B;
            }
          };
        }
      };
    }
  }// sequence of boards in a shortest solution; null if unsolvable
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
    Solver S = new Solver(B);
    if (!S.isSolvable()) {
      System.out.println("No solution possible");
    }
    else {
      Iterator<Board> SI = S.solution().iterator();
      System.out.println("Minimum number of moves = "+S.moves());
      try {
        while (true) {
          System.out.print(SI.next().toString());
        }
      }
      catch (NoSuchElementException e) {};
    }
  }// solve a slider puzzle (given below)
}