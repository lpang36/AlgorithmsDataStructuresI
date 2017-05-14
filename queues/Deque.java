import java.util.NoSuchElementException;
import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
  private int size;
  private Node first;
  private Node last;
  public Deque() {
    size = 0;
    first = null;
    last = null;
  }// construct an empty deque
  private class Node {
    Item item;
    Node next;
    Node previous;
  }
  public boolean isEmpty() {
    return size==0;
  }// is the deque empty?
  public int size() {
    return size;
  }// return the number of items on the deque
  public void addFirst(Item item) {
    if (item==null) throw new NullPointerException();
    if (size==0) {
      first = new Node();
      first.item = item;
      first.next = null;
      first.previous = null;
      last = first;
    }
    else {
      Node oldFirst = first;
      first = new Node();
      first.item = item;
      first.next = oldFirst;
      first.previous = null;
      oldFirst.previous = first;
    }
    size++;
  }// add the item to the front
  public void addLast(Item item) {
    if (item==null) throw new NullPointerException();
    if (size==0) {
      first = new Node();
      first.item = item;
      first.next = null;
      first.previous = null;
      last = first;
    }
    else {
      Node oldLast = last;
      last = new Node();
      last.item = item;
      last.next = null;
      last.previous = oldLast;
      oldLast.next = last;
    }
    size++;
  }// add the item to the end
  public Item removeFirst() {
    if (isEmpty()) throw new NoSuchElementException();
    Item copy = first.item;
    first = first.next;
    size--;
    return copy;
  }// remove and return the item from the front
  public Item removeLast() {
    if (isEmpty()) throw new NoSuchElementException();
    Item copy = last.item;
    last = last.previous;
    size--;
    return copy;
  }// remove and return the item from the end
  public Iterator<Item> iterator() { return new DequeIterator(); }
  private class DequeIterator implements Iterator<Item> {
    private Node current = first;
    public boolean hasNext () {
      return current != null;
    }
    public void remove () {
      throw new UnsupportedOperationException ();
    }
    public Item next () {
      if (!hasNext()) throw new NoSuchElementException();
      Item item = current.item;
      current = current.next;
      return item;
    }
  }// return an iterator over items in order from front to end
}