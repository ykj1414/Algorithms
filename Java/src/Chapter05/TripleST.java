package Chapter05;

public class TripleST<Key,Value>{

    private class Node{
        private char key;
        private Value val;
        private Node left,right;
        private Node mid;

        public Node(char c){
            this(c,null);
        }

        public Node(char c,Value val){
            this.key = c;
            this.val = val;
            this.left = null;
            this.right = null;
            this.mid = null;
        }
    }

    private Node root;

    public void put(String s,Value val){
        root = put(root,s,val,0);
    }

    private Node put(Node cur,String s,Value val,int index){
        char c = s.charAt(index);
        if(cur==null){
            cur = new Node(c);
        }
        if(cur.key<c) cur.right = put(cur.right,s,val,index);
        else if (cur.key>c) cur.left = put(cur.left,s,val,index);
        else if(index<s.length()-1)
            cur.mid = put(cur.mid,s,val,index+1);
        else
            cur.val = val;
        return cur;
    }

    public Value get(String s){
        Node res = get(root,s,0);
        if(res==null)
            return null;
        return res.val;
    }

    private Node get(Node cur,String s,int index){
        if(index==s.length()-1)
            return cur;
        if(cur==null)
            return cur;
        if(cur.key>s.charAt(index))
            return get(cur.left,s,index);
        else if(cur.key<s.charAt(index))
            return get(cur.right,s,index);
        else
            return get(cur.mid,s,index+1);
    }

    public static void main(String[] args){
        TripleST tst = new TripleST();
        tst.put("see",1);
        tst.put("sea",1);
        tst.put("by",9);
        tst.put("are",0);
        System.out.println(tst.get("see"));
        System.out.println(tst.get("sea"));
        System.out.println(tst.get("by"));
        System.out.println(tst.get("are"));
    }

}
