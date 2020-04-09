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
#include <pthread.h>
#define MAX_V 280000
#define MAX_C 25
#include "HashMap.h"
#include "Graph.h"
#define Put(map, key, value) map->put(map, (void *)key, (void *)value);
#define Get(map, key) (char *)map->get(map, (void *)key)
#define Remove(map, key) map->remove(map, (void *)key)
#define Existe(map, key) map->exists(map, (void *)key)
#define HashCode(map,key) map->hashCode(map,(void*)key)
#define freeHashList(map) map->freeList(map)
#define append(List,val) List->append(List,val)

int cmp(const void *a,const void *b){
    return *(int*)a-*(int*)b;
}


typedef struct args{
    int num;
    int start;
    int end;
    Graph g;
    int ***res;
    int *resSize;
}Args;

void* threadFind(void*arg){
    Args *args = (Args*)arg;
    int start = args->start;
    int end = args->end;
    Graph g = args->g;
    for(int i = 0;i<5;i++) args->res[i] = (int**)malloc(sizeof(int*)*200000);
    int ***res = args->res;
    int *resSize = args->resSize;
    int *r = (int*)malloc(sizeof(int)*7);
    for(int i = start;i<end;i++){
        r[0] = g->lvp->vp[i];
        g->findCycle(g,r[0],r,1,res,resSize);
    }
    free(r);
    return NULL;
}

int main(int argc, const char * argv[]) {
    // insert code here...
    clock_t start,end;
    start = clock();
    int from=0,to=0,money= 0;
    Graph g = createGraph();
    FILE *f1 = fopen("/Users/ying/PycharmProjects/Python/Data/test_data_10000_60000.txt", "r");
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
    //总共开5个线程
    pthread_t *t = (pthread_t*)malloc(sizeof(pthread_t)*5);
    int index = g->lvp->size/5;
    //每个线程都有自己的结果池，总共5个结果池
    int ****res = (int****)malloc(sizeof(int***)*5);
    int **resSize = (int**)malloc(sizeof(int*)*5);
    for (int i = 0; i<5; i++) {
        resSize[i] = (int*)calloc(5, sizeof(int));
    }
    for (int i = 0;i<5;i++){
        int start = i*index;
        int end = i==4?g->lvp->size:(i+1)*index;
        res[i] = (int***)malloc(sizeof(int**)*5);
        resSize[i] = (int*)calloc(5, sizeof(int));
        Args arg = {i,start,end,g,res[i],resSize[i]};
        pthread_create(&t[i], NULL, threadFind, (void*)&arg);
        pthread_join(t[i],NULL);
    }
    free(t);
    int total = 0;
    for (int i = 0; i<5; i++) {
        for(int j = 0;j<5;j++) total+=resSize[i][j];
    }
    end = clock();
    printf("time=%f\n",(double)(end-start)/CLOCKS_PER_SEC);
    start = clock();
    FILE *f2= fopen("/Users/ying/PycharmProjects/Python/Data/result_c50000.txt", "w");
    fprintf(f2, "%d\n",total);
    for(int i = 0;i<5;i++){
        for(int n = 0;n<5;n++){
            int **rr = res[n][i];
            for(int j = 0;j<resSize[n][i];j++){
                int *rrr = rr[j];
                if(i==0){
                    fprintf(f2, "%d,%d,%d\n",rrr[0],rrr[1],rrr[2]);
                }
                else if(i==1){
                    fprintf(f2, "%d,%d,%d,%d\n",rrr[0],rrr[1],rrr[2],rrr[3]);
                }
                else if(i==2){
                    fprintf(f2, "%d,%d,%d,%d,%d\n",rrr[0],rrr[1],rrr[2],rrr[3],rrr[4]);
                }
                else if(i==3){
                    fprintf(f2, "%d,%d,%d,%d,%d,%d\n",rrr[0],rrr[1],rrr[2],rrr[3],rrr[4],rrr[5]);
                }
                else if(i==4){
                    fprintf(f2, "%d,%d,%d,%d,%d,%d,%d\n",rrr[0],rrr[1],rrr[2],rrr[3],rrr[4],rrr[5],rrr[6]);
                }
                free(rrr);
            }
            free(rr);
        }
    }
    for(int n = 0;n<5;n++) free(res[n]);
    free(res);
    free(resSize);
    fclose(f2);
    end = clock();
    printf("time=%f\n",(double)(end-start)/CLOCKS_PER_SEC);
    g->gfree(&g);
    return 0;
}
