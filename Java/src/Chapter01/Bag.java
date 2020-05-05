package Chapter01;
//背包实现，方便后面算法使用
import java.util.Iterator;
public class Bag<Item>implements Iterable<Item> {
    private int N = 0;
    private class Node{
        Item item;
        Node next;
    }
    private Node head;
    public void add(Item item){
        Node node = new Node();
        node.item = item;
        node.next = head;
        head = node;
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
            cur = cur.next;
            return item;
        }
    }
}
