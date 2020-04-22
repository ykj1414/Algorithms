import sys

def dfs(costdays,day,cost,res,index,end):
    if index==end:
        return True
    for c in costdays[index]:
        if c>=cost:
            res[index] = c
            if (dfs(costdays, day, c, res, index + 1, end)):
                return True
    return False



if __name__ =="__main__":
    t = int(sys.stdin.readline().strip())
    i = 1
    while i<=t:
        line = sys.stdin.readline().strip().split()
        n,d = int(line[0]),int(line[1])
        line = sys.stdin.readline().strip().split()
        line = [int(i) for i in line]
        res = [0]*n
        costday = []
        for k in range(n):
            cur = d//line[k]
            cost = [line[k]*(cur-j) for j in range(cur)]
            costday.append(cost)
        dfs(costday,d,1,res,0,len(line))
        print("Case #{}: {}".format(i, res[0]))
        i+=1
