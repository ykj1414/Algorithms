package Chapter04;

import Chapter01.Bag;

import edu.princeton.cs.algs4.In;

public class DiGraph {

    private int V;      //有向图的顶点数

    private int E;      //有向图的边数

    private Bag<Integer> []adj;       //有向图的邻接表

    public DiGraph(int V){
        this.V = V;
        this.E = 0;
        adj = (Bag<Integer>[])new Bag[V];
        for(int i = 0;i<V;i++)
            adj[i] = new Bag<Integer>();
    }

    public DiGraph(In in){
        this(in.readInt());
        int E = in.readInt();
        for(int i = 0;i<E;i++){
            int v = in.readInt();
            int w = in.readInt();
            addEdge(v,w);
        }
    }

    public void addEdge(int v,int w){
        adj[v].add(w);
        this.E++;
    }

    public int V(){ return V; }

    public int E(){
        return E;
    }

    public DiGraph reverse(){
        DiGraph rdg = new DiGraph(V);
        for(int i = 0;i<V;i++){
            for(int v:adj[i])
                rdg.addEdge(v,i);
        }
        return rdg;
    }

    public Iterable<Integer> adj(int s){
        return adj[s];
    }
}
