//
//  HashMap.c
//  HWCMT
//
//  Created by YING on 2020/4/4.
//  Copyright © 2020 YING. All rights reserved.
//

#include "HashMap.h"
#include <stdlib.h>

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
                if (current->node->val>node->val) {
                    node->next = hashMap->list[index]->node;
                    hashMap->list[index]->node = node;
                }
                else{
                    Node cur = current->node;
                    while(cur->next){
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
