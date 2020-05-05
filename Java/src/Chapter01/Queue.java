package Chapter01;

import java.util.Iterator;
import java.util.ListIterator;

//先进先出的FIFO队列
public class Queue<Item> implements Iterable<Item> {
    private int N = 0;
    private class Node{
        Item item;
        Node next;
    }
    private Node head=null;
    private Node tail=null;
    public void enqueue(Item item){
        Node node = new Node();
        node.item = item;
        node.next = null;
        if(isEmpty()){
            tail = node;
            head = tail;
        }
        else{
            tail.next = node;
            tail = node;
        }
        N++;
    }
    public Item dequeue(){
        if (isEmpty())
            return null;
        Item item = head.item;
        head = head.next;
        if(N==1)
            tail = tail.next;
        N--;
        return item;
    }
    public boolean isEmpty(){
        return N==0;
    }
    public int size(){
        return N;
    }

    @Override
    public Iterator<Item> iterator() {
        return new ListIterator();
    }
    private class ListIterator implements Iterator<Item>{
        private Node cur = head;
        @Override
        public boolean hasNext() {
            return cur!=null;
        }

        @Override
        public Item next() {
            Item item = cur.item;
            cur =cur.next;
            return  item;
        }
    }
}
