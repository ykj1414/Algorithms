//
//  Graph.c
//  HWCMT
//
//  Created by YING on 2020/4/6.
//  Copyright © 2020 YING. All rights reserved.
//

#include "Graph.h"

int gcmp(const void*a,const void*b){
    return *(int*)a-*(int*)b;
}

static void graphFree(Graph *graph){
    Graph g = *graph;
    freeHashList(g->hashMap);
    freeHashList(g->judgeMap);
    listFree(&g->lvp);
    free(g->hashMap);
    free(g->judgeMap);
    free(g);
    *graph = NULL;
}


Graph createGraph(void){
    Graph g = (Graph)malloc(sizeof(struct graph));
    g->hashMap = createHashMap();
    g->judgeMap = createHashMap();
    g->V = 0;
    g->addadj = addGraphAdj;
    g->E = 0;
    g->gfree = graphFree;
    g->findCycle = findGraphCycle;
    g->lvp = createList();                     //顶点数组长度初始化为8
    return g;
}

static void addGraphAdj(Graph g,void* key,void*value){
    Put(g->hashMap, key, value);
}

//static void findGraphCycle(Graph g,int sp,int* list,int lSize)
static void findGraphCycle(Graph g,int sp,int* list,int lSize,int ***res,int *resSize){
    if (lSize>7){
        return;
    }
    unsigned long index = HashCode(g->hashMap, &sp);
    if(!g->hashMap->list[index]) return;
    g->judgeMap->list[index]->node->val = 1;
    Node cur = g->hashMap->list[index]->node;
    while(cur){
        if(cur->val<list[0]){
            cur= cur->next;
            continue;
        }
        unsigned long n_index = HashCode(g->hashMap, &(cur->val));
        if(g->judgeMap->list[n_index] && !g->judgeMap->list[n_index]->node->val){
            list[lSize] = cur->val;
            findGraphCycle(g, cur->val, list,lSize+1,res,resSize);
        }
        if(cur->val==list[0] && lSize>=3){
            int index = resSize[lSize-3];
            res[lSize-3][index] = (int*)malloc(sizeof(int)*lSize);
            for(int i = 0;i<lSize;i++) res[lSize-3][index][i] = list[i];
            resSize[lSize-3]++;
        }
        cur = cur->next;
    }
    g->judgeMap->list[index]->node->val = 0;
}


static void appendInt(List l,int val){
    if(l->size>=l->length){
        reSetList(l,l->length+l->length/2);
    }
    l->vp[l->size++] = val;
}

static void listFree(List *l){
    free((*l)->vp);
    (*l)->vp = NULL;
    free(*l);
    *l = NULL;
}

List createList(void){
    List list = (List)malloc(sizeof(struct list));
    list->size = 0;
    list->length = 8;
    list->vp = (int*)malloc(sizeof(int)*list->length);
    list->append = appendInt;
    return list;
}

static void reSetList(List l,int length){
    int *temp = (int*)malloc(sizeof(int)*l->size);
    for(int i = 0;i<l->size;i++) temp[i] = l->vp[i];
    free(l->vp);
    l->vp = NULL;
    l->vp = (int*)malloc(sizeof(int)*length);
    l->length = length;
    for(int i = 0;i<l->size;i++) l->vp[i] = temp[i];
    free(temp);
}
