package Chapter03;

import Chapter01.Queue;

import java.util.Iterator;

public class LinearProbingHashST<Key,Value> {
    private int N;
    private int M;
    private Key[] keys;
    private Value[] vals;

    public LinearProbingHashST(){
        this(16);
    }
    public LinearProbingHashST(int n){
        keys = (Key[])new Object[n];
        vals = (Value[]) new Object[n];
        M=n;
    }
    private int hash(Key key){
        return (key.hashCode()&0x7fffffff)%M;
    }

    private int hash(Key key,int M){
        return (key.hashCode()&0x7fffffff)%M;
    }

    private void resize(int n){
        LinearProbingHashST temp = new LinearProbingHashST(n);
        for(int i = 0;i<M;i++){
            if(keys[i]!=null){
                temp.put(keys[i],vals[i]);
            }
        }
        keys = (Key[])temp.keys;
        vals = (Value[])temp.vals;
        M = n;
    }

    public void put(Key key,Value value){
        if(N>=M/2) resize(2*M);
        int i = 0;
        for(i = hash(key);keys[i]!=null;i=(i+1)%M){
            if(keys[i].equals(key)) {
                vals[i] = value;
                return;
            }
        }
        keys[i] = key;
        vals[i] = value;
        N++;
    }

    public boolean contains(Key key){
        for(int i = 0;i<M;i=(i+1)%M){
            if(keys[i]==null) continue;
            if(keys[i].equals(key))
                return true;
        }
        return false;
    }
    public void delete(Key key){
        if(!contains(key)) return;
        int i;
        for(i=hash(key);keys[i]!=null;i=(i+1)%M){
            if(keys[i].equals(key)){
                keys[i] = null;
                vals[i] = null;
                break;
            }
        }
        i = (i+1)%M;
        while(keys[i]!=null){
            Key tempk = keys[i];
            Value tempv = vals[i];
            N--;
            keys[i] = null;
            vals[i] = null;
            put(tempk,tempv);
        }
        N--;
        if(N>0&&N<M/2) resize(M/2);
    }

    public void print(){
        for(int i = 0;i<M;i++){
            if(keys[i]!=null){
                System.out.println(keys[i]+" "+vals[i]);
            }
        }
    }

    public static void main(String[] args){
        LinearProbingHashST lpst = new LinearProbingHashST(2);
        lpst.put(1,2);
        lpst.put(2,1);
        lpst.print();
        System.out.println("M:"+lpst.M);
        lpst.put(3,2);
        lpst.print();
        System.out.println("M:"+lpst.M);
        lpst.delete(3);
        lpst.print();
        System.out.println("M:"+lpst.M);
    }
}
