# 题目:
# 在一个小城市里，有 m 个房子排成一排，你需要给每个房子涂上 n 种颜色之一（颜色编号为 1 到 n ）。有的房子去年夏天已经涂过颜色了，所以这些房子不需要被重新涂色。
#
# 我们将连续相同颜色尽可能多的房子称为一个街区。（比方说 houses = [1,2,2,3,3,2,1,1] ，它包含 5 个街区  [{1}, {2,2}, {3,3}, {2}, {1,1}] 。）
#
# 给你一个数组 houses ，一个 m * n 的矩阵 cost 和一个整数 target ，其中：
#
# houses[i]：是第 i 个房子的颜色，0 表示这个房子还没有被涂色。
# cost[i][j]：是将第 i 个房子涂成颜色 j+1 的花费。
# 请你返回房子涂色方案的最小总花费，使得每个房子都被涂色后，恰好组成 target 个街区。如果没有可用的涂色方案，请返回 -1 。
# 题解： 使用动态规划 构造三维dp数组，代码本身还能继续优化

class Solution:
    def minCost(self, houses, cost, m: int, n: int, target: int) -> int:
        res = [[[float("inf")]*(target+1) for i in range(n+1)] for i in range(m+1)]
        # 不存在的第0栋房子只能形成0个分组，对于第0栋房子的n中不存在的颜色，每个颜色分为0组的成本都为0
        # 这步是关键的dp初始化
        for i in range(1,n+1):
            res[0][i][0] = 0
        for i in range(1,m+1):
            for j in range(1,n+1):
                for k in range(1,target+1):
                    #这里的逻辑可以简化 将cost[i-1][j-1]做成一个变量C
                    #通过判断当前房子有没有上色 决定C 的值是0还是对应的颜色成本
                    if houses[i-1]:
                        if j!=houses[i-1]:
                            continue
                        for l in range(1,n+1):
                            if l!=j:
                                res[i][j][k] = min(res[i][j][k],res[i-1][l][k-1])
                            else:
                                res[i][j][k] = min(res[i][j][k],res[i-1][j][k])
                    else:
                        for l in range(1,n+1):
                            if l!=j:
                                res[i][j][k] = min(res[i][j][k],res[i-1][l][k-1]+cost[i-1][j-1])
                            else:
                                res[i][j][k] = min(res[i][j][k],res[i-1][j][k]+cost[i-1][j-1])
        min_res = float("inf")
        for i in range(1,n+1):
            min_res = min(min_res,res[m][i][target])
        return min_res if min_res<float("inf") else -1