package Chapter04;

import Chapter01.Bag;
import edu.princeton.cs.algs4.In;

public class EdgeWeightedDigraph {

    private Bag<DirectedEdge>[]adj;
    private int V;
    private int E;

    public EdgeWeightedDigraph(int V){
        this.V = V;
        adj = (Bag<DirectedEdge>[])new Bag[V];
        for(int i = 0;i<V;i++)
            adj[i] = new Bag<DirectedEdge>();

    }

    public EdgeWeightedDigraph(In in){
        this(in.readInt());
        int E = in.readInt();
        for(int i = 0;i<E;i++){
            int v = in.readInt();
            int w = in.readInt();
            double weight = in.readDouble();
            DirectedEdge e = new DirectedEdge(v,w,weight);
            addEdge(e);
        }
    }

    public void addEdge(DirectedEdge e){
        int v = e.from();
        adj[v].add(e);
        E++;
    }

    public int V(){
        return this.V;
    }

    public int E(){
        return E;
    }


    public Iterable<DirectedEdge> adj(int v){
        return adj[v];
    }

    public Iterable<DirectedEdge> edges(){
        Bag<DirectedEdge> bag = new Bag<>();
        for(int i = 0;i<V;i++){
            for(DirectedEdge e:adj[i])
                bag.add(e);
        }
        return bag;
    }
}
