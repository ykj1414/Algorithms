package Chapter01;

import java.util.Iterator;
import java.util.ListIterator;

public class Stack<Item> implements Iterable<Item> {
    @Override
    public Iterator<Item> iterator() {
        return new ListIterator();
    }
    private class ListIterator implements Iterator<Item>{
        private Node cur = first;
        @Override
        public boolean hasNext() {
            return cur!=null;
        }

        @Override
        public Item next() {
            Item item = cur.item;
            cur = cur.next;
            return item;
        }
    }
    private class Node{
        Item item;
        Node next;
    }
    private Node first=null;
    private int N = 0;
    public boolean isEmpty(){ return N==0;}
    public int size(){return N;}
    public void push(Item item){
        Node node = new Node();
        node.item = item;
        node.next = first;
        first = node;
        N++;
    }
    public Item pop(){
        if(isEmpty())
            return null;
        Item item = first.item;
        first = first.next;
        N--;
        return item;
    }
}
