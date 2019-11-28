package com.xd.SelectionSort;

import com.xd.SelectionSort.tools.Integers;
import com.xd.SelectionSort.tools.Times;

public class SelectionSortDemo {
    public static void main(String[] args) {
        System.out.println("SelectionSortDemo.main");
        Integer[] array = Integers.random(10,1,100);
        selectionSort(array);
        Integers.println(array);
    }
    public static void selectionSort(Integer[] array) {
        for (int end = array.length - 1; end > 0; end--) {
            int maxIndex = 0;
            for (int begin = 1; begin <= end ; begin++) {
                    if (array[maxIndex] <= array[begin]) {
                        maxIndex = begin;
                    }
            }
            int tmp = array[maxIndex];
            array[maxIndex] = array[end];
            array[end] = tmp;
        }
    }
}
