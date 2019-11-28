package com.xd.Sort;

import com.xd.Sort.SortClass.BubbleSort3;
import com.xd.Sort.SortClass.HeapSort;
import com.xd.Sort.SortClass.SelectionSort;
import com.xd.Sort.SortClass.Sort;
import com.xd.Sort.tools.Integers;

import java.util.Arrays;

public class SortDemo {
    public static void main(String[] args) {
        Integer[] array = Integers.random(10000,1,20000);
        testSorts(array, new HeapSort(),new SelectionSort(), new BubbleSort3());
    }

    static  void  testSorts(Integer[] array, Sort... sorts) {
        for (Sort sort: sorts  ) {
            sort.sort(Integers.copy(array));
        }
        Arrays.sort(sorts);
        for (Sort sort: sorts  ) {
            System.out.println(sort);
        }
    }
}
