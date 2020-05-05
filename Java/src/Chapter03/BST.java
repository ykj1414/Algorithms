package Chapter03;

import Chapter01.Bag;
import Chapter01.Queue;
import com.sun.xml.internal.ws.api.ha.StickyFeature;

import java.util.Iterator;

public class BST<Key extends Comparable<Key>,Value> {
    private class Node{
        private Key key;
        private Value val;
        private Node left = null;
        private Node right = null;
        private int N;
        public Node(){
            this(null,null,0);
        }
        public Node(Key key,Value val,int n){
            this.key = key;
            this.val = val;
            this.N = n;
        }
    }
    private Node root;
    public BST(){
        root = new Node();
    }
    public BST(Key key,Value val){
        root = new Node(key,val,1);
    }
    public int size(){
        return size(root);
    }
    private int size(Node x){
        if(x==null) return 0;
        else return x.N;
    }
    public void put(Key key,Value val){
        root = put(root,key,val);
    }

    private Node put(Node x,Key key,Value val){
        if(x==null){
            Node node = new Node(key,val,1);
            return node;
        }
        int cmp = x.key.compareTo(key);
        if(cmp<0)
            x.right = put(x.right,key,val);
        else if (cmp>0)
            x.left = put(x.left,key,val);
        else
            x.val = val;
        x.N = size(x.left)+size(x.right)+1;
        return x;
    }

    public Value get(Key key){
        Node cur = root;
        while(cur!=null){
            int cmp = cur.key.compareTo(key);
            if(cmp<0) cur = cur.right;
            else if(cmp>0) cur = cur.left;
            else break;
        }
        return cur==null?null:cur.val;
    }

    public void delete(Key key){
        root = delete(root,key);
    }

    private Node delete(Node x,Key key){
        if(x==null)
            return null;
        int cmp = x.key.compareTo(key);
        if(cmp<0)
            x.right = delete(x.right,key);
        else if(cmp>0)
            x.left = delete(x.left,key);
        else{
            if(x.left==null)
                return x.right;
            else if(x.right==null)
                return x.left;
            else{
                Node m = Min(x);
                x = deleteMin(x);
                x.key = m.key;
                x.val = m.val;
            }
        }
        x.N = size(x.left)+size(x.right)+1;
        return x;
    }

    public Key Max(){
        return Max(root).key;
    }

    private Node Max(Node x){
        if(x.right==null)
            return x;
        else
            return Max(x.right);
    }

    private  Node Min(Node x){
        if(x.left!=null)
            return Min(x.left);
        else
            return x;
    }
    public Key Min(){
        return Min(root).key;
    }

    public void deleteMin(){
        root = deleteMin(root);
    }

    private Node deleteMin(Node x){
        if(x.left!=null)
            x.left = deleteMin(x.left);
        else
            return x.right;
        x.N = size(x.left)+size(x.right)+1;
        return x;
    }

    public void bstMidPrint(){
        midPrint(root);
    }

    private void midPrint(Node x){
        if(x.left!=null)
            midPrint(x.left);
        System.out.println(x.key);
        if(x.right!=null)
            midPrint(x.right);
    }

    public Key floor(Key key){
        Node x = floor(root,key);
        if(x==null) return null;
        return x.key;
    }

    private Node floor(Node x,Key key){
        if(x==null)
            return null;
        int cmp = x.key.compareTo(key);
        if(cmp==0)
            return x;
        if(cmp>0)
            return floor(x.left,key);
        Node t = floor(x.right,key);
        if(t!=null)
            return t;
        else
            return x;
    }

    public Key ceiling(Key key){
        Node x = ceiling(root,key);
        return x==null?null:x.key;
    }

    private Node ceiling(Node x,Key key){
        if(x==null) return null;
        int cmp = x.key.compareTo(key);
        if(cmp==0) return x;
        if(cmp<0)
            return ceiling(x.right,key);
        Node t = ceiling(x.left,key);
        if(t!=null)
            return t;
        else
            return x;
    }

    public Iterable<Key> keys(){
        return keys(Max(),Min());
    }

    public Iterable<Key> keys(Key min,Key max){
        Queue<Key> queue = new Queue<Key>();
        keys(root,queue,min,max);
        return queue;
    }

    private void keys(Node x,Queue<Key>queue,Key min,Key max){
        if(x==null) return;
        int cmpmin = x.key.compareTo(min);
        int cmpmax = x.key.compareTo(max);
        if(cmpmin>0)
            keys(x.left,queue,min,max);
        if(cmpmin>=0&&cmpmax<=0){
            queue.enqueue(x.key);
        }
        if(cmpmax<0)
            keys(x.right,queue,min,max);

    }

    public static void main(String[] args){
        BST bst = new BST('S',0);
        bst.put('E', 1);
        bst.put('A', 2);
        bst.put('R', 3);
        bst.put('C', 4);
        bst.put('H', 5);
        bst.put('M', 6);
        bst.put('X', 7);
        bst.put('M', 8);
        bst.put('P', 9);
        bst.put('L', 10);
//        System.out.println(bst.size());
//        bst.delete('L');
//        System.out.println(bst.size());
//        System.out.println(bst.get('L'));
//        System.out.println(bst.floor('G'));
//        System.out.println(bst.ceiling('G'));
        Iterator keys = bst.keys('F','T').iterator();
        while(keys.hasNext()){
            System.out.println(keys.next());
        }
    }
}
