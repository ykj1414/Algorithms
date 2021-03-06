package Chapter05;

import Chapter02.Insertion;

public class MSD {
    private static int R = 256;
    private static final int M = 15;
    private static String[] aux;

    private static int charAt(String a,int b){
        if(b<a.length())
            return a.charAt(b);
        return -1;
    }

    public static void sort(String[] a){
        int N = a.length;
        aux = new String[N];
        sort(a,0,N,0);
    }

    private static void sort(String[] a,int lo,int hi,int d){
        if(hi<=lo+M){
            Insertion.sort(a,lo,hi,d);
            return;
        }
        int [] count = new int[R+2];
        for(int i = lo;i<hi;i++)
            count[charAt(a[i],d)+2]++;
        for(int r = 0;r<R+1;r++)
            count[r+1]+=count[r];
        for(int i = lo;i<hi;i++)
            aux[count[charAt(a[i],d)+1]++]=a[i];
        for(int i = lo;i<hi;i++)
            a[i] = aux[i-lo];
        for(int r = 0;r<R;r++)
            sort(a,lo+count[r],lo+count[r+1],d+1);
    }

    public static void main(String[] args){
        //String[] a = {"she","are","by","seashells","sells","shells","seashells","sells"};
        String[] a = {"123","124","134","132","213","234","243","1234"};
        sort(a);
        for(int i = 0;i<a.length;i++)
            System.out.println(a[i]);
    }
}
