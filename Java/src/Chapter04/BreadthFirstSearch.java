package Chapter04;

import Chapter01.Queue;
import Chapter01.Stack;

public class BreadthFirstSearch {
    private boolean[] marked;

    private int[] edgeTo;

    private final int s;

    public BreadthFirstSearch(Graph g,int s){
        this.s = s;
        marked = new boolean[g.V()];
        edgeTo = new int[g.V()];
        bfs(g,s);
    }

    private void bfs(Graph g,int s){
        Queue<Integer> queue = new Queue<Integer>();
        queue.enqueue(s);
        marked[s] = true;
        while(!queue.isEmpty()){
            int v = queue.dequeue();
            for(int i:g.adj(v)){
                if(!marked[i]){
                    edgeTo[i] = v;
                    queue.enqueue(i);
                    marked[i] = true;
                }
            }
        }
    }

    public boolean hasPathTo(int v){
        return marked[v];
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
}
