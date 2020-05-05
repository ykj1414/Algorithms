package Chapter04;


import Chapter01.Bag;
import edu.princeton.cs.algs4.In;

public class DirectedDFS {

    private boolean[]marked;

    public DirectedDFS(DiGraph dg,int s){
        marked = new boolean[dg.V()];
        dfs(dg,s);
    }

    public DirectedDFS(DiGraph dg,Iterable<Integer>sources){
        marked = new boolean[dg.V()];
        for(Integer i :sources)
            dfs(dg,i);
    }


    private void dfs(DiGraph dg,int s){
        marked[s] = true;
        for(int i:dg.adj(s)){
            if(!marked[i]){
                dfs(dg,i);
            }
        }
    }

    public boolean marked(int v){
        return marked[v];
    }


    public static void main(String[] args){
        In in  = new In("Data/tinyDG.txt");
        DiGraph dg = new DiGraph(in);
        Bag<Integer> sources = new Bag<Integer>();
        sources.add(1);
        sources.add(2);
        sources.add(6);
        DirectedDFS dDfs = new DirectedDFS(dg,sources);
        for(int i = 0;i<dg.V();i++){
            if(dDfs.marked[i])
                System.out.print(i+" ");
        }
    }
}
