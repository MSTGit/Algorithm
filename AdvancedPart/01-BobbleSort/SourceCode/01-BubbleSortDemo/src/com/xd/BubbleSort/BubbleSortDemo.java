package com.xd.BubbleSort;

import com.xd.BubbleSort.tools.Integers;
import com.xd.BubbleSort.tools.Times;

public class BubbleSortDemo {
    public static void main(String[] args) {
        System.out.println("BubbleSortDemo.main");

        Integer[] array1 = Integers.tailAscOrder(1,10000,2000);
        Integer[] array2 = Integers.copy(array1);
        Integer[] array3 = Integers.copy(array1);
        Times.test("bubbleSort1",()->{
            bubbleSort1(array1);
        });

        Times.test("bubbleSort2",()->{
            bubbleSort2(array2);
        });

        Times.test("bubbleSort3",()->{
            bubbleSort3(array3);
        });

    }

    public static void bubbleSort1(Integer[] array) {
        for (int end = array.length - 1; end > 0; end--) {
            for (int begin = 1; begin <= end ; begin++) {
                if (array[begin] < array[begin - 1]) {
                    int tmp = array[begin];
                    array[begin] = array[begin - 1];
                    array[begin - 1] = tmp;
                }
            }
        }
    }
    public static void bubbleSort2(Integer[] array) {
        for (int end = array.length - 1; end > 0; end--) {
            boolean sorted = true;
            for (int begin = 1; begin <= end ; begin++) {
                if (array[begin] < array[begin - 1]) {
                    int tmp = array[begin];
                    array[begin] = array[begin - 1];
                    array[begin - 1] = tmp;
                    sorted = false;
                }
            }
            if (sorted == true) {
                break;
            }
        }
    }

    public static void bubbleSort3(Integer[] array) {
        for (int end = array.length - 1; end > 0; end--) {
            int sortedIndex = 1;//该值在数组完全女友许的时候有用
            for (int begin = 1; begin <= end ; begin++) {
                if (array[begin] < array[begin - 1]) {
                    int tmp = array[begin];
                    array[begin] = array[begin - 1];
                    array[begin - 1] = tmp;
                    sortedIndex = begin;
                }
            }
            end = sortedIndex;
        }
    }

    public static void bubbleSort4(Integer[] array) {
        for (int end = array.length - 1; end > 0; end--) {
            for (int begin = 1; begin <= end ; begin++) {
                if (array[begin] <= array[begin - 1]) {
                    int tmp = array[begin];
                    array[begin] = array[begin - 1];
                    array[begin - 1] = tmp;
                }
            }
        }
    }
}
