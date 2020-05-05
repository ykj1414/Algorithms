package Chapter04;


import edu.princeton.cs.algs4.In;

//注意 该算法必须在无环加权有向图中使用，因为使用到了拓扑排序
/*求最长路径 只需要构造时将路径变为负数，将distTo初始值设为Double.NEGATIVE_INFINITY
   并在relax方法中改变不等号即可
 */

public class AcyclicSP extends SP{

    public AcyclicSP(EdgeWeightedDigraph g,int s){
        super(g,s);
        Topological tg = new Topological(g);
        for(int i :tg.order())
            relax(g,i);
    }

    @Override
    public void relax(EdgeWeightedDigraph g,int s){
        for(DirectedEdge e :g.adj(s)){
            int w = e.to();
            if(distTo[w]>distTo[s]+e.weight()){
                distTo[w] = distTo[s]+e.weight();
                edgeTo[w] = e;
            }
        }

    }

    public static void main(String[] args){
        In in = new In("Data/tinyEWDAG.txt");
        EdgeWeightedDigraph ewd = new EdgeWeightedDigraph(in);
        int s = 5;
        AcyclicSP  asp = new AcyclicSP(ewd,s);
        for(int i = 0;i<ewd.V();i++){
            System.out.printf("%d->%d:%.2f\t",s,i,asp.distTo(i));
            for(DirectedEdge e:asp.pathTo(i))
                System.out.printf("%s ",e);
            System.out.println();
        }
    }
}
