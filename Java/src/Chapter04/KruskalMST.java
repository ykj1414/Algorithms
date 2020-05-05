package Chapter04;

import Chapter01.Queue;
import Chapter01.UF;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;

public class KruskalMST {

    private Queue<Edge>mst;

    private double weight = 0.0;

    public KruskalMST(EdgeWeightGraph G){
        UF uf = new UF(G.V());
        mst = new Queue<Edge>();
        MinPQ<Edge> pq = new MinPQ<Edge>();
        for(Edge e:G.edges())
            pq.insert(e);
        while(!pq.isEmpty() && mst.size()<G.V()-1){
            Edge e = pq.delMin();
            int v = e.either();
            int w = e.other(v);
            if(uf.connected(v,w)) continue;
            uf.union(v,w);
            mst.enqueue(e);
            weight+=e.weight();
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
        KruskalMST kmst = new KruskalMST(G);
        for(Edge e:kmst.edges())
            System.out.println(e);
        System.out.printf("%.2f",kmst.weight());
    }
}

