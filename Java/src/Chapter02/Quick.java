package Chapter02;

public class Quick {
    public static void sort(Comparable[] a){
        sort(a,0,a.length-1);
    }
    private static void sort(Comparable[] a,int l,int r){
        if(r<=l) return;
        int i= l,j=r;
        int pos = l;
        while(i<j){
            while(j>pos && !less(a[j],a[pos]))
                j--;
            exch(a,pos,j);
            pos = j;
            while(i<pos && !less(a[pos],a[i]))
                i++;
            exch(a,pos,i);
            pos = i;
        }
        sort(a,l,pos-1);
        sort(a,pos+1,r);
    }
    private static boolean less(Comparable a,Comparable b){
        return a.compareTo(b)<0;
    }
    private static void exch(Comparable[] a,int i,int j){
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
    public static void main(String[] args){
        //         In in = new In("Data/tiny.txt");
//         String[] a = in.readAllStrings();
        String[] a = {"S", "O", "R", "T", "E", "X", "A", "M", "P", "L", "E"};
        sort(a);
        assert isSorted(a);
        show(a);
    }
}
