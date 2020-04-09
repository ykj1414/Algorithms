//
//  HashMap.h
//  HWCMT
//
//  Created by YING on 2020/4/4.
//  Copyright © 2020 YING. All rights reserved.
//

#ifndef HashMap_h
#define HashMap_h
#include <stdio.h>

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

#endif /* HashMap_h */
