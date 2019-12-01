package com.xd.Sort.SortClass;

public class InsertionSort3<E  extends Comparable<E>> extends Sort<E> {
//        protected void sort() {
//             for (int begin = 1; begin < array.length; begin++) {
//                int insertIndex = search(begin);
//                E v = array[begin];
//                 for (int i = begin; i > insertIndex; i--) {
//                     array[i] = array[i - 1];
//                 }
//                 array[insertIndex] = v;
//             }
//        }

    protected void sort() {
        for (int begin = 1; begin < array.length; begin++) {
            int insertIndex = search(begin);
            insert(begin,search(begin));
        }
    }

    /*
    * 将source位置的元素，插入到dest位置
    * */
    private void insert(int source, int dest) {
        E v = array[source];
        for (int i = source; i > dest; i--) {
            array[i] = array[i - 1];
        }
        array[dest] = v;

    }
    
        /*
        * 利用二分搜索找到index 位置元素的待插入位置
        * 已经排好序数组的区间范围是[0,index)
        * index为待插入元素的索引
        * */
        private int search(int index) {
            if (array == null || array.length == 0) return -1;
            int begin = 0;
            int end = index;
            while (begin < end) {
                int mid = (begin + end) >> 1;
                if (cmp(array[index],array[mid]) < 0) {
                    end = mid;
                } else {
                    begin = mid + 1;
                }
            }
            return begin;
        }
}
