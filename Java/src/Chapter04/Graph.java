package Chapter04;

import Chapter01.Bag;
import edu.princeton.cs.algs4.In;

public class Graph {
    private int V;      //顶点数量

    private int E;      //边的数量

    private Bag<Integer>[]adj;      //邻接表数组，存储单元是Bag对象，
                                    // Bag对象在Chapter01中实现，存储单元是链表
    public Graph(int v){
        V = v;
        E = 0;
        adj = (Bag<Integer>[])new Bag[V];
        for(int i = 0;i<V;i++)
            adj[i] = new Bag<Integer>();
    }

    public Graph(In in){
        this(in.readInt());
        int E = in.readInt();
        for(int i = 0;i<E;i++) {
            int v = in.readInt();
            int w = in.readInt();
            addEdge(v,w);
        }
    }

    public int V(){
        return V;
    }

    public int E(){
        return E;
    }

    public void addEdge(int v,int w){
        adj[v].add(w);
        adj[w].add(v);
        E++;
    }

    public Iterable<Integer> adj(int v){
        return adj[v];
    }

    public String toString(){
        return null;
    }
}
