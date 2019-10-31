package com.xd.Heap;

import com.xd.Heap.Heap.BinaryHeap;
import com.xd.Heap.printer.BinaryTrees;

import java.util.Comparator;

public class HeapDemo {
    static void test1(){
        BinaryHeap<Integer> heap = new BinaryHeap<>();
        heap.add(68);
        heap.add(72);
        heap.add(43);
        heap.add(50);
        heap.add(38);
        heap.add(10);
        heap.add(90);
        heap.add(65);
        BinaryTrees.println(heap);
//        heap.remove();
//        BinaryTrees.println(heap);
        System.out.println(heap.replace(70));
        BinaryTrees.println(heap);
    }

    static void test2() {
        Integer[] data =  {88, 44, 53, 41, 16, 6, 70, 18, 85, 98, 81, 23, 36, 43, 37};
        BinaryHeap<Integer> heap = new BinaryHeap<>(data);
        BinaryTrees.println(heap);
    }

    static void test3() {
        Integer[] data =  {88, 44, 53, 41, 16, 6, 70, 18, 85, 98, 81, 23, 36, 43, 37};
        BinaryHeap<Integer> heap = new BinaryHeap<>(data, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        });
        BinaryTrees.println(heap);
    }

    static void test4(){
        //新建一个小顶堆
        BinaryHeap<Integer> heap = new BinaryHeap<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        });
        //最大的前K个数
        int K = 5;
        Integer[] data = {51, 30, 39, 92, 74, 25, 16, 93,
                91, 19, 54, 47, 73, 62, 76, 63, 35, 18,
                90, 6, 65, 49, 3, 26, 61, 21, 48};
        for (int i = 0; i < data.length; i++) {
            if (heap.size() < K){//前K个数添加到小顶堆
                heap.add(data[i]);
            } else if (data[i] > heap.get()) { //如果是第K + 1个数，并且大于堆顶元素
                heap.replace(data[i]);
            }
        }
        BinaryTrees.println(heap);
    }

    public static void main(String[] args) {
        System.out.println("HeapDemo.main");

        test4();
    }
}
