class Solution(object):
    def minDays(self, bloomDay, m, k):
        """
        :type bloomDay: List[int]
        :type m: int
        :type k: int
        :rtype: int
        """

        def check(day):
            n = len(bloomDay)
            flower = [True if v <= day else False for v in bloomDay]
            s, t = 0, 0
            count = 0
            while t < n:
                if not flower[t]:
                    count += (t - s) // k
                    s = t + 1
                t += 1
            count += (t - s) // k
            return count >= m

        left, right = min(bloomDay), max(bloomDay)
        while left <= right:
            day = (left + right) // 2
            if check(day):
                right = day - 1
            else:
                left = day + 1
        return left if check(left) else -1