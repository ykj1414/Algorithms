class Solution(object):
    def peopleIndexes(self, favoriteCompanies):
        """
        :type favoriteCompanies: List[List[str]]
        :rtype: List[int]
        """
        words = []
        help_list = []
        res = []
        d = {}
        for i in favoriteCompanies:
            i = sorted(i)
            word=''
            for k in i:
                word+=k+' '
            word = word[0:-1]
            help_list.append([len(words),len(word)])
            words.append(word)
            if not word in d:
                d[word] = 1
            else:
                d[word]+=1
        help_list = sorted(help_list,key = lambda x:x[1])
        index = 0
        while index<len(help_list):
            i = help_list[index][0]
            end = len(help_list)-1
            if d[words[i]]>1:
                continue
            while end>index and help_list[end][1]>=help_list[index][1]:
                k = help_list[end][0]
                flag = True
                for w in favoriteCompanies[i]:
                    if w not in words[k]:
                        flag = False
                        break
                if not flag:
                    end-=1
                else:
                    break
            else:
                res.append(i)
            index+=1
        return sorted(res)