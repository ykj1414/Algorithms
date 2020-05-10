

# 5406. 收集树上所有苹果的最少时间 显示英文描述
# 给你一棵有 n 个节点的无向树，节点编号为 0 到 n-1 ，
# 它们中有一些节点有苹果。通过树上的一条边，需要花费 1 秒钟。
# 你从 节点 0 出发，请你返回最少需要多少秒，可以收集到所有苹果，
# 并回到节点 0 。
# 无向树的边由 edges 给出，其中 edges[i] = [fromi, toi] 
# ，表示有一条边连接 from 和 toi 。
# 除此以外，还有一个布尔数组 hasApple ，
# 其中 hasApple[i] = true 代表节点 i 有一个苹果，否则，节点 i 没有苹果。

class Solution(object):
    class Node(object):
        def __init__(self, val, left, right, paths):
            self.val = val
            self.next = []
            self.paths = paths

        def path(self, root):
            if not root:
                return 0
            else:
                return root.paths

    def dfs(self, node):
        if not node:
            return 0
        c_num = 0
        for i in node.next:
            c_num += self.dfs(i)

        node.paths = 2 if c_num > 0 or node.paths == 2 else 0
        return node.paths

    def minTime(self, n, edges, hasApple):
        """
        :type n: int
        :type edges: List[List[int]]
        :type hasApple: List[bool]
        :rtype: int
        """
        nodelist = []
        res = 0
        for i in range(n):
            node = self.Node(i, None, None, 2 if hasApple[i] else 0)
            nodelist.append(node)
        for i in range(len(edges)):
            nodelist[edges[i][0]].next.append(nodelist[edges[i][1]])
        self.dfs(nodelist[0])

        for k in range(1, n):
            i = nodelist[n - k]
            res += i.paths
            # print(i.val,i.paths,hasApple[i.val])
        return res