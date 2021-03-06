import java.lang.Integer;

public class BruteCollinearPoints {
  private int num;
  private LineSegment[] segs;
  public BruteCollinearPoints(Point[] points) {
    num = 0;
    int size = points.length;
    segs = new LineSegment[size];
    for (int i = 0; i<size-3; i++) {
      for (int j = i+1; j<size-2; j++) { 
        for (int k = j+1; k<size-1; k++) {
          for (int l = k+1; l<size; l++) {
            if (points[i].slopeTo(points[j])==points[i].slopeTo(points[k])&&
                points[i].slopeTo(points[j])==points[i].slopeTo(points[l])) {
              Point p1 = new Point (Integer.MIN_VALUE,Integer.MIN_VALUE);
              Point p2 = new Point (Integer.MAX_VALUE,Integer.MAX_VALUE);
              int[] temp = {i,j,k,l};
              for (int m = 0; m<4; m++) {
                if (points[temp[m]].compareTo(p1)>0) {
                  p1 = points[temp[m]];
                }
                if (points[temp[m]].compareTo(p2)<0) {
                  p2 = points[temp[m]];
                }
              }
              LineSegment LS = new LineSegment (p1,p2);
              segs[num] = LS;
              num++;
            }
          }
        }
      }
    }
    LineSegment[] temp = new LineSegment[num];
    for (int i = 0; i<num; i++) {
      temp[i] = segs[i];
    }
    segs = temp;
  }// finds all line segments containing 4 points
  public int numberOfSegments() {
    return num;
  }// the number of line segments
  public LineSegment[] segments() {
    return segs;
  }// the line segments
}