package Chapter05;

public class Quick3String {

    private static int charAt(String a,int d){
        if(d<a.length())
            return a.charAt(d);
        return -1;
    }

    public static void sort(String [] a){
        sort(a,0,a.length-1,0);
    }
    private static void sort(String[] a,int lo,int hi ,int d){
        if(hi<=lo)
            return;
        int i = lo,j = hi;
        int m = lo+1;
        int v = charAt(a[lo],d);
        while(m<=j){
            int t = charAt(a[m],d);
            if(t<v) exch(a,i++,m++);
            else if(t>v) exch(a,m,j--);
            else m++;
        }
        sort(a,lo,i-1,d);
        sort(a,j+1,hi,d);
        if(v>=0) sort(a,i,j,d+1);
    }

    private static void exch(String[] a,int i,int j){
        String b = a[i];
        a[i] = a[j];
        a[j] = b;
    }

    public static void main(String[] args){
        //String[] a = {"123","124","134","132","213","234","243","1234"};
        String[] a = {"she","are","by","seashells","sells","shells","seashells","sells"};
        sort(a);
        for(int i = 0;i<a.length;i++)
            System.out.println(a[i]);
    }
}
