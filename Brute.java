import java.util.Arrays;

public class Brute {
    public static void main(String[] args) {
        // rescale coordinates and turn on animation mode
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);
        
        // read in the input
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        Point[] points = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            Point p = new Point(x, y);
            points[i] = p;
            p.draw();
        }
        
        // sort point array
        Arrays.sort(points);
        
        // brute force method to find out collinear line segments
        for (int p = 0; p < N; p++) {
            for (int q = p + 1; q < N; q++) {
                for (int r = q + 1; r < N; r++) {
                    if (points[p].slopeTo(points[q]) == points[p].slopeTo(points[r])) {
                        for (int s = r + 1; s < N; s++) {
                            if (points[p].slopeTo(points[q]) == points[p].slopeTo(points[s])) {                              
                                points[p].drawTo(points[s]);
                                StdOut.print(points[p] 
                                                 + " -> " + points[q] 
                                                 + " -> " + points[r] 
                                                 + " -> " + points[s]);
                                StdOut.println();
                            }
                        }
                    }
                }
            }
        }
        
        // display to screen all at once
        StdDraw.show(0);
    }
}