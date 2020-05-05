package Chapter04;

import Chapter01.Stack;
import edu.princeton.cs.algs4.In;

public class DirectedCycle {
    private boolean[] marked;

    private int[] edgeTo;

    private Stack<Integer> cycle;

    private boolean[]onStack;

    public DirectedCycle(DiGraph dg){
        marked = new boolean[dg.V()];
        edgeTo = new int[dg.V()];
        onStack = new boolean[dg.V()];
        for(int i = 0;i<dg.V();i++){
            if(!marked[i])
                dfs(dg,i);
        }
    }

    public boolean hasCycle(){
        return cycle!=null;
    }

    private void dfs(DiGraph dg,int s){
        marked[s] = true;
        onStack[s] = true;
        for(int i:dg.adj(s)){
            if(this.hasCycle()) return;
            else if(!marked[i]){
                edgeTo[i] = s;
                dfs(dg,i);
            }
            else if(onStack[i]){
                cycle = new Stack<Integer>();
                for(int x = s;x!=i;x = edgeTo[x])
                    cycle.push(x);
                cycle.push(i);
                cycle.push(s);
            }
        }
        onStack[s] = false;
    }

    public Iterable<Integer>cycle(){
        return cycle;
    }

    public static void main(String[] args){
        In in = new In("Data/tinyDG.txt");
        DiGraph dg = new DiGraph(in);
        DirectedCycle dc = new DirectedCycle(dg);
        for(int i :dc.cycle())
            System.out.print(i+" ");
    }
}
