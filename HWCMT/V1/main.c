//
//  main.c
//  HWCMT
//
//  Created by YING on 2020/4/4.
//  Copyright © 2020 YING. All rights reserved.
//

#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#define MAX_V 280000
#define MAX_C 25
//#include "HashMap.h"
//#include "Graph.h"
#define Put(map, key, value) map->put(map, (void *)key, (void *)value);
#define Get(map, key) (char *)map->get(map, (void *)key)
#define Remove(map, key) map->remove(map, (void *)key)
#define Existe(map, key) map->exists(map, (void *)key)
#define HashCode(map,key) map->hashCode(map,(void*)key)
#define freeHashList(map) map->freeList(map)
#define append(List,val) List->append(List,val)

#define newHashMapIterator() (HashMapIterator)malloc(sizeof(struct hashMapIterator))
#define newHashMap() (HashMap)malloc(sizeof(struct hashMap))
#define newKey() (void*)malloc(sizeof(void))


//键存储采用无类型指针，value使用链表存储

typedef struct node *Node;

typedef void (*NodeFree)(Node *node);

typedef struct node{
    int val;
    struct node *next;
    NodeFree nodeFree;
}*Node;

static Node newNode(void);

static void nodeFree(Node *node);

typedef  struct entry *Entry;

typedef void (*EntryFree)(Entry *entry);

typedef struct entry{
    void *key;
    Node node;
    struct entry*next;
    EntryFree entryFree;
}*Entry;

static Entry newEntry(void);

static Entry* newEntryList(int n);

static void entryFree(Entry *entry);


//通过枚举定义布尔返回值
typedef enum {false=0,true} Boolean;

//预先定义hashMap结构体别名
typedef struct hashMap *HashMap;

//定义各种函数类型，并设置为函数指针
typedef unsigned long(*HashCode)(HashMap, const void * key);

typedef Boolean(*Equal)(const void * key1,const void * key2);

typedef void(*Put)(HashMap hashMap, void * key,void * value);

typedef void*(*Get)(HashMap hashMap,const void *key);

typedef void(*Clear)(HashMap hashMap);

typedef void(*Remove)(HashMap,const void * key);

typedef Boolean(*Exists)(HashMap hashMap,const void *key);

typedef void(*FreeList)(HashMap hashmap);

typedef struct hashMap {
    int size;           // 当前大小
    int listSize;       // 有效空间大小
    HashCode hashCode;  // 哈希函数
    Equal equal;        // 判等函数
    Entry *list;         // 存储区域
    Put put;            // 添加键的函数
    Get get;            // 获取键对应值的函数
    Remove remove;      // 删除key中元素，但保留key
    Clear clear;        // 清空Map
    Exists exists;      // 判断键是否存在
    Boolean autoAssign;    // 设定是否根据当前数据量动态调整内存大小，默认开启
    FreeList freeList;             //哈希列表释放
}*HashMap;


static unsigned long defaultHashCode(HashMap hashMap, const void * key);

static Boolean defaultEqual(const void * key1, const void * key2);

static void defaultPut(HashMap hashMap,void *key,void *value);

static void*defaultGet(HashMap hashMap,const void *key);

static void defaultClear(HashMap hashMap);

static Boolean defaultExists(HashMap,const void *key);

static void defaultRemove(HashMap,const void*key);

static void hashListFree(HashMap hashmap);

typedef struct hashMapIterator {
    Entry entry;    // 迭代器当前指向
    int count;      // 迭代次数
    int index;   // 键值对的哈希值
    HashMap hashMap;
}*HashMapIterator;



// 创建一个哈希结构,直接使用默认方法，不调用函数指针
HashMap createHashMap(void);

// 创建哈希结构迭代器
HashMapIterator createHashMapIterator(HashMap hashMap);

// 迭代器是否有下一个
Boolean hasNextHashMapIterator(HashMapIterator iterator);

// 迭代到下一次
HashMapIterator nextHashMapIterator(HashMapIterator iterator);

// 释放迭代器内存
void freeHashMapIterator(HashMapIterator * iterator);


static void nodeFree(Node *node){
    free(*node);
    *node = NULL;
}

static Node newNode(void){
    Node node = (Node)malloc(sizeof(struct node));
    node->next = NULL;
    node->val = -1;
    node->nodeFree = nodeFree;
    return node;
}

