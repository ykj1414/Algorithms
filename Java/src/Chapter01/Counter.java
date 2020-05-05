package Chapter01;

public class Counter {
    private final String name;
    private static int n = 0;
    Counter(String id){
        name = id;
    }
    public void increment(){
        n++;
    }
    public int tally(){
        return n;
    }
    public String toString(){
        return name+" total increment "+n;
    }

    public static void main(String[] args){
        Counter head = new Counter("head");
        head.increment();
        System.out.println(head.tally());
        System.out.println(head);
        Counter tail = new Counter("tail");
        tail.increment();
        System.out.println(tail.tally());
        System.out.println(tail);
    }
}
