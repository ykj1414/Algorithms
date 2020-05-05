package Chapter04;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;

public class EdgeWeightGraph {
    private int V; //顶点数

    private int E; //边数

    private Bag<Edge>[] adj; //顶点对信息

    private Bag<Edge> edges; //所有不重复边的信息

    public EdgeWeightGraph(int V){
        this.V = V;
        adj = (Bag<Edge>[])new Bag[V];
        edges = new Bag<Edge>();
        for(int i = 0;i<V;i++)
            adj[i] = new Bag<Edge>();
    }

    public EdgeWeightGraph(In in){
        this(in.readInt());
        this.E = in.readInt();
        while(!in.isEmpty()){
            int v = in.readInt();
            int w = in.readInt();
            double weight = in.readDouble();
            Edge edge = new Edge(v,w,weight);
            addEdge(edge);
            edges.add(edge);
        }
    }

    public void addEdge(Edge e){
        int v = e.either();
        int w = e.other(v);
        adj[v].add(e);
        adj[w].add(e);
        E++;
    }

    public int V(){
        return V;
    }

    public int E(){
        return E;
    }

    public Iterable<Edge> adj(int v){
        return adj[v];
    }

    public Iterable<Edge> edges(){
        return edges;
    }
}
