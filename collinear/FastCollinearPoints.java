public class FastCollinearPoints {
  private int num;
  private LineSegment[] segs;
  public FastCollinearPoints(Point[] points) {
    num = 0;
    int size = points.length;
    segs = new LineSegment[size*4];
    Point[] lowPoints = new Point[size*4];
    Point[] highPoints = new Point[size*4];
    for (int i = 0; i<size; i++) {
      double[] slopes = new double[size-1];
      int[] indices = new int[size-1];
      int count = 0;
      for (int j = 0; j<size; j++) {
        if (j!=i) {
          indices[count] = j;
          slopes[count] = points[i].slopeTo(points[j]);
          count++;
        }
      }    
      Quick quick = new Quick();
      quick.sort(slopes, indices);
      double last = slopes[0];
      int streak = 1;
      for (int j = 1; j<size-1; j++) {
        if (slopes[j]==last) {
          streak++;
        }
        else {
          last = slopes[j];
          streak = 1;
        }
        if (streak>2&&j==size-2||streak>2&&j<size-2&&slopes[j+1]!=last) {
          Point p1 = new Point (Integer.MIN_VALUE,Integer.MIN_VALUE);
          Point p2 = new Point (Integer.MAX_VALUE,Integer.MAX_VALUE);
          int[] temp = new int[streak+1];
          temp[0] = i;
          for (int k = 1; k<streak+1; k++) {
            temp[k] = indices[j-k+1];
          }
          for (int m = 0; m<streak+1; m++) {
            if (points[temp[m]].compareTo(p1)>0) {
              p1 = points[temp[m]];
            }
            if (points[temp[m]].compareTo(p2)<0) {
              p2 = points[temp[m]];
            }
          }
          LineSegment LS = new LineSegment (p2,p1);
          lowPoints[num] = p2;
          highPoints[num] = p1;
          segs[num] = LS;
          num++;
        }
      }
    }
    LineSegment[] temp = new LineSegment[num];
    int count = 0;
    for (int i = 0; i<num; i++) {
      boolean marker = true;
      for (int j = 0; j<i; j++) {
        if (lowPoints[i].slopeTo(lowPoints[j])==Double.NEGATIVE_INFINITY&&
            highPoints[i].slopeTo(highPoints[j])==Double.NEGATIVE_INFINITY) {
          marker = false;
          break;
        }
      }
      if (marker) {
        temp[count] = segs[i];
        count++;
      }
    }
    num = count;
    segs = temp;
    temp = new LineSegment[num];
    for (int i = 0; i<count; i++) {
      temp[i] = segs[i];
    }
    segs = temp;
  }// finds all line segments containing 4 or more points
  public int numberOfSegments() {
    return num;
  }// the number of line segments
  public LineSegment[] segments() {
    return segs;
  }// the line segments
  private class Quick {

    // This class should not be instantiated.
    private Quick() { }

    /**
     * Rearranges the array in ascending order, using the natural order.
     * @param a the array to be sorted
     */
    public void sort(double[] a, int[] b) {
        //StdRandom.shuffle(a);
        sort(a, 0, a.length - 1, b);
        //assert isSorted(a);
    }

    // quicksort the subarray from a[lo] to a[hi]
    private void sort(double[] a, int lo, int hi, int[] b) { 
        if (hi <= lo) return;
        int j = partition(a, lo, hi, b);
        sort(a, lo, j-1, b);
        sort(a, j+1, hi, b);
        //assert isSorted(a, lo, hi);
    }

    // partition the subarray a[lo..hi] so that a[lo..j-1] <= a[j] <= a[j+1..hi]
    // and return the index j.
    private int partition(double[] a, int lo, int hi, int[] b) {
        int i = lo;
        int j = hi + 1;
        double v = a[lo];
        while (true) { 

            // find item on lo to swap
            while (less(a[++i], v))
                if (i == hi) break;

            // find item on hi to swap
            while (less(v, a[--j]))
                if (j == lo) break;      // redundant since a[lo] acts as sentinel

            // check if pointers cross
            if (i >= j) break;

            exch(a, i, j);
            int temp = b[i];
            b[i] = b[j];
            b[j] = temp;
        }

        // put partitioning item v at a[j]
        exch(a, lo, j);
        int temp = b[lo]; 
        b[lo] = b[j];
        b[j] = temp;

        // now, a[lo .. j-1] <= a[j] <= a[j+1 .. hi]
        return j;
    }

   /***************************************************************************
    *  Helper sorting functions.
    ***************************************************************************/
    
    // is v < w ?
    private boolean less(double v, double w) {
        return v<w;
    }
        
    // exchange a[i] and a[j]
    private void exch(double[] a, int i, int j) {
        double swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }


   /***************************************************************************
    *  Check if array is sorted - useful for debugging.
    ***************************************************************************/
    /*
    private boolean isSorted(Comparable[] a) {
        return isSorted(a, 0, a.length - 1);
    }

    private boolean isSorted(Comparable[] a, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++)
            if (less(a[i], a[i-1])) return false;
        return true;
    }


    // print array to standard output
    private void show(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
            StdOut.println(a[i]);
        }
    }
    */
  }
}