class BalancedBST(object):

    #平衡搜索树的内部结点定义，

    class Node(object):
        def __init__(self,key=None,val=None):
            self.key = key      #结点Key值
            self.val = val      #结点value值
            self.h = 1          #结点的高度
            self.N = 1          #到该节点为止的节点总数
            self.left = None
            self.right = None

    def __init__(self):
        self.root = None


    def size(self):
        return self.__size(self.root)


    def __size(self,node:Node)->int:
        if not node:
            return 0
        return node.N

    def put(self,key,val=None):
        if not self.root:
            self.root = self.Node(key,val)
            return
        self.root = self.__put(self.root,key,val)

    def __put(self,node:Node,key,val):
        if not node:
            node = self.Node(key,val)
            return node
        if key>node.key:
            node.right = self.__put(node.right,key,val)
        elif key<node.key:
            node.left = self.__put(node.left,key,val)
        else:
            node.val = val
        return self.__balance(node)

    def __balance(self, node:Node):
        if(self.__height(node.left)-self.__height(node.right)>1):
            if not node.left.left or (self.__height(node.left.right)-self.__height(node.left.left)>0):
                node.left = self.__rotateLeft(node.left)
            node = self.__rotateRight(node)
        elif(self.__height(node.left)-self.__height(node.right)<-1):
            if not node.right.right or (self.__height(node.right.right) - self.__height(node.right.left)<0):
                node.right = self.__rotateRight(node.right)
            node = self.__rotateLeft(node)
        node.N = 1+self.__size(node.left)+self.__size(node.right)
        node.h = 1+max(self.__height(node.left),self.__height(node.right))
        return node
        pass



    def height(self):
        return self.__height(self.root)

    def __height(self,node:Node):
        if not node:
            return 0
        return  node.h

    #向左旋转
    def __rotateLeft(self,node:Node):
        cur = node.right
        node.right = cur.left
        cur.left = node
        cur.N = node.N
        node.N = 1+self.__size(node.left)+self.__size(node.right)
        #维持高度
        node.h = 1+max(self.__height(node.left),self.__height(node.right))
        return cur
        pass

    #向右旋转
    def __rotateRight(self,node:Node):
        cur = node.left
        node.left = cur.right
        cur.right = node
        cur.N = node.N
        node.N = 1+self.__size(node.left)+self.__size(node.right)
        node.h = 1+max(self.__height(node.left), self.__height(node.right))
        return cur
        pass

    def midPrint(self):
        self.__midPrint(self.root)

    def __midPrint(self,node:Node):
        if not node:
            return
        self.__midPrint(node.left)
        print(node.key,node.val,node.N,node.h)
        self.__midPrint(node.right)

    #平衡搜索树的删除操作
    def delete(self,key):
        self.root = self.__delete(self.root,key)
        pass

    def __delete(self,node:Node,key):
        if not node:
            return
        if key<node.key:
            node.left = self.__delete(node.left,key)
        elif key>node.key:
            node.right = self.__delete(node.right,key)
        else:
            if not node.right:
                return node.left
            cur = self.__Min(node.right)
            node.val,node.key = cur.val,cur.key
            node.right = self.__deleteMin(node.right)
        node.N = 1+self.__size(node.left)+self.__size(node.right)
        node.h = 1+max(self.__height(node.left), self.__height(node.right))
        return self.__balance(node)

    def min(self):
       return self.__Min(self.root)

    def __Min(self,node:Node):
        if not node.left:
            return node
        return self.__Min(node.left)

    def deleteMin(self):
        self.root = self.__deleteMin(self.root)

    def __deleteMin(self,node:Node):
        if not node.left:
            return None
        node.left = self.__deleteMin(node.left)
        node.N = 1 + self.__size(node.left) + self.__size(node.right)
        node.h = 1 + max(self.__height(node.left), self.__height(node.right))
        return self.__balance(node)

if __name__=="__main__":
    a = BalancedBST()
    b = [1,2,3,4,5,6,7,8,9,10,12,13]
    for i in b:
        a.put(i)
    a.midPrint()
    print()
    a.delete(13)
    a.delete(12)
    a.delete(9)
    a.midPrint()


