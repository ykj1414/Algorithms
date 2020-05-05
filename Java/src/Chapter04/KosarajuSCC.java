package Chapter04;

//寻找有向图中的强连通分量
//有向图中的强连通意味着 能找到v->w的路径同时也存在w->v的路径，即一定存在有向环。
public class KosarajuSCC {

    private boolean[] marked;
    private int[] id;
    private int count;

    public KosarajuSCC(DiGraph dg){
        marked = new boolean[dg.V()];
        id = new int[dg.V()];
        DepthFirstOrder dfOrder = new DepthFirstOrder(dg.reverse());
        for(int s:dfOrder.reverse()){
            if(!marked[s]) {
                dfs(dg, s);
                count++;
            }
        }
    }

    public void dfs(DiGraph dg,int s){
        marked[s] = true;
        id[s] = count;
        for(int i:dg.adj(s)){
            if(!marked[i]){
                dfs(dg,i);
            }
        }
    }

    public boolean stronglyConnected(int v,int w){
        return id[v]==id[w];
    }
    public int id(int v){
        return id[v];
    }
    public int count(){
        return count;
    }
}
