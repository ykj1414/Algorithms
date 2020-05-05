package Chapter02;


//三向快排，非常适用有大量重复数值比较的情况，在很多算法中都有三向思路的应用。
public class Quick3Way {
    public static void sort(Comparable[] a){
        sort(a,0,a.length-1);
    }
    private static void sort(Comparable[] a,int l,int r){
        if(r<=l) return;
        int i= l,j=r;
        int pos = l+1;
        Comparable cur = a[l];
        while(pos<=j){
            if(less(a[pos],cur)){
                exch(a,pos++,i++);
            }
            else if(less(cur,a[pos])){
                exch(a,pos,j--);
            }
            else
                pos++;
        }
        sort(a,l,i-1);
        sort(a,pos,r); //pos==j+1
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
