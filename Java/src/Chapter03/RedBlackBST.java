package Chapter03;


import com.sun.org.apache.regexp.internal.RE;
import edu.princeton.cs.algs4.BlackFilter;
import edu.princeton.cs.algs4.Queue;

public class RedBlackBST<Key extends  Comparable<Key>,Value> {
    private static final boolean RED= true;
    private static final boolean BLACK = false;
    private Node root;
    private class Node{
        private boolean color = RED;            //用于表示父节点连接到当前节点的链接颜色
        private Key key;
        private Value val;
        private Node left,right;
        private int N = 0;
        public Node(){
            this(null,null,0);
            this.color = BLACK;
            //红黑树根节点默认是黒链接，如果是红链接，则其左孩子就无法是红链接
        }
        public Node(Key key,Value val,int n){
            this.key = key;
            this.val = val;
            this.N = n;
        }
    }
    public RedBlackBST(){
        root = null;
    }

    public RedBlackBST(Key key,Value val){
        root = new Node(key,val,1);
        root.color = BLACK;
    }

    private boolean isRed(Node x){
        if(x==null) return false;
        else return x.color==RED;
    }

    private int size(Node x){
        if(x==null) return 0;
        else return x.N;
    }
    //将右子节点红链接转移给左链接
    private Node rotateLeft(Node x){
        Node r = x.right;
        x.right = r.left;
        r.left = x;
        r.N = x.N;
        x.N = 1+size(x.left)+size(x.right);
        r.color = x.color;
        x.color = RED;
        return r;
    }

    //将左子节点红链接转移给右链接
    private Node rotateRight(Node x){
        Node l = x.left;
        x.left = l.right;
        l.right = x;
        l.N = x.N;
        x.N = 1+size(x.left)+size(x.right);
        l.color = x.color;
        x.color = RED;
        return l;
    }

    public void put(Key key,Value val){
        root = put(root,key,val);
        root.color = BLACK;
    }

    private void flipColor(Node x){
        x.right.color = isRed(x.right)?BLACK:RED;
        x.left.color = isRed(x.left)?BLACK:RED;
        x.color = x.color==RED?BLACK:RED;
    }

    private Node put(Node x,Key key,Value val){
        if(x==null){
            Node cur = new Node(key, val, 1);
            return cur;
        }
        //2-3-4树的插入操作，将翻转链接语句放在这，既能向上变换也能向下变换。
        //2-3-4树不是红黑树，允许左右子节点链接都为红。
//        if(isRed(x.left)&&isRed(x.right))
//            flipColor(x);
        int cmp = x.key.compareTo(key);
        if(cmp>0)
            x.left = put(x.left,key,val);
        else if(cmp<0)
            x.right = put(x.right,key,val);
        else
            x.val = val;

        //以下代码皆为自底向上插入的判断算法由于在递归之后判断链接颜色，所以无法向下改变链接颜色
        //可以参考算法书上的情况来理解，算法书上写的判断更少
        if(isRed(x.right)&&!isRed(x.left))
            x = rotateLeft(x);
        if(isRed(x.left)&&isRed(x.left.left))
            x = rotateRight(x);
        if(isRed(x.left)&&isRed(x.right))
            flipColor(x);

        //我的判断方式也是正确的，但是由于不判断左子树链接是否为红色
        // 如果在第一个条件就出现left和right都是红的情况，这么做会浪费时间,不如算法橙书的算法更快捷
//        if(isRed(x.right))
//            x = rotateLeft(x);
//        if(isRed(x.left)&&isRed(x.left.left)){
//            x = rotateRight(x);
//            flipColor(x);
//        }
//        if(isRed(x.left)&& isRed(x.left.right)){
//            x.left = rotateLeft(x.left);
//            x = rotateRight(x);
//            flipColor(x);
//        }
        x.N = size(x.left)+size(x.right)+1;
        return x;
    }

    public void delete(Key key){
        root = delete(root,key);
        if(root!=null)
            root.color = BLACK;
    }
    //沿着左链接往下走，需要翻转左子2-节点
    private Node  flipLeftRight(Node x){
        //在实际旋转过程中，只要左右孩子链接为黑，就需要翻转，有以下三种情况
        //1.如果左右子节点全为2-节点，翻转链接构建4-节点
        //2.如果左子节点为2-节点，右子节点为3-节点，需要将右子3-节点中的小值替换为root，
        //完成这个操作需要将左右孩子链接染红再进行旋转。
        //3.如果左子节点为3节点，那么在递归返回的时候，会通过条件判断把这链接重新配平
            flipColor(x);
        //如果左节点为2-节点，右节点为3-或者4-节点，进行左右旋转操作
        if(!isRed(x.left.left)&&isRed(x.right.left)){
            x = rotateLeftRight(x);
        }
        return x;
    }

    //沿着右链接往下走,可能删除最大值，需要翻转右子2-节点，确保删除时右子节点链接为红色
    private Node flipRightLeft(Node x){
        //如果左右子节点全为2-节点，翻转链接构建4-节点,这个与沿着左链接往下走一致
        //if(!isRed(x.left.left)&&!isRed(x.right.left))
            flipColor(x);
        //如果右节点为2节点左子节点为3-或者4-节点，进行右左旋转操作
        if(!isRed(x.right.left)&&isRed(x.left.left)){
            x = rotateRightLeft(x);
        }
        return x;
    }

