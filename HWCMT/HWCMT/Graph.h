//
//  Graph.h
//  HWCMT
//
//  Created by YING on 2020/4/6.
//  Copyright © 2020 YING. All rights reserved.
//

#ifndef Graph_h
#define Graph_h

#include <stdio.h>
#include "HashMap.h"
#include <stdlib.h>
#define Put(map, key, value) map->put(map, (void *)key, (void *)value);
#define Get(map, key) (char *)map->get(map, (void *)key)
#define Remove(map, key) map->remove(map, (void *)key)
#define Existe(map, key) map->exists(map, (void *)key)
#define HashCode(map,key) map->hashCode(map,(void*)key)
#define freeHashList(map) map->freeList(map)


typedef struct list*List;

typedef void(*Append)(List l,int val);

typedef void(*LFree)(List *l);

typedef struct list{
    int size;
    int length;
    int *vp;
    Append append;
    LFree lfree;
}*List;

List createList(void);

static void appendInt(List l,int val);

static void listFree(List *l);

static void reSetList(List l,int length);

typedef struct graph *Graph;

typedef void(*FindCycle)(Graph g,int sp,int*,int,int***,int*);

typedef void(*AddAdj)(Graph g,void*key,void *value);

typedef void(*GFree)(Graph *g);

typedef struct graph{
    HashMap hashMap;            //图的邻接表
    HashMap judgeMap;           //记录顶点是否在递归中
    int V;                      //图的顶点数
    List lvp;                   //顶点的数量使用可增长数组保存
    int E;                      //图的边数
    FindCycle findCycle;        //寻找环
    AddAdj addadj;              //添加顶点和边
    GFree gfree;
    int ***res;                  //返回的结果数
    int *resSize;
}*Graph;

static void graphFree(Graph*);

//static void findGraphCycle(Graph,int,int*,int);

static void findGraphCycle(Graph g,int sp,int* list,int lSize,int ***res,int *resSize);

static void addGraphAdj(Graph,void*,void*);

Graph createGraph(void);

#endif /* Graph_h */
