public class TestFastCollinearPoints {
  public static void main(String[] args) {
    int num = Integer.parseInt(args[0]);
    Point[] points = new Point[num];
    for (int i = 0; i<num; i++) {
      points[i] = new Point(Integer.parseInt(args[i*2+1]),Integer.parseInt(args[i*2+2]));
    }
    FastCollinearPoints FCP = new FastCollinearPoints(points);
    LineSegment[] LS = FCP.segments();
    for (int i = 0; i<LS.length; i++) {
      System.out.println(LS[i].toString());
    }
  }
}