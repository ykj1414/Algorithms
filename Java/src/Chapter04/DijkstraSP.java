package Chapter04;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.IndexMinPQ;

public class DijkstraSP extends SP{
    private IndexMinPQ<Double> pq;

    public DijkstraSP(EdgeWeightedDigraph g,int s){
        super(g,s);
        pq = new IndexMinPQ<Double>(g.V());
        pq.insert(s,distTo[s]);
        while(!pq.isEmpty()){
            relax(g,pq.delMin());
        }
    }

    @Override
    protected void relax(EdgeWeightedDigraph g, int v){
        for(DirectedEdge e:g.adj(v)){
            int w = e.to();
            if(distTo[w]>distTo[v]+e.weight()){
                distTo[w] = distTo[v]+e.weight();
                edgeTo[w] = e;
                if(pq.contains(w)) pq.changeKey(w,distTo[w]);
                else
                    pq.insert(w,distTo[w]);
            }
        }
    }

    public static void main(String[] args){
        In in = new In("Data/tinyEWD.txt");
        EdgeWeightedDigraph ewd = new EdgeWeightedDigraph(in);
        int s = 0;
        DijkstraSP  dsp = new DijkstraSP(ewd,s);
        for(int i = 0;i<ewd.V();i++)
            System.out.printf("%d->%d,%s,%.2f\n",s,i,dsp.edgeTo[i],dsp.distTo(i));
    }
}
