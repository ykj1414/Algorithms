package Chapter04;

//检查顶点对的可达性，注意在图论中 dfs算法的重要性。

public class TransitiveClosure {
    private DirectedDFS[] all;
    public TransitiveClosure(DiGraph dg){
        all = new DirectedDFS[dg.V()];
        for(int i = 0;i<dg.V();i++)
            all[i] = new DirectedDFS(dg,i);
    }

    public boolean reachable(int v,int w){
        return all[v].marked(w);
    }
}