static void entryFree(Entry *entry){
    free((*entry)->key);
    while((*entry)->node){
        Node node = (*entry)->node;
        (*entry)->node = node->next;
        nodeFree(&node);
        node = NULL;
    }
    free(*entry);
    *entry = NULL;
}

static void hashListFree(HashMap hashmap){
    for(int i = 0;i<hashmap->listSize;i++){
        while (hashmap->list[i]) {
            Entry next = hashmap->list[i]->next;
            entryFree(&hashmap->list[i]);
            hashmap->list[i] = next;
        }
    }
    free(hashmap->list);
    hashmap->list = NULL;
}

static Entry newEntry(void){
    Entry entry = (Entry)malloc(sizeof(struct entry));
    entry->key = newKey();
    //entry->key = NULL;
    entry->node = NULL;
    entry->next = NULL;
    entry->entryFree = entryFree;
    return entry;
}

static Entry* newEntryList(int n){
    Entry *entryList = (Entry*)(malloc(sizeof(Entry)*n));
    for (int i = 0; i<n; i++) {
        entryList[i] = NULL;
    }
    return entryList;
}

static void defaultRemove(HashMap hashMap,const void * key){
    unsigned long index  = defaultHashCode(hashMap, key);
    Entry current = hashMap->list[index];
    if(defaultEqual(current->key,key)){
        hashMap->list[index] = current->next;
        entryFree(&current);
    }
}

static unsigned long defaultHashCode(HashMap hashMap, const void * key){
    int k = *(int*)key;
    unsigned long h = 0;
    h = (h << 4) + k;
    unsigned long g = h & 0xF0000000L;
    if (g) {
        h ^= g >> 24;
    }
    h &= ~g;
    return h%hashMap->listSize;
}

Boolean defaultExists(HashMap hashMap, const void* key) {
    unsigned long index = hashMap->hashCode(hashMap, key);
    Entry entry = hashMap->list[index];
    if (entry->key == NULL) {
        return false;
    }
    if (hashMap->equal(entry->key, key)) {
        return true;
    }
    if (entry->next != NULL) {
        do {
            if (hashMap->equal(entry->key, key)) {
                return true;
            }
            entry = entry->next;

        } while (entry != NULL);
        return false;
    }
    else {
        return false;
    }
}

void* defaultGet(HashMap hashMap, const void* key) {
    unsigned long index = hashMap->hashCode(hashMap, key);
    Entry entry = (hashMap->list[index]);
    while (entry->key != NULL && !hashMap->equal(entry->key, key)) {
        entry = entry->next;
    }
    return entry->node;
}

Boolean defaultEqual(const void*key1,const void *key2){
    return *(int*)key1==*(int*)key2?true:false;
}

void defaultClear(HashMap hashMap) {
    for (int i = 0; i < hashMap->listSize; i++) {
        Entry entry = hashMap->list[i]->next;
        while (entry != NULL) {
            Entry next = entry->next;
            free(entry);
            entry = next;
        }
        hashMap->list[i]->next = NULL;
    }
    free(hashMap->list);
    hashMap->list = NULL;
    hashMap->size = -1;
    hashMap->listSize = 0;
}

HashMapIterator createHashMapIterator(HashMap hashMap) {
    HashMapIterator iterator = newHashMapIterator();
    iterator->hashMap = hashMap;
    iterator->count = 0;
    iterator->index = -1;
    iterator->entry = NULL;
    return iterator;
}

Boolean hasNextHashMapIterator(HashMapIterator iterator) {
    return iterator->count < iterator->hashMap->size ? true : false;
}

HashMapIterator nextHashMapIterator(HashMapIterator iterator) {
    if (hasNextHashMapIterator(iterator)) {
        if (iterator->entry != NULL && iterator->entry->next != NULL) {
            iterator->count++;
            iterator->entry = iterator->entry->next;
            return iterator;
        }
        while (++iterator->index < iterator->hashMap->listSize) {
            Entry entry = iterator->hashMap->list[iterator->index];
            if (entry) {
                iterator->count++;
                iterator->entry = entry;
                break;
            }
        }
    }
    return iterator;
}

void freeHashMapIterator(HashMapIterator * iterator) {
    free(*iterator);
    *iterator = NULL;
}

