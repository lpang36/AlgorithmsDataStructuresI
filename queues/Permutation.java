import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import java.util.NoSuchElementException;
import java.util.Iterator;

public class Permutation {
  public static void main(String[] args) {
    int k = Integer.parseInt(args[0]);
    RandomizedQueue<String> RQ = new RandomizedQueue<String> ();
    String[] array = StdIn.readAllLines();
    for (int i = 0; i<array.length; i++) {
      RQ.enqueue(array[i]);
    }
    /*
    while (StdIn.hasNextChar()) {
      StdOut.println("hello");
      RQ.enqueue(StdIn.readString()); 
      if (StdIn.readLine().equals("")) {
        System.exit(0);
        break;
      }
    }
    */
    Iterator I = RQ.iterator();
    for (int i = 0; i<k; i++) {
      try {
        StdOut.println(I.next());
      } catch (NoSuchElementException e) {
      }
    }
  }
}