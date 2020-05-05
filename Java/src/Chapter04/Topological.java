package Chapter04;

public class Topological {
    private Iterable<Integer> order; //拓扑排序
    private DirectedCycle cyclefinder;
    public Topological(DiGraph dg){
        cyclefinder = new DirectedCycle(dg);
        findCycle(dg);
    }

    public Topological(EdgeWeightedDigraph ewg){
        DiGraph dg = new DiGraph(ewg.V());
        for(int i = 0;i<ewg.V();i++){
            for(DirectedEdge e:ewg.adj(i))
                dg.addEdge(i,e.to());
        }
        cyclefinder = new DirectedCycle(dg);
        findCycle(dg);
    }

    private void findCycle(DiGraph diGraph){
        //如果有环就不存在拓扑排序
        if(!cyclefinder.hasCycle()){
            DepthFirstOrder dfOrder = new DepthFirstOrder(diGraph);
            order = dfOrder.reverse();
        }
    }

    public Iterable<Integer> order(){
        return order;
    }

    public boolean isDAG(){
        return order!=null;
    }
}
