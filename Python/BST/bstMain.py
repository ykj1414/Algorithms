from BST import BST

if __name__ =="__main__":
    a = BST('S',0)
    a.put('E', 1)
    a.put('A', 2)
    a.put('R', 3)
    a.put('C', 4)
    a.put('H', 5)
    a.put('M', 6)
    a.put('X', 7)
    a.put('M', 8)
    a.put('P', 9)
    a.put('L', 10)
    # a.put('E',1)
    # a.put('A',2)
    # a.put('R',3)
    # a.put('C',4)
    # a.put('H',5)
    # a.put('M',6)
    # a.put('X',7)
    a.bstprint()
    # a.delete('E')
    print()
    a.bstprint()
    print()
    print(a.findKeys('E','H'))
    # a.put('A',8)
    # a.put('M',9)
    # a.put('P',10)
    # a.put('L',11)
    # a.put('E',12)
    # a.bstmidprint()
    # print(a.size())