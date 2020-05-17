import math


class Solution:
    def numPoints(self, points, r):
        def dis(x1, y1, x2, y2):
            return math.sqrt(float((x1 - x2) ** 2) + float((y1 - y2) ** 2))

        def get_circle(x1, y1, x2, y2, r):
            midx = (x1 + x2) / 2.0
            midy = (y1 + y2) / 2.0
            angle = math.atan2(float(y2 - y1), float(x2 - x1))
            d = math.sqrt(r ** 2 - dis(x1, y1, midx, midy) ** 2)
            cx = midx + d * math.sin(angle)
            cy = midy - d * math.cos(angle)
            return cx, cy

        r = float(r)
        l = len(points)
        res = 1
        for i in range(l):
            for j in range(i + 1, l):
                if dis(points[i][0], points[i][1], points[j][0], points[j][1]) > r * 2:
                    continue
                cx, cy = get_circle(points[i][0], points[i][1], points[j][0], points[j][1], r)
                count = 0
                for k in range(l):
                    if dis(cx, cy, points[k][0], points[k][1]) < r + 1e-8:
                        count += 1
                res = max(res, count)
        return res