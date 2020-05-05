package Chapter04;

import Chapter01.Bag;
import Chapter01.Stack;

public class SP {
    protected double[] distTo;
    protected DirectedEdge[] edgeTo;
    public SP(EdgeWeightedDigraph g, int s){
        edgeTo = new DirectedEdge[g.V()];
        distTo = new double[g.V()];
        for(int i = 0;i<g.V();i++)
            distTo[i] = Double.POSITIVE_INFINITY;
        distTo[s] = 0.0;
    }

    protected void relax(EdgeWeightedDigraph g, int s){

    }

    public double distTo(int v){
        return distTo[v];
    }

    public boolean hasPathTo(int v){
        return distTo[v]<Double.POSITIVE_INFINITY;
    }
    public Iterable<DirectedEdge>pathTo(int v){
        if(!hasPathTo(v)) return null;
        Stack<DirectedEdge> path = new Stack<DirectedEdge>();
        while(edgeTo[v]!=null){
            path.push(edgeTo[v]);
            v = edgeTo[v].from();
        }
        return path;
    }
}
