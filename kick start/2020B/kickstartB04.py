import sys

def dfs(square,x,y,w,h,way,cur):
    if x==h and y==w:
        way.append(cur)
        return
    if x>h or y>w or square[x][y]==-1:
        return
    if x==h or y==w:
        cur/=1
    else:
        cur/=2
    dfs(square,x+1,y,w,h,way,cur)
    dfs(square,x,y+1,w,h,way,cur)


if __name__=="__main__":
    t = int(sys.stdin.readline().strip())
    i = 1
    while i<=t:
        line = sys.stdin.readline().strip().split(' ')
        w,h,l = int(line[0]),int(line[1]),int(line[2])
        u,r,d = int(line[3]),int(line[4]),int(line[5])
        square = [[1]*(w) for i in range(h)]
        way = [0]
        for j in range(l-1,r):
            for k in range(u-1,d):
                square[k][j] = -1
        dfs(square,0,0,w-1,h-1,way,1)
        res = sum(way)
        print("Case #{}: {}".format(i,res))
        i+=1