package Chapter03;


//二叉平衡树代码 Python版本也有对应代码，相比红黑树，AVL树的实现很简单
public class BalancedBST <Key extends Comparable<Key>,Value>{

    private class Node{
        private int N;
        private int h;
        private Node left,right;
        private Key key;
        private Value val;
        public Node(Key key){
            this(key,null);
        }
        public Node(Key key,Value val){
            N = 1;
            h = 1;
            left = null;
            right = null;
            this.key = key;
            this.val = val;
        }
    }

    private Node root;

    public BalancedBST(){
        root = null;
    }

    public void put(Key key ,Value val){
        root = put(root,key,val);
    }

    private Node put(Node root,Key key,Value val){
        if(root==null){
            Node cur = new Node(key,val);
            return cur;
        }
        int cmp = key.compareTo(root.key);
        if(cmp<0)
            root.left = put(root.left,key,val);
        else if(cmp>0)
            root.right = put(root.right,key,val);
        else
            root.val = val;

        return balance(root);
    }

    public void delete(Key key){
        root = delete(root,key);
    }

    private Node delete(Node node,Key key){
        if(node==null) return null;
        int cmp = key.compareTo(node.key);
        if(cmp<0)
            node.left = delete(node.left,key);
        else if(cmp>0)
            node.right = delete(node.right,key);
        else{
            if(node.right==null)
                return node.left;
            Node cur = Min(node.right);
            node.key = cur.key;
            node.val = cur.val;
            node.right = deleteMin(node.right);
        }
        return balance(node);
    }

    public void deleteMin(){
        root = deleteMin(root);
    }

    private Node deleteMin(Node node){
        if(node.left==null) return node.right;
        node.left = deleteMin(node.left);
        return balance(node);
    }

    public Node Min(){
        return Min(root);
    }

    private Node Min(Node node){
        if(node.left==null) return node;
        return Min(node.left);
    }

    private Node balance(Node root){
        if(size(root.left)-size(root.right)>1){
            if(root.left.left==null||size(root.left.left)-size(root.left.right)<0){
                root.left = rotateLeft(root.left);
            }
            root = rotateRight(root);
        }
        else if(size(root.left)-size(root.right)<-1){
            if(root.right.right==null||size(root.right.right)-size(root.right.left)<0){
                root.right = rotateRight(root.right);
            }
            root = rotateLeft(root);
        }
        root.h = 1+Math.max(height(root.left),height(root.right));
        root.N = 1+size(root.left)+size(root.right);
        return root;
    }


    //向左旋转
    private Node rotateLeft(Node root){
        Node cur = root.right;
        root.right = cur.left;
        cur.left = root;
        cur.N = root.N;
        root.N = 1+size(root.left)+size(root.right);
        root.h = 1+Math.max(height(root.left),height(root.right));
        return cur;
    }


    //向右旋转
    private Node rotateRight(Node root){
        Node cur = root.left;
        root.left = cur.right;
        cur.right = root;
        cur.N = root.N;
        root.N = 1+size(root.left)+size(root.right);
        root.h = 1+Math.max(height(root.left),height(root.right));
        return cur;
    }

    public int size(){
        return size(root);
    }

    private int size(Node node){
        if(node==null) return 0;
        return node.N;
    }

    public int height(){
        return height(root);
    }

    private int height(Node node){
        if(node==null) return 0;
        return node.h;
    }

    public void midPrint(){
        midPrint(root);
    }

    private void midPrint(Node node){
        if(node==null) return;
        midPrint(node.left);
        System.out.println(node.key+" "+node.N+" "+node.h);
        midPrint(node.right);
    }

    public static void main(String[] args){
        int[] a = {1,2,3,4,5,6,7,8,9};
        BalancedBST avlT = new BalancedBST();
        for(int i = 0;i<a.length;i++){
            avlT.put(a[i],0);
        }
        avlT.midPrint();
        avlT.delete(3);
        avlT.delete(2);
        avlT.delete(1);
        avlT.delete(4);
        avlT.delete(5);
        System.out.println();
        avlT.midPrint();
    }
}
