package Chapter01;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.io.File;
import java.util.Arrays;
import java.util.Scanner;
public class BinarySearch {
    public static int rank(int key,int[]a){
        int lo = 0;
        int hi = a.length-1;
        while(lo<=hi){
            int mid = lo+(hi-lo)/2;
            if(key<mid) hi = mid-1;
            else if (key>mid) lo = mid+1;
            else return mid;
        }
        return -1;
    }
    public static void main(String[] args) {
        In in = new In("Data/largeW.txt");
        int[] whiteList = in.readAllInts();
        Arrays.sort(whiteList);
        In k = new In("Data/largeT.txt");
        int[] keyList = k.readAllInts();
        for(int i = 0;i<keyList.length;i++){
            if(rank(keyList[i],whiteList)==-1)
                System.out.printf("%d\n",keyList[i]);
        }
    }
}
