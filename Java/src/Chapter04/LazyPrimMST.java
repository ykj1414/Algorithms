package Chapter04;

import Chapter01.Bag;
import Chapter01.Queue;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;

import java.util.Iterator;

public class LazyPrimMST {

    private boolean[] marked;

    private Queue<Edge> mst;

    private MinPQ<Edge> pq; //使用最小堆排序构建的结构，类似MaxPQ

    private double weight;

    public LazyPrimMST(EdgeWeightGraph ewg){
        marked = new boolean[ewg.V()];
        pq = new MinPQ<Edge>();
        mst = new Queue<Edge>();
        weight = 0.0;
        visit(ewg,0);
        while(!pq.isEmpty()){
            Edge e = pq.delMin();
            int v = e.either();
            int w = e.other(v);
            if(marked[v]&&marked[w]) continue;
            mst.enqueue(e);
            weight+=e.weight();
            if(!marked[v]) visit(ewg,v);
            if(!marked[w]) visit(ewg,w);
        }
    }

    private void visit(EdgeWeightGraph G,int v){
        marked[v] = true;
        for(Edge e:G.adj(v)){
            if(!marked[e.other(v)])
                pq.insert(e);
        }
    }
    public Iterable<Edge> edges(){
        return mst;
    }

    public double weight(){
        return weight;
    }

    public static void main(String[] args){
        In in = new In("Data/tinyEWG.txt");
        EdgeWeightGraph G = new EdgeWeightGraph(in);
        LazyPrimMST lpmst = new LazyPrimMST(G);
        for(Edge e:lpmst.edges())
            System.out.println(e);
        System.out.println(lpmst.weight());
    }
}
