package Chapter02;

import edu.princeton.cs.algs4.StdDraw;
import java.awt.Font;

//插入排序，其中StdDraw写法参考官网题解。

public class Insertion {
    private static int line = 0;
    //针对字符串高位优先排序做的优化插入算法
    public static void sort(String[] a,int lo,int hi,int d){
        for(int i = lo+1;i<hi;i++){
            int j = i;
            for(;j>lo&&less(a[j].substring(d),a[j-1].substring(d));j--)
                exch(a,j,j-1);
        }
    }
    public static void sort(Comparable[] a){
        int N = a.length;
        for(int i = 1;i<N;i++){
            int j = i;
            for(;j>0&&less(a[j],a[j-1]);j--)
                exch(a,j,j-1);
            draw((String[])a,i,i,j);
        }
    }

    private static boolean less(Comparable a,Comparable b){
        return a.compareTo(b)<0;
    }

    private static void exch(Comparable[] a, int i,int j){
        Comparable c = a[i];
        a[i] = a[j];
        a[j] = c;
    }

    public static void show(Comparable[] a){
        for(int i = 0;i<a.length;i++)
            System.out.print(a[i]+" ");
        System.out.println();
    }

    public static boolean isSorted(Comparable[] a){
        for(int i = 1;i<a.length;i++){
            if(less(a[i],a[i-1]))
                return false;
        }
        return true;
    }
    private static void draw(String[] a, int row, int ith, int jth) {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.text(-2.50, row, ith + "");
        StdDraw.text(-1.25, row, jth + "");
        for (int i = 0; i < a.length; i++) {
            if (i == jth)                StdDraw.setPenColor(StdDraw.BOOK_RED);
            else if (i > ith)            StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
            else if (i < jth)            StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
            else if ((i % row) == (jth % row)) StdDraw.setPenColor(StdDraw.BLACK);
            else                         StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
            StdDraw.text(i, row, a[i]);
        }
    }

    private static void header(String[] a) {
        int n = a.length;

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.text(n/2.0, -3, "a[ ]");
        for (int i = 0; i < n; i++)
            StdDraw.text(i, -2, i + "");
        StdDraw.text(-2.50, -2, "i");
        StdDraw.text(-1.25, -2, "j");
        StdDraw.setPenColor(StdDraw.BOOK_RED);
        StdDraw.line(-3, -1.65, n - 0.5, -1.65);
        StdDraw.setPenColor(StdDraw.BLACK);
        for (int i = 0; i < n; i++)
            StdDraw.text(i, -1, a[i]);
    }

    // display footer
    private static void footer(String[] a) {
        int n = a.length;
        StdDraw.setPenColor(StdDraw.BLACK);
        for (int i = 0; i < n; i++)
            StdDraw.text(i, n, a[i]);
    }


    public static void main(String[] args){
//         In in = new In("Data/tiny.txt");
//         String[] a = in.readAllStrings();
        String[] a = {"S", "O", "R", "T", "E", "X", "A", "M", "P", "L", "E"};
        //Integer[] a = {1,5,2,7,8,0,10,9,1,1,2,3,4,5,1,10,7,8,6};
        sort(a);
        assert (isSorted(a));
        show(a);
        int rows = 0;
        int h = 1;
        int n = a.length;
        while (h < n/3) {
            rows += (n - h + 1);
            h = 3*h + 1;
        }
        rows += (n - h + 1);

        // set canvas size
        StdDraw.setCanvasSize(30*(n+3), 30*(rows+3));
        StdDraw.setXscale(-4, n+1);
        StdDraw.setYscale(rows, -4);
        StdDraw.setFont(new Font("SansSerif", Font.PLAIN, 13));

        // draw the header
        header(a);

        // sort the array
        sort(a);
        footer(a);
    }
}
