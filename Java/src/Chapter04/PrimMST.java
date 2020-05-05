package Chapter04;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.IndexMinPQ;
import edu.princeton.cs.algs4.Queue;

public class PrimMST {
    private Edge[] edgeTo;      //距离树最近的边
    private double[] distTo;    //distTo[w] = edgeTo[w].weight()
    private boolean[] marked;
    private IndexMinPQ<Double> pq;// 有效的横切边的权重

    public PrimMST(EdgeWeightGraph G){
        marked = new boolean[G.V()];
        edgeTo = new Edge[G.V()];
        distTo = new double[G.V()];
        for(int i = 0;i<G.V();i++)
            distTo[i] = Double.POSITIVE_INFINITY;
        pq = new IndexMinPQ<Double>(G.V());
        distTo[0] = 0.0;
        pq.insert(0,0.0);
        while(!pq.isEmpty())
            visit(G,pq.delMin());

    }

    private void visit(EdgeWeightGraph G,int v){
        marked[v] = true;
        for(Edge e:G.adj(v)){
            int w = e.other(v);
            if(marked[w]) continue;
            if(distTo[w]>e.weight()){
                distTo[w] = e.weight();
                edgeTo[w] = e;
                if(pq.contains(w)) pq.changeKey(w,e.weight());
                else pq.insert(w,e.weight());
            }
        }
    }

    public Iterable<Edge> edges(){
        Queue<Edge> edges = new Queue<Edge>();
        for(int i = 0;i<edgeTo.length;i++)
            edges.enqueue(edgeTo[i]);
        return edges;
    }

    public double weight(){
        double res = 0.0;
        for(int i = 0;i<distTo.length;i++)
            res+=distTo[i];
        return res;
    }

    public static void main(String[] args){
        In in = new In("Data/tinyEWG.txt");
        EdgeWeightGraph G = new EdgeWeightGraph(in);
        PrimMST pmst = new PrimMST(G);
        for(Edge e:pmst.edges())
            System.out.println(e);
        System.out.printf("%.2f",pmst.weight());
    }
}
