import java.util.NoSuchElementException;
import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;

public class RandomizedQueue<Item> implements Iterable<Item> {
  private int count;
  private int size;
  private Item[] array;
  public RandomizedQueue() {
    array = (Item[]) new Object[1];
    size = 1;
    count = 0;
  }// construct an empty randomized queue
  public boolean isEmpty() {
    return count==0;
  }// is the queue empty?
  public int size() {
    return count;
  }// return the number of items on the queue
  public void enqueue(Item item) {
    if (item==null) throw new NullPointerException();
    if (size==count) {
      Item[] newarray = (Item[]) new Object[size*2];
      for (int i = 0; i<size; i++) {
        newarray[i] = array[i];
      }
      array = newarray;
      size = size*2;
    }
    /*
    int i;
    while (true) {
      i = StdRandom.uniform(size);
      if (array[i]==null)
        break;
    }
    array[i] = item;
    */
    array[count] = item;
    count++;
  }// add the item
  public Item dequeue() {
    if (isEmpty()) throw new NoSuchElementException();
    if (count*4==size) {
      Item[] newarray = (Item[]) new Object[size/2];
      int j = 0;
      for (int i = 0; i<size; i++) {
        if (array[i]!=null) {
          newarray[j] = array[i];
          j++;
        }
      }
      array = newarray;
      size = size/2;
    }
    int rand = StdRandom.uniform(count);
    Item copy = array[rand];
    array[rand] = array[count-1];
    array[count-1] = null;
    count--;
    return copy;
  }// remove and return a random item
  public Item sample() {
    if (isEmpty()) throw new NoSuchElementException();
    int rand = StdRandom.uniform(count);
    return array[rand];
  }// return (but do not remove) a random item
  public Iterator<Item> iterator() { return new RandomizedQueueIterator(); }        // return an independent iterator over items in random order
  private class RandomizedQueueIterator implements Iterator<Item> {
    int[] order;
    int current;
    private RandomizedQueueIterator () {
      order = new int[count];
      for (int i = 0; i<count; i++) {
        order[i] = i;
      }
      if (count>1) {
        for (int i = count-1; i>=1; i--) {
          int j = StdRandom.uniform(i+1);
          int temp = order[j];
          order[j] = order[i];
          order[i] = temp;
        }
      }
      current = 0;
    }
    public boolean hasNext () {
      return current<count;
    }
    public void remove () {
      throw new UnsupportedOperationException ();
    }
    public Item next () {
      if (!hasNext()) throw new NoSuchElementException();
      Item item = array[order[current]];
      current++;
      return item;
    }
  }
}