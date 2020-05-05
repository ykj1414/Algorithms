package Chapter02;

public class Merge {
    private static Comparable[] aux;
    //自顶向下的归并排序，适用数组
    public static void sort(Comparable[] a){
        aux = new Comparable[a.length];
        sort(a,0,a.length-1);
    }
    private static void sort(Comparable[] a,int l,int r){
        if(r<=l) return;
        int mid = l+(r-l)/2;
        sort(a,l,mid);
        sort(a,mid+1,r);
        merge(a,l,mid,r);
    }
    //自底向上的归并排序，适用链表结构的排序
    public static void sort_bottom(Comparable[] a){
        aux = new Comparable[a.length];
        int N = a.length;
        for(int sz = 1;sz<N;sz+=sz){     //sz代表子数组的大小
            for(int lo=0;lo<N-sz;lo+=sz+sz){
                merge(a,lo,lo+sz-1,Math.min(lo+sz+sz-1,N-1));
            }
        }
    }

    private static void merge(Comparable[] a, int l, int mid, int r) {
        int i = l,j = mid+1;
        for(int k = l;k<=r;k++){
            aux[k] = a[k];
        }
        int k = l;
        while(k<=r){
            if(i>mid)
                a[k++] = aux[j++];
            else if(j>r)
                a[k++] = aux[i++];
            else if (less(aux[j],aux[i]))
                a[k++] = aux[j++];
            else
                a[k++] = aux[i++];
        }
    }

    private static boolean less(Comparable a, Comparable b) {
        return a.compareTo(b)<0;
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
        sort_bottom(a);
        assert isSorted(a);
        show(a);
    }
}
