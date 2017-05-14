import edu.princeton.cs.algs4.*;
import java.util.NoSuchElementException;
import java.util.Iterator;

public class PointSET {
  private SET<Point2D> rbt;
  public         PointSET() {
    rbt = new SET<Point2D>();
  }// construct an empty set of points 
  public           boolean isEmpty() {
    return rbt.size()==0;
  }// is the set empty? 
  public               int size() {
    return rbt.size();
  }// number of points in the set 
  public              void insert(Point2D p) {
    rbt.add(p);
  }// add the point to the set (if it is not already in the set)
  public           boolean contains(Point2D p) {
    return rbt.contains(p);
  }// does the set contain point p? 
  public              void draw() {
    for (Point2D p : rbt) {
      p.draw();
    }
  }// draw all points to standard draw 
  public Iterable<Point2D> range(RectHV rect) {
    Point2D[] list = new Point2D[this.size()];
    int temp = 0;
    for (Point2D p : rbt) {
      if (rect.distanceTo(p)==0) {
        list[temp] = p;
        temp++;
      }
    }
    final int count = temp;
    return new Iterable<Point2D> () {
      public Iterator<Point2D> iterator() {
        return new Iterator<Point2D>() {
          int newCount = 0;
          public boolean hasNext() {
            return newCount<count;
          }
          public Point2D next() {
            if (!hasNext()) {
              throw new NoSuchElementException();
            }
            newCount++;
            return list[newCount-1];
          }
        };
      }
    };
  }// all points that are inside the rectangle 
/*
      private class Node () {
        Point2D p;
        Point2D next;
        Node (Point np) {
          p = np;
          next = null;
        }
        void addNext (Point2D nextp) {
          next = nextp;
        }
        Point2D getNext () {
          return next;
        }
    }
    */
  public           Point2D nearest(Point2D p) {
    double min = 2;
    Point2D output = null;
    for (Point2D test : rbt) {
      if (test.distanceTo(p)<min) {
        min = test.distanceTo(p);
        output = test;
      }
    }
    return output;
  }// a nearest neighbor in the set to point p; null if the set is empty 

   //public static void main(String[] args)                  // unit testing of the methods (optional) 
}