void resetHashMap(HashMap *hM, int listSize) {
    HashMap hashMap = *hM;
    if (listSize < 8) return;
    // 键值对临时存储空间
    Entry *tempList = newEntryList(hashMap->size);
    HashMapIterator iterator = createHashMapIterator(hashMap);
    int length = hashMap->size;
    for (int index = 0; hasNextHashMapIterator(iterator); index++) {
        // 迭代取出所有键值对
        iterator = nextHashMapIterator(iterator);
        tempList[index] = newEntry();
        *(int*)tempList[index]->key = *(int*)iterator->entry->key;
        tempList[index]->node = newNode();
        Node cur = iterator->entry->node;
        Node p = tempList[index]->node;
        p->val = cur->val;
        while(cur->next){
            cur = cur->next;
            Node node = newNode();
            node->val = cur->val;
            p->next = node;
            p = p->next;
        }
        p->next = NULL;
        tempList[index]->next = NULL;
    }
    freeHashMapIterator(&iterator);

    // 清除原有键值对数据
    hashListFree(hashMap);
    hashMap->listSize = listSize;
    hashMap->list = newEntryList(hashMap->listSize);
    hashMap->size = 0;
    for(int i = 0;i<length;i++){
        hashMap->put(hashMap,tempList[i]->key,&(tempList[i]->node->val));
        entryFree(&tempList[i]);
    }
    free(tempList);
}

static void defaultPut(HashMap hashMap,void *key,void *value){
    if (hashMap->autoAssign && hashMap->size >= hashMap->listSize) {
            resetHashMap(&hashMap, hashMap->listSize+hashMap->listSize/2);
        }
    unsigned long index = hashMap->hashCode(hashMap, key);
    if (hashMap->list[index]==NULL) {
        hashMap->size++;
        // 该地址为空时直接存储
        hashMap->list[index] = newEntry();
        *(int*)hashMap->list[index]->key = *(int*)key;
        Node node = newNode();
        hashMap->list[index]->node= node;
        node->val = *(int*)value;
        node->next = NULL;
    }
    else {
        Entry current = hashMap->list[index];
        while (current != NULL) {
            if (hashMap->equal(key, current->key)) {
                // 对于键值已经存在,往value中的链表进行头插
                Node node = newNode();
                node->val = *(int*)value;
                if(node->val<current->node->val){
                    node->next = hashMap->list[index]->node;
                    hashMap->list[index]->node = node;
                }
                else{
                    Node cur = current->node;
                    while (cur->next) {
                        if(cur->next->val>node->val){
                            node->next = cur->next;
                            cur->next = node;
                            return;
                        }
                        cur = cur->next;
                    }
                    cur->next = node;
                }
                return;
            }
            current = current->next;
        };
        // 发生冲突则创建节点挂到相应位置的next上
        Entry entry = newEntry();
        *(int*)entry->key = *(int*)key;
        Node node = newNode();
        node->val = *(int*)value;
        node->next = NULL;
        entry->node= node;
        entry->next = hashMap->list[index]->next;
        hashMap->list[index]->next = entry;
        hashMap->size++;
    }
}
HashMap createHashMap(void) {
    HashMap hashMap = newHashMap();
    hashMap->size = 0;
    hashMap->listSize = 500000;
    hashMap->hashCode = defaultHashCode;
    hashMap->equal =  defaultEqual;
    hashMap->exists = defaultExists;
    hashMap->get = defaultGet;
    hashMap->put = defaultPut;
    hashMap->clear = defaultClear;
    hashMap->remove = defaultRemove;
    hashMap->autoAssign = true;
    hashMap->freeList = hashListFree;
    // 起始分配8个内存空间，溢出时会自动扩充
    hashMap->list = newEntryList(hashMap->listSize);
    return hashMap;
}



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

typedef void(*FindCycle)(Graph g,int sp,int*,int);

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

static void findGraphCycle(Graph,int,int*,int);

static void addGraphAdj(Graph,void*,void*);

Graph createGraph(void);


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
    for(int i = 0;i<5;i++){
        for(int j = 0;j<g->resSize[i];j++){
            free(g->res[i][j]);
        }
        free(g->res[i]);
    }
    free(g->res);
    free(g->resSize);
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
    g->res = (int***)malloc(sizeof(int**)*5);
    g->resSize = (int*)calloc(5,sizeof(int));
    for (int i = 0;i<5;i++) g->res[i] = (int**)malloc(sizeof(int*)*1000000);
    return g;
}

static void addGraphAdj(Graph g,void* key,void*value){
    Put(g->hashMap, key, value);
}


