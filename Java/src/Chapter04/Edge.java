package Chapter04;

public class Edge implements Comparable<Edge> {

    private int v;      //起点
    private int w;      //终点
    private double weight; //权值
    public Edge(int v,int w,double weight){
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    public int either(){
        return  this.v;
    }

    public double weight(){
        return this.weight;
    }

    public int other(int v){        //最好将形参改名
        if(this.v==v)
            return w;
        else if(this.w==v)
            return this.v;
        else
            return  -1;
    }

    @Override
    public String toString() {
        return String.format("%d-%d %.2f",v,w,weight);
    }

    @Override
    public int compareTo(Edge that) {
        if (this.weight>that.weight){
            return 1;
        }
        else if(this.weight==that.weight){
            return 0;
        }
        else
            return -1;
    }
}
