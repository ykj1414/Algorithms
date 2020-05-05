package Chapter04;

import Chapter01.Queue;
import Chapter01.Stack;

public class DepthFirstOrder {

    private Queue<Integer> preOrder;        //顶点的前序排列
    private Queue<Integer> postOrder;       //顶点的后续排列
    private Stack<Integer> reverseOrder;    //所有顶点的逆后序排列
    //当有向图无环时，逆后序排序就是拓扑排序

    private boolean[] marked;

    public DepthFirstOrder(DiGraph dg){
        marked = new boolean[dg.V()];
        preOrder = new Queue<Integer>();
        postOrder = new Queue<Integer>();
        reverseOrder = new Stack<Integer>();
        for(int i = 0;i<dg.V();i++)
            if(!marked[i])
                dfs(dg,i);
    }

    private void dfs(DiGraph dg,int s){
        marked[s] = true;
        preOrder.enqueue(s);
        for(int i:dg.adj(s)){
            if(!marked[i])
                dfs(dg,i);
        }
        postOrder.enqueue(s);
        reverseOrder.push(s);
    }

    public Iterable<Integer> pre(){
        return preOrder;
    }

    public Iterable<Integer> post(){
        return postOrder;
    }

    public Iterable<Integer> reverse(){
        return reverseOrder;
    }
}