static void findGraphCycle(Graph g,int sp,int* list,int lSize){
    if (lSize>7){
        return;
    }
    unsigned long index = HashCode(g->hashMap, &sp);
    if(!g->hashMap->list[index]) return;
    g->judgeMap->list[index]->node->val = 1;
    Node cur = g->hashMap->list[index]->node;
    while(cur){
        if(cur->val<list[0]){
            cur = cur->next;
            continue;
        }
        unsigned long n_index = HashCode(g->hashMap, &(cur->val));
        if(g->judgeMap->list[n_index] && !g->judgeMap->list[n_index]->node->val){
            list[lSize] = cur->val;
            findGraphCycle(g, cur->val, list,lSize+1);
        }
        if(cur->val==list[0] && lSize>=3){
            int index = g->resSize[lSize-3];
            g->res[lSize-3][index] = (int*)malloc(sizeof(int)*lSize);
            for(int i = 0;i<lSize;i++) g->res[lSize-3][index][i] = list[i];
            //qsort(g->res[lSize-3][index],lSize,sizeof(int),gcmp);
            g->resSize[lSize-3]++;
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



int cmp(const void *a,const void *b){
    return *(int*)a-*(int*)b;
}

int main(int argc, const char * argv[]) {
    // insert code here...
//    clock_t start,end;
//    start = clock();
    int from=0,to=0,money= 0;
    Graph g = createGraph();
    FILE *f1 = fopen("/data/test_data.txt", "r");
//    FILE *f1 = fopen("/Users/ying/Desktop/初赛/HWcode2020-TestData-master/testData/test_data_10000_60000.txt", "r");
    int a = 0;
    while(fscanf(f1, "%d,%d,%d\n",&from,&to,&money)!=EOF){
        g->E+=1;
        Put(g->judgeMap,&from,&a);
        g->addadj(g,&from,&to);
    }
    g->V = g->hashMap->size;
    fclose(f1);
    HashMapIterator itr = createHashMapIterator(g->hashMap);
    while (hasNextHashMapIterator(itr)){
        itr = nextHashMapIterator(itr);
        append(g->lvp, *(int*)itr->entry->key);
    }
    freeHashMapIterator(&itr);
    qsort(g->lvp->vp, g->lvp->size,sizeof(int), cmp);
    int *r = (int*)malloc(sizeof(int)*7);
    for(int i = 0;i<g->lvp->size;i++){
        r[0] = g->lvp->vp[i];
        g->findCycle(g,r[0],r,1);
    }
    free(r);
//    end = clock();
//    printf("time=%f\n",(double)(end-start)/CLOCKS_PER_SEC);
//    start = clock();
    FILE *f2= fopen("/projects/student/result.txt", "w");
//    FILE *f2= fopen("/Users/ying/Desktop/初赛/HWcode2020-TestData-master/testData/V1_result.txt", "w");
    int total =g->resSize[0]+g->resSize[1]+g->resSize[2]+g->resSize[3]+g->resSize[4];
    fprintf(f2, "%d\n",total);
        for(int i = 0;i<5;i++){
            for(int j = 0;j<g->resSize[i];j++){
                if(i==0){
                    fprintf(f2, "%d,%d,%d\n",g->res[i][j][0],g->res[i][j][1],g->res[i][j][2]);
                }
                if(i==1){
                    fprintf(f2, "%d,%d,%d,%d\n",g->res[i][j][0],g->res[i][j][1],g->res[i][j][2],g->res[i][j][3]);
                }
                if(i==2){
                    fprintf(f2, "%d,%d,%d,%d,%d\n",g->res[i][j][0],g->res[i][j][1],g->res[i][j][2],g->res[i][j][3],g->res[i][j][4]);
                }
                if(i==3){
                    fprintf(f2, "%d,%d,%d,%d,%d,%d\n",g->res[i][j][0],g->res[i][j][1],g->res[i][j][2],g->res[i][j][3],g->res[i][j][4],g->res[i][j][5]);
                }
                if(i==4){
                    fprintf(f2, "%d,%d,%d,%d,%d,%d,%d\n",g->res[i][j][0],g->res[i][j][1],g->res[i][j][2],g->res[i][j][3],g->res[i][j][4],g->res[i][j][5],g->res[i][j][6]);
                }
            }
        }
    fclose(f2);
//    end = clock();
//    printf("time=%f\n",(double)(end-start)/CLOCKS_PER_SEC);
    g->gfree(&g);
    return 0;
}
