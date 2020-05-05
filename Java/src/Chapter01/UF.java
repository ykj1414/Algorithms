package Chapter01;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
//动态连通性问题，在后面图算法中也会有应用
//实现了路径压缩的加权quick-union算法，是书上几种算法中，效率最高的，其他算法可以参考算法书140-144
//在我的Python版本算法中实现的是普通的quick-union算法，并没有在find函数中将所有的触点连接到根节点。
//这个uf算法本质上构建了一片森林。
public class UF {
    private int[] id;       //分量id
    private int count;      //分量数量
    private int[] id_count; //每个分量的森林数量
    public int count(){
        return count;
    }
    public UF(int N){
        count = N;
        id = new int[N];
        id_count = new int[N];
        for (int i = 0;i<N;i++){
            id[i] = i;
            id_count[i] = 1;
        }
    }
    public void union(int p ,int q){
        int sp = find(p);
        int sq = find(q);
        if(sp==sq)
            return;
        count--;
        if(id_count[sp]>=id_count[sq]){
            id[sq] = id[sp];
            id_count[sp]+=id_count[sq];
        }
        else{
            id[sp] = id[sq];
            id_count[sq]+=id_count[sp];
        }
    }
    public boolean connected(int p,int q){
        return find(p)==find(q);
    }
    public int find(int p){
        int s = p;
        while(id[s]!=s){
            s = id[s];
        }
        while(id[p]!=s){
            p = id[p];
            id[p] = s;
        }
        return s;
    }

    public static void main(String[] args){
        In in = new In("Data/tinyUF.txt");
        int N = in.readInt();
        UF uf = new UF(N);
        while(!in.isEmpty()){
            int p = in.readInt();
            int q = in.readInt();
            if(uf.connected(p,q)) continue;
            else uf.union(p,q);
            StdOut.println(p+" "+q);
        }
        System.out.println(uf.count()+"components");

    }
}
