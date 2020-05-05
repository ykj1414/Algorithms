package Chapter05;


import Chapter01.Queue;

public class TrieST<Value> {
    private static int N = 256;
    private Node root;
    private static class Node{
        private Object val;
        private Node[] next;
        public Node(){
            this(null);
        }
        public Node(Object item){
            val = item;
            next = (Node[])new Node[N];
        }
    }

    public TrieST(){
        root = new Node();
    }

    public void put(String key, Value val){
        put(root,key,0,val);
    }

    private void put(Node cur,String key,int index,Value val){
        int pos = key.charAt(index);
        if(cur.next[pos]==null)
            cur.next[pos] = new Node();
        if(index==key.length()-1){
            cur.next[pos].val = val;
            return;
        }
        put(cur.next[pos],key,index+1,val);
    }

    public Value get(String key){
        Node res = get(root,key,0);
        if(res==null)
            return null;
        return (Value)res.val;
    }

    private Node get(Node cur,String key ,int index){
        int pos = key.charAt(index);
        if(cur.next[pos]==null)
            return null;
        if(index == key.length()-1)
            return cur.next[pos];
        return get(cur.next[pos],key,index+1);
    }

    public int size(){
        return size(root);
    }

    private int size(Node cur){
        if(cur==null)
            return 0;
        int temp = 0;
        if(cur.val!=null)
            temp++;
        for(int i = 0;i<N;i++)
            temp+=size(cur.next[i]);
        return temp;
    }

    public void delete(String key){
        delete(root,key,0);
    }

    private Node delete (Node cur,String key,int index){
        if(cur==null)
            return null;
        if(index<key.length()){
            Node next = cur.next[key.charAt(index)];
            cur.next[key.charAt(index)] = delete(next,key,index+1);
        }
        cur.val=null;
        for(int i = 0;i<N;i++)
            if(cur.next[i]!=null)
                return cur;
        return null;
    }

    public Iterable<String> keys(){
        return keysWithPrefix("");
    }

    public Iterable<String> keysWithPrefix(String pre){
        Node cur = get(root,pre,0);
        if(cur==null)
            return null;
        Queue<String> item = new Queue<>();
        collect(cur,pre,item);
        return item;
    }

    private void collect(Node x,String pre,Queue<String> item){
        if(x==null)
            return;
        if(x.val!=null)
            item.enqueue(pre);
        for(int i = 0;i<N;i++)
            collect(x.next[i],pre+(char)i,item);
    }

    public Iterable<String> keysThatMatch(String pat){
        Queue<String> res = new Queue<>();
        collect(root,"",pat,res);
        return res;
    }
    
    private void collect(Node x,String pre,String pat,Queue<String> item){
        int d = pre.length();
        if(x==null) return;
        if(d==pat.length()&&x.val!=null) item.enqueue(pre);
        if(d==pat.length()) return;
        char c = pat.charAt(d);
        for(char i = 0;i<N;i++){
            if(i==d||d=='.')
                collect(x.next[i],pre+i,pat,item);
        }
    }

    public String longestPrefix(String s){
        int length = search(root,s,0,0);
        return s.substring(0,length);
    }

    private int search(Node x,String s,int index,int length){
        if(x==null) return length;
        if(x.val!=null) length = index;
        if(index==s.length()) return length;
        char c = s.charAt(index);
        return search(x.next[c],s,index+1,length);
    }

    public static void main(String [] args){
        TrieST tst = new TrieST();
        tst.put("see",1);
        tst.put("sea",1);
        tst.put("by",9);
        tst.put("are",0);
        tst.delete("are");
        System.out.println(tst.get("see"));
        System.out.println(tst.get("sea"));
        System.out.println(tst.get("by"));
        System.out.println(tst.get("are"));
        System.out.println(tst.size());
        for(Object s:tst.keysWithPrefix("s"))
            System.out.println(s);
        System.out.println(tst.longestPrefix("seashelles"));
    }
}