    //红黑树删除操作中需要的旋转操作，具体图例可参考算法书p284图3.3.26
    private Node rotateLeftRight(Node x){
        //首先将x.right节点的左子节点x.right.left的红链接右旋转
        x.right = rotateRight(x.right);
        //将x.right子节点的红链接向左旋转
        x = rotateLeft(x);
        //将x本身的链接颜色还原，此时已经将原本的root节点和左孩子一起构成了新3-节点
        flipColor(x);
        return x;
    }

    private Node rotateRightLeft(Node x){
        //将x左孩子的红链接旋转到右边，原来的右孩子变为新x的右孙子节点
        x = rotateRight(x);
        //旋转完毕后，由于此时链接全为红，且将本身x的链接颜色反转了，现在需要重新翻转
        //注意此时右子树中已经多了一条红链接
        flipColor(x);
        return x;
    }

    private Node delete(Node x,Key key){
        if(x==null) return null;
        int cmp = x.key.compareTo(key);
        if(cmp<0){
            //如果当前节点左右子节点都为黑，进行翻转颜色操作
            if(isRed(x.left))
                x = rotateRight(x);
            if(!isRed(x.right)&&!isRed(x.right.left))
                if(x.right!=null&&x.left!=null)
                    x = flipRightLeft(x);
            x.right = delete(x.right,key);
        }
        else if(cmp>0){
            //判断条件不加isRed(x.left.left)也是可以的，但是会导致向上返回时多一次翻转链接操作
            //加了这个判断就相当于确定x.left是一个2-结点
            if(!isRed(x.left)&&!isRed(x.left.left))
                x = flipLeftRight(x);
            x.left = delete(x.left,key);
        }
        else{
            if(x.left==null)
                return null;
            if(!isRed(x.left)&&!isRed(x.left.left))
                x = flipLeftRight(x);
            //注意这步判断，当x经过flipLeftRight函数后，原本的x节点可能已经被旋转到左子节点
            //需要判断当前的x.key是否还是等于key，如果不是就沿着左链接往下走，如果是可以进行删除操作。
            if (x.key.compareTo(key)==0){
                //如果找到的是一个子树中的最大值，如果有左子节点，那么左子节点链接必为红色
                //先旋转再重新连接。
                if(x.right==null&&x.left!=null){
                    x = rotateRight(x);
                    x.right = x.right.left;
                }
                else{
                    Node temp = Min(x.right);
                    x.key = temp.key;
                    x.val = temp.val;
                    x.right = deleteMin(x.right);
                }
            }
            else x.left = delete(x.left,key);
        }
        if(!isRed(x.left)&&isRed(x.right))
            x = rotateLeft(x);
        if(isRed(x.left)&&isRed(x.left.left))
            x = rotateRight(x);
        if(isRed(x.left)&&isRed(x.right))
            flipColor(x);
        x.N = size(x.left)+size(x.right)+1;
        return x;
    }

    public Node Min(){
        return Min(root);
    }

    private Node Min(Node x){
        if(x.left==null)
            return x;
        return Min(x.left);
    }


    private Node deleteMin(Node x){
        if(x.left==null)
            return null;
        if(!isRed(x.left))
            x = flipLeftRight(x);
        x.left = deleteMin(x.left);
        if(!isRed(x.left)&&isRed(x.right))
            x = rotateLeft(x);
        if(isRed(x.left)&&isRed(x.left.left))
            x = rotateRight(x);
        if(isRed(x.left)&&isRed(x.right))
            flipColor(x);
        x.N = size(x.left)+size(x.right)+1;
        return x;
    }

    public void print(){
        Queue<Node> queue = new Queue<>();
        queue.enqueue(root);
        while(!queue.isEmpty()){
            Node cur = queue.dequeue();
            System.out.println(cur.key+" "+cur.val+" "+isRed(cur)+" "+size(cur));
            if(cur.left!=null) queue.enqueue(cur.left);
            if(cur.right!=null) queue.enqueue(cur.right);
        }
    }

    public void midPrint(){
        midPrint(root);
    }

    private void midPrint(Node x){
        if(x==null) return;
        midPrint(x.left);
        System.out.println(x.key+" "+x.val+" "+isRed(x));
        midPrint(x.right);
    }

    public void prePrint(){
        prePrint(root);
    }

    private void prePrint(Node x){
        if(x==null) return;
        System.out.println(x.key+" "+x.val+" "+isRed(x));
        prePrint(x.left);
        prePrint(x.right);
    }

    public static void main(String[] args){
        RedBlackBST a = new RedBlackBST();
        String bstr = "ACEHLMPRSX";
        int[] b = {1,2,3,4,5,6,7};
        //String bstr = "SEARCHXMPL";
//        for(int i = 0;i<bstr.length();i++){
//            char c = bstr.charAt(i);
//            a.put(c,0);
//        }
        for(int i = 0;i<7;i++) {
            a.put(b[i], 0);
        }
        a.print();
        System.out.println();
        a.delete(3);
//        a.delete(1);
//        a.delete(3);
//        a.delete(5);
        a.print();
//        System.out.println();
//        a.prePrint();
    }

}
