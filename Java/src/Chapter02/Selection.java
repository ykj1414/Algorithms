package Chapter02;

import edu.princeton.cs.algs4.In;

public class Selection {
     public static void sort(Comparable[] a){
         int alen = a.length;
         for (int i = 0;i<alen;i++) {
             for(int j = i+1;j<alen;j++){
                 if(less(a[j],a[i]))
                     exch(a,i,j);
             }
         }
     }
     private static void exch(Comparable[] a,int i,int j){
         Comparable c = a[i];
         a[i] = a[j];
         a[j] = c;
     }
     private static boolean less(Comparable a,Comparable b){
         return a.compareTo(b)<0;
     }
     private static boolean isSorted(Comparable[] a){
         for (int i = 1;i<a.length;i++){
             if(less(a[i],a[i-1]))
                 return false;
         }
         return true;
     }
     public static void main(String[] args){
//         In in = new In("Data/tiny.txt");
//         String[] a = in.readAllStrings();
         String[] a = {"S", "O", "R", "T", "E", "X", "A", "M", "P", "L", "E"};
         sort(a);
         assert (isSorted(a));
         show(a);

     }

    private static void show(Comparable[] a) {
         for(int i = 0;i<a.length;i++)
             System.out.print(a[i] +" ");
         System.out.println();
    }
}
