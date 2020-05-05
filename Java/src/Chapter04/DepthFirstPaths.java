package Chapter04;

import Chapter01.Stack;

public class DepthFirstPaths {

    private int N;

    private boolean[] mark;

    private int[] edgeTo;

    private final int s;

    public DepthFirstPaths(Graph g, int s){
        mark = new boolean[g.V()];
        edgeTo = new int[g.V()];
        this.s = s;
        dfs(g,s);
    }


    private void dfs(Graph g,int s){
        mark[s]=true;
        N++;
        for(int i:g.adj(s)){
            if(!mark[i]) {
                edgeTo[i] = s;
                dfs(g,i);
            }
        }
    }

    public boolean hasPathTo(int v){
        return mark[v];
    }

    public Iterable<Integer> pathTo(int v){
        if(!hasPathTo(v)) return null;
        Stack<Integer> stack = new Stack<>();
        int x = v;
        while(x!=s){
            stack.push(x);
            x = edgeTo[x];
        }
        return stack;
    }

    public int count(){
        return this.N;
    }
}
