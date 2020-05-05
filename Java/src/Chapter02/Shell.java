package Chapter02;

import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;

public class Shell {
    private static int line=0;
    public static void sort(Comparable[] a){
        int N = a.length;
        int h = 1;
        while(h<N/3) h=3*h+1;
        while(h>=1){
            for(int i = h ;i<N;i++){
                int j = i;
                for(;j>=h&&less(a[j],a[j-h]);j-=h)
                    exch(a,j-h,j);
                draw((String[])a, h, i, j);
                line++;
            }
            footer((String[])a);
            h/=3;
            line++;
        }
    }

    private static void draw(String[] a, int h, int ith, int jth) {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.text(-3.75, line, h + "");
        StdDraw.text(-2.50, line, ith + "");
        StdDraw.text(-1.25, line, jth + "");
        for (int i = 0; i < a.length; i++) {
            if (i == jth)                StdDraw.setPenColor(StdDraw.BOOK_RED);
            else if (i > ith)            StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
            else if (i < jth)            StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
            else if ((i % h) == (jth % h)) StdDraw.setPenColor(StdDraw.BLACK);
            else                         StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
            StdDraw.text(i, line, a[i]);
        }
    }

    private static void footer(String[] a) {
        int n = a.length;
        StdDraw.setPenColor(StdDraw.BLACK);
        for (int i = 0; i < n; i++)
            StdDraw.text(i, line, a[i]);
    }

    private static void header(String[] a) {
        int n = a.length;

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.text(n/2.0, -3, "a[ ]");
        for (int i = 0; i < n; i++)
            StdDraw.text(i, -2, i + "");
        StdDraw.text(-3.75, -2, "h");
        StdDraw.text(-2.50, -2, "i");
        StdDraw.text(-1.25, -2, "j");
        StdDraw.setPenColor(StdDraw.BOOK_RED);
        StdDraw.line(-4, -1.65, n - 0.5, -1.65);
        StdDraw.setPenColor(StdDraw.BLACK);
        for (int i = 0; i < a.length; i++)
            StdDraw.text(i, -1, a[i]);
    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable c = a[i];
        a[i] = a[j];
        a[j] = c;
    }

    private static boolean less(Comparable a, Comparable b) {
        return a.compareTo(b)<0;
    }

    public static void main(String[] args){
//         In in = new In("Data/tiny.txt");
//         String[] a = in.readAllStrings();
        String[] a = {"S", "O", "R", "T", "E", "X", "A", "M", "P", "L", "E"};
        int n = a.length;
        int rows = 0;
        int h = 1;
        while (h < n/3) {
            rows += (n - h + 1);
            h = 3*h + 1;
        }
        rows += (n - h + 1);
        // set canvas size
        StdDraw.setCanvasSize(30*(n+3), 30*(rows+3));
        StdDraw.setXscale(-4, n+1);
        // StdDraw.setYscale(n+1, -4);
        StdDraw.setYscale(rows, -4);
        StdDraw.setFont(new Font("SansSerif", Font.PLAIN, 13));

        // draw the header
        header(a);

        // sort the array
        sort(a);
        assert (isSorted(a));
        show(a);

    }

    private static void show(Comparable[] a) {
        for(int i = 0;i<a.length;i++)
            System.out.print(a[i] +" ");
        System.out.println();
    }

    private static boolean isSorted(Comparable[] a){
        for(int i = 1;i<a.length;i++){
            if(less(a[i],a[i-1]))
                return false;
        }
        return true;
    }


}
