package Chapter04;

public class CC {
    private int[] id;
    private int N = 0;
    private boolean marked[];
    public CC(Graph g){
        id = new int[g.V()];
        marked = new boolean[g.V()];
        for(int i = 0;i<g.V();i++){
            if(!marked[i]){
                dfs(g,i);
                N++;
            }
        }
    }

    private void dfs(Graph g,int s){
        marked[s] = true;
        //进入dfs的分组标记 一定与最开始进入时的最大分组数一致，
        // 因为dfs会遍历完该点能联通的所有点
        id[s] = N;
        for(int i:g.adj(s)){
            if(!marked[i]){
                dfs(g,i);
            }
        }
    }

    public boolean connected(int v,int w){
        return id[v]==id[w];
    }

    public int count(){
        return N;
    }

    public int id(int v){
        return id[v];
    }
}
