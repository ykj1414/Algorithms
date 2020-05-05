package Chapter02;

//利用堆排序实现优先队列。
//最大堆是一种很常用的数据结构，可以利用数组轻松的实现
//为了增加代码的可读性以及更方便理解，我在Java版本中令堆头为空，直接从下标1开始计数
//在Python版本中是从下标0开始记录数据的

import edu.princeton.cs.algs4.MinPQ;

import java.security.Key;

public class MaxPQ<KEY extends Comparable<KEY>>{
    private int N = 0;
    private KEY[] pq;
    public MaxPQ(){
        pq = (KEY[])new Comparable[N+1];
    }

    public MaxPQ(int n){
        pq = (KEY[])new Comparable[n+1];
    }

    public MaxPQ(KEY[] a){
        int n = a.length+1;
        pq = (KEY[])new Comparable[n+1];
        for(KEY i:a){
            insert(i);
        }
    }

    private void sort(Comparable[] a){
        int maxN = a.length-1;
        for(int k = maxN/2;k>=1;k--){
            sink(a,k,maxN);
        }
        while(maxN>1){
            exch(a,1,maxN--);
            sink(a,1,maxN);
        }
    }

    private void sink(Comparable[] a,int k,int maxN){
        while(2*k<=maxN){
            int j = 2*k;
            if(j<maxN&&less(a,j,j+1))j++;
            if(!less(a,k,j)) break;
            exch(a,k,j);
            k = j;
        }
    }
    private boolean less(Comparable[] a,int i,int j){
        return a[i].compareTo(a[j])<0;
    }

    private void exch(Comparable[]a,int i,int j){
        Comparable c = a[i];
        a[i] = a[j];
        a[j] = c;
    }

    public KEY Max(){
        return pq[1];
    }

    public KEY delMax(){
        KEY max = pq[1];
        exch(1,N--);
        pq[N+1] = null;
        sink(1);
        return max;
    }

    public void insert(KEY a){
        pq[++N] = a;
        swim(N);
    }

    public boolean isEmpty(){
        return N<=1;
    }

    public int size(){
        return N;
    }

    //堆的上浮操作
    private void swim(int k){
        while(k>1 && less(k/2,k)){
            exch(k/2,k);
            k/=2;
        }
    }



    //对的下沉操作,注意需要比较两个子节点的大小
    private void sink(int k){
        while(2*k<=size()){  //保证j不会越界
            int j = 2*k;
            if(j<N&&less(j,j+1)) j++;
            if(!less(k,j)) break;
            exch(j,k);
            k=j;
        }
    }

    private  boolean less(int i,int j){
        return pq[i].compareTo(pq[j])<0;
    }

    private void exch(int i ,int j){
        KEY a = pq[i];
        pq[i] = pq[j];
        pq[j] = a;
    }

    public void show(KEY[] a){
        for(KEY i:a){
            System.out.print(i+" ");
        }
        System.out.println();
    }

    public boolean isSorted(KEY[] a){
        for(int i =1;i<a.length;i++){
            if (a[i].compareTo(a[i+1])<0) return false;
        }
        return true;
    }

    public static void main(String[] args){
        //         In in = new In("Data/tiny.txt");
//         String[] a = in.readAllStrings();
        String[] a = {"S", "O", "R", "T", "E", "X", "A", "M", "P", "L", "E"};
        //由于我的实现是下标对应元素的出现位置，所以直接使用堆排序需要首元素为空，保证下标与遍历位置一致
        String[] b = {" ","S", "O", "R", "T", "E", "X", "A", "M", "P", "L", "E"};
        MaxPQ mpq = new MaxPQ(a);
        for(int i = a.length-1;i>=0;i--){
            a[i] = (String)mpq.delMax();
        }
        mpq.sort(b);
        assert mpq.isSorted(a)&& mpq.isSorted(b);
        mpq.show(a);
        mpq.show(b);
    }

}
