import edu.princeton.cs.algs4.*;
import java.util.NoSuchElementException;
import java.util.Iterator;

public class KdTree {
  private int size;
  private Node root;
  private SearchNode first;
  private SearchNode last;
  private Node champion;
  //double dist;
  //boolean searchTrue;
  public         KdTree() {
    size = 0;
    root = null;
    first = null;
    last = null;
    champion = null;
    //dist = 0;
    //searchTrue = false;
  }// construct an empty set of points 
  public           boolean isEmpty() {
    return size==0;
  }// is the set empty? 
  public               int size() {
    return size;
  }// number of points in the set 
  public              void insert(Point2D p) {
    if (contains(p)) {
      return;
    }
    if (isEmpty()) {
      root = new Node(p, 0);
    }
    else {
      int parity = 0;
      Node current = root;
      Node last = null;
      boolean goesLeft = false;
      while (current!=null) {
        last = current;
        goesLeft = false;
        if (parity%2==0) {
          if (p.x()<current.point.x()) {
            current = current.left;
            goesLeft = true;
          }
          else {
            current = current.right;
          }
        }
        else {
          if (p.y()<current.point.y()) {
            current = current.left;
            goesLeft = true;
          }
          else {
            current = current.right;
          }
        }
        parity++;
      }
      current = new Node(p, parity);
      if (goesLeft) {
        last.left = current;
      }
      else {
        last.right = current;
      }
    }
    size++;
  }// add the point to the set (if it is not already in the set)
  public           boolean contains(Point2D p) {
    if (isEmpty()) {
      return false;
    }
    else {
      int parity = 0;
      Node current = root;
      while (current!=null&&!current.equals(p)) {
        if (parity%2==0) {
          if (p.x()<current.point.x()) {
            current = current.left;
          }
          else {
            current = current.right;
          }
        }
        else {
          if (p.y()<current.point.y()) {
            current = current.left;
          }
          else {
            current = current.right;
          }
        }
        parity++;
      }
      if (current==null) {
        return false;
      }
      return true;
    }
  }// does the set contain point p? 
  public              void draw() {
    recursiveDraw(root);
  }// draw all points to standard draw 
  private void recursiveDraw (Node n) {
    if (n==null) {
      return;
    }
    n.point.draw();
    recursiveDraw (n.left);
    recursiveDraw (n.right);
  }
  public Iterable<Point2D> range(RectHV rect) {
    //searchTrue = false;
    first = null;
    last = null;
    recursiveSearch (root, rect);
    return new Iterable<Point2D> () {
      public Iterator<Point2D> iterator() {
        return new Iterator<Point2D>() {
          SearchNode current = first;
          public boolean hasNext() {
            return current!=null;
          }
          public Point2D next() {
            if (!hasNext()) {
              throw new NoSuchElementException();
            }
            SearchNode temp = current;
            current = current.next;
            return temp.n.point;
          }
        };
      }
    };
  }// all points that are inside the rectangle 
  private void recursiveSearch (Node n, RectHV rect) {
    if (n==null) {
      return;
    }
    if (rect.distanceTo(n.point)==0) {
      if (last==null) {
        first = new SearchNode(n);
        last = first;
      }
      else {
        SearchNode temp = new SearchNode(n);
        last.next = temp;
        last = temp;
      }
    }
    if (n.parity%2==0) {
      if (rect.xmax()<n.point.x()) {
        recursiveSearch (n.left, rect);
      }
      else if (rect.xmin()>n.point.x()) {
        recursiveSearch (n.right, rect);
      }
      else {
        recursiveSearch (n.left, rect);
        recursiveSearch (n.right, rect);
      }
    }
    else {
      if (rect.ymax()<n.point.y()) {
        recursiveSearch (n.left, rect);
      }
      else if (rect.ymin()>n.point.y()) {
        recursiveSearch (n.right, rect);
      }
      else {
        recursiveSearch (n.left, rect);
        recursiveSearch (n.right, rect);
      }
    }
  }
  private class SearchNode {
    Node n;
    SearchNode next;
    SearchNode (Node node) {
      n = node;
      next = null;
    }
  }
  public           Point2D nearest(Point2D p) {
    champion = root;
    recursiveSearch (root, p);
    return champion.point;
  }// a nearest neighbor in the set to point p; null if the set is empty 
  private void recursiveSearch (Node n, Point2D p) {
    if (n==null) {
      return;
    }
    if (n.point.distanceTo(p)<champion.point.distanceTo(p)) {
      champion = n;
    }
    boolean a = isEligible(n,"left",p);
    boolean b = isEligible(n,"right",p);
    if (a&&b) {
      if (n.parity%2==0) {
        if (n.point.x()>p.x()) {
          recursiveSearch (n.left,p);
          recursiveSearch (n.right,p); 
        }
        else {
          recursiveSearch (n.right,p); 
          recursiveSearch (n.left,p);
        }
      }
      else {
        if (n.point.y()>p.y()) {
          recursiveSearch (n.left,p);
          recursiveSearch (n.right,p); 
        }
        else {
          recursiveSearch (n.right,p); 
          recursiveSearch (n.left,p);
        }
      }
    }
    else if (a) {
      recursiveSearch (n.left,p);
    }
    else if (b) {
      recursiveSearch (n.right,p); 
    }
    /*
    if (n.left!=null&&n.right!=null) {
      
    }
    else if (n.left!=null) {
      if (n.parity%2==0) {
        if (p.x()-n.point.x()<champion.point.distanceTo(p)) {
          recursiveSearch(n.left, p);
        }
        else {
          return;
        }
      }
      if (n.parity%2==1) {
        if (p.y()-n.point.y()<champion.point.distanceTo(p)) {
          recursiveSearch(n.left, p);
        }
        else {
          return;
        }
      }
    }
    else if (n.right!=null) {
      if (n.parity%2==0) {
        if (n.point.x-p.x()()<champion.point.distanceTo(p)) {
          recursiveSearch(n.right, p);
        }
        else {
          return;
        }
      }
      if (n.parity%2==1) {
        if (n.point.y()-p.y()<champion.point.distanceTo(p)) {
          recursiveSearch(n.right, p);
        }
        else {
          return;
        }
      }
    }
    */
  }
  private boolean isEligible (Node n, String side, Point2D p) {
    //System.out.println(n.point.toString()+" "+side);
    if (side.equals("left")) {
      if (n.left==null) {
        return false;
      }
      if (n.parity%2==0) {
        if (p.x()-n.point.x()<champion.point.distanceTo(p)) {
          return true;
        }
        else {
          return false;
        }
      }
      else {
        if (p.y()-n.point.y()<champion.point.distanceTo(p)) {
          return true; 
        }
        else {
          return false;
        }
      }
    }
    else {
      if (n.right==null) {
        return false;
      }
      if (n.parity%2==0) {
        if (n.point.x()-p.x()<champion.point.distanceTo(p)) {
          return true;
        }
        else {
          return false;
        }
      }
      else {
        if (n.point.y()-p.y()<champion.point.distanceTo(p)) {
          return true;
        }
        else {
          return false;
        }
      }
    }
  }
   private class Node {
     Point2D point;
     Node left;
     Node right;
     int parity;
     Node (Point2D p, int par) {
       point = p;
       left = null;
       right = null;
       parity = par;
     }
     boolean equals (Node n) {
       return point.equals(n.point);
     }
     boolean equals (Point2D p) {
       return point.equals(p);
     }
   }
   public static void main(String[] args) {
     KdTree kdt = new KdTree();
     kdt.insert (new Point2D (0.3, 0.4));
     kdt.insert (new Point2D (0.2, 0.1));
     kdt.insert (new Point2D (0.4, 0.5));
     System.out.println(kdt.nearest(new Point2D (0.2, 0.2)).toString()); 
   }// unit testing of the methods (optional) 
}