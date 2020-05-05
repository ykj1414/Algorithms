package Chapter03;

import edu.princeton.cs.algs4.SequentialSearchST;

import java.util.Iterator;
import Chapter01.Queue;

public class SeparateChainningHashST<Key extends Comparable<Key>,Value> {
    private int N;
    private int M;
    private SequentialSearchST<Key,Value>[] st;     //在algs4包中提供的可存放链表的数组


    public SeparateChainningHashST(){
        this(997);
    }
    public SeparateChainningHashST(int n){
        M = n;
        st = (SequentialSearchST<Key, Value>[]) new SequentialSearchST[M];
        for(int i=0;i<M;i++)
            st[i] = new SequentialSearchST();
    }

    private int hash(Key key){
        return (key.hashCode()&0x7fffffff)%M;
    }

    private int hash(Key key,int M){
        return (key.hashCode()&0x7fffffff)%M;
    }

    public Value get(Key key){
        return (Value)st[hash(key)].get(key);
    }

    public void put(Key key,Value val){
        if(N/M>=0.8)
            reset(M+M/2);
        int index = hash(key);
        st[index].put(key,val);
        N++;
    }

    private void reset(int length){
        SequentialSearchST<Key,Value>[] temp =(SequentialSearchST<Key, Value>[]) new SequentialSearchST[length];
        for(int i =0;i<length;i++) temp[i] = new SequentialSearchST<Key,Value>();
        Iterator<Key> keys = keys().iterator();
        while(keys.hasNext()){
            Key cur = keys.next();
            temp[hash(cur,length)].put(cur,st[hash(cur)].get(cur));
        }
        M=length;
        st = temp;
    }

    public Iterable<Key>keys(){
        Queue<Key> queue = new Queue<Key>();
        for(int i = 0;i<M;i++){
            if(st[i].isEmpty())
                continue;
            Iterator<Key> keys = st[i].keys().iterator();
            while(keys.hasNext())
                queue.enqueue(keys.next());
        }
        return queue;
    }

    public void delete(Key key){
        st[hash(key)].delete(key);
    }

    public static void main(String[] args){
        SeparateChainningHashST schst = new SeparateChainningHashST(2);
        schst.put(1,2);
        schst.put(2,1);
        schst.delete(1);
        Iterator keys = schst.keys().iterator();
        while(keys.hasNext())
            System.out.println(keys.next());
        System.out.println();
        schst.put(3,2);
        keys = schst.keys().iterator();
        while(keys.hasNext())
            System.out.println(keys.next());
    }
}
