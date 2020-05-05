package Chapter05;


public class LSD {
    public void sort(String[] a,int w){
        int N = a.length;
        int R = 256;
        String []aux = new String[N];
        for(int d = w-1;d>=0;d--){
            int[] count = new int[R];
            for(int i = 0;i<N;i++)
                count[a[i].charAt(d)+1]++;
            for(int r = 0;r<R-1;r++)
                count[r+1]+=count[r];
            for(int i = 0;i<N;i++)
                aux[count[a[i].charAt(d)]++]=a[i];
            for(int i = 0;i<N;i++)
                a[i] = aux[i];
        }
    }

    public static void main(String [] args){
        String[] a = {"123","124","134","132","213","234","243"};
        LSD lsd = new LSD();
        lsd.sort(a,3);
        for (int i = 0;i<a.length;i++)
            System.out.println(a[i]);
    }
}
