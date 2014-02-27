import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections; 
import java.util.Iterator;
import java.util.HashSet;

public class Fast {    
    private static String join(ArrayList<Point> list, String delim) {
        StringBuilder sb = new StringBuilder();        
        String loopDelim = "";
        
        for (Point s : list) {            
            sb.append(loopDelim);
            sb.append(s);               
            loopDelim = delim;
        }        
        return sb.toString();
    }
    
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

        // line segments array list
        ArrayList<ArrayList<Point>> seg = new ArrayList<ArrayList<Point>>();
        for (int i = 0; i < N; i++) {            
            Point p = points[i];

            Arrays.sort(points, p.SLOPE_ORDER);
            int lo = 0;
            int hi = 0;

            for (int j = 1; j < N-1; j++) {   
                // searching for the low bound and high bound
                if (lo == 0) {
                    if (p.slopeTo(points[j]) == p.slopeTo(points[j+1]))
                        lo = j;
                }
                else {
                    if (p.slopeTo(points[j]) != p.slopeTo(points[j+1]))
                        hi = j;                        
                    else if (j == N-2)
                        hi = N-1;   
                }
                 
                if (lo > 0 && hi > 0) {   
                    if (hi - lo > 1) {
                        Point[] range = Arrays.copyOfRange(points, lo, hi + 1);
                        ArrayList<Point> a = new ArrayList<Point>();
                        Collections.addAll(a, range);
                        a.add(p);
                        Collections.sort(a);
                        seg.add(a);
                    }
                    lo = 0;
                    hi = 0;
                }             
            }
            // re-sort point array
            Arrays.sort(points);
        }
        
        //remove duplicate line segments
        HashSet<ArrayList<Point>> hs = new HashSet<ArrayList<Point>>();
        hs.addAll(seg);
        seg.clear();
        seg.addAll(hs);
        
        // draw lines
        Iterator<ArrayList<Point>> i = seg.iterator();        
        while (i.hasNext()) {
            ArrayList<Point> a = i.next();
            a.get(0).drawTo(a.get(a.size()-1));
            StdOut.println(join(a, " -> "));           
        }
       
        // display to screen all at once
        StdDraw.show(0);
    }
}