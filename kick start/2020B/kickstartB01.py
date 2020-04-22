import sys


if __name__=="__main__":
    num = int(sys.stdin.readline().strip())
    i = 1
    while i<=num:
        n = int(sys.stdin.readline().strip())
        line = sys.stdin.readline().strip().split(' ')
        line = [int(i) for i in line]
        j = 1
        res = 0
        while j<=n-2:
            if line[j]>line[j-1] and line[j]>line[j+1]:
                res+=1
                j+=2
            elif line[j]>=line[j+1]:
                j+=2
            else:
                j+=1
        print("Case #{}: {}".format(i, res))
        i+=1
