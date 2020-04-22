import sys

dic = {'N':[0,-1],'S':[0,1],'W':[-1,0],'E':[1,0]}

def judge(x,e):
    while int(x)<=0:
        x+=e
    while int(x)>e:
        x-=e
    return x

def help(line,res,end):
    blacket = []
    r = []
    for i in line:
        if i==')':
            s = blacket.pop()
            r0 = r.pop()
            res[0]+=(res[0]-s[0])*r0
            res[1]+=(res[1]-s[1])*r0
            res[0] = judge(res[0],end)
            res[1] = judge(res[1],end)
        elif i=='(':
            cur = [i for i in res]
            blacket.append(cur)
        elif i>='2'and i<='9':
            r.append(ord(i)-49)
        else:
            res[0]+=dic[i][0]
            res[0] = judge(res[0],end)
            res[1]+=dic[i][1]
            res[1] = judge(res[1],end)
    while r:
        r0 = r.pop()
        s = blacket.pop()
        res[0]+=(res[0]-s[0])*r0
        res[1]+=(res[1]-s[0])*r0
        res[0] = judge(res[0], end)
        res[1] = judge(res[1], end)

if __name__=="__main__":
    t = int(sys.stdin.readline().strip())
    i = 1
    while i<=t:
        line = sys.stdin.readline().strip()
        res = [1,1]
        help(line,res,1e9)
        # print(res)
        print("Case #{}: {} {}".format(i,int(res[0]),int(res[1])))
        i+=1