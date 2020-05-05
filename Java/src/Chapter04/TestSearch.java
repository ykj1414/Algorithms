package Chapter04;

import Chapter01.Bag;
import edu.princeton.cs.algs4.In;

import java.util.Iterator;


//还有一些符号图，二分图，环之类的算法可以在算法书上找到，比较简单且易理解。
public class TestSearch {
    public static void main(String[] args){
        In in = new In("Data/tinyG.txt");
        Graph g = new Graph(in);
        int s = 0;
        DepthFirstPaths dfp = new DepthFirstPaths(g,s);
        BreadthFirstSearch bfp = new BreadthFirstSearch(g,s);
        for(int i = 0;i<g.V();i++){
            if(dfp.hasPathTo(i)){
                System.out.print("dfs: "+s+" to "+i+":"+s+" ");
                Iterator<Integer> path = dfp.pathTo(i).iterator();
                while(path.hasNext())
                    System.out.print(path.next()+" ");
                System.out.println();
            }
            if(bfp.hasPathTo(i)){
                System.out.print("bfs: "+s+" to "+i+":"+s+" ");
                Iterator<Integer> path = bfp.pathTo(i).iterator();
                while(path.hasNext())
                    System.out.print(path.next()+" ");
                System.out.println();
            }
        }
        if(dfp.count()!=g.V())
            System.out.println("Not connected");
        CC cc = new CC(g);
        Bag<Integer> []components = (Bag<Integer>[])new Bag[cc.count()];
        for(int i = 0;i<cc.count();i++) components[i] = new Bag<Integer>();
        for(int i = 0;i<g.V();i++){
            components[cc.id(i)].add(i);
        }
        for(int i = 0;i<cc.count();i++){
            System.out.print(i+"th: ");
            for(int j:components[i])
                System.out.print(j+" ");
            System.out.println();
        }
    }
}
