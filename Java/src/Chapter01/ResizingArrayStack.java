package Chapter01;
//后进先出，LIFO 栈
import java.util.Iterator;

public class ResizingArrayStack<Item> implements Iterable<Item> {
    private int N = 0;
    private Item[]a = (Item[])new Object[1];
    public boolean isEmpty(){
        return N==0;
    }
    public void push(Item item){
        if(N==a.length) resize(2*N);
        a[N++] = item;
    }
    public Item pop(){
        Item res = a[--N];
        a[N] = null;
        if(N>0 && N==a.length/4)
            resize(a.length/2);
        return res;
    }
    public int size(){
        return N;
    }
    private void resize(int max){
        Item[]temp = (Item[])new Object[max];
        for(int i = 0;i<N;i++)
            temp[i] = a[i];
        a = temp;
    }
    @Override
    public Iterator<Item> iterator() {
        return new ReverseArrayIterator();
    }
    private class ReverseArrayIterator implements Iterator<Item>{
        private int i = N;
        public boolean hasNext(){return i>0;}
        public Item next(){return a[--i];}
        public void remove(){

        }
    }
    public static void main(String[] args){
        ResizingArrayStack<Integer> nums = new ResizingArrayStack<>();
        nums.push(1);
        nums.push(2);
        nums.push(3);
        for(Integer a:nums)
            System.out.println(a);
    }
}
