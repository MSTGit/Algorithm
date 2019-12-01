package com.xd.Sort.SortClass;

public class MergeSort<E  extends Comparable<E>> extends Sort<E> {
    private E[] leftArray;
    @Override
    protected void sort() {
        leftArray = (E[]) new Comparable[array.length >> 1];
        divide(0,array.length);
    }

    /*
    *   对[begin,end)范围内的数据进行归并排序
    * */
    private void divide(int begin, int end) {
        if (end - begin < 2) return;
        int mid = (begin + end) >> 1;
        divide(begin,mid);
        divide(mid,end);
        merge(begin,mid,end);
    }

    /*
    * 将[begin,mid)和[mid,end）范围的序列进行合并成一个有序序列
    * */
    private void merge(int begin, int mid, int end){
        int li = 0, le = mid - begin;
        int ri = mid, re = end;
        int ai = begin;
        //现将左边的数据备份
        for (int i = li; i < le; i++) {
            leftArray[i] = array[begin +i];
        }

        //merge操作
        while (li < le) {
            //左边还没结束
            if (ri < re && cmp(array[ri],leftArray[li]) < 0) {
                array[ai++] = array[ri++];
            } else {
                array[ai++] = leftArray[li++];
            }
        }
    }
}
