package com.xd.Sort.SortClass;

import com.xd.Sort.SortClass.Cmp.Sort;

public class RadixSort extends Sort<Integer> {
    @Override
    protected void sort() {
        //找出最大值
        int max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }
        for (int devider = 1; devider <= max ; devider *= 10) {
            countingSort(devider);
        }
    }

    protected void countingSort(int devider) {
        // 开辟内存空间，存储次数
        int[] counts = new int[10];
        // 统计每个整数出现的次数
        for (int i = 0; i < array.length; i++) {
            counts[array[i] / devider % 10]++;
        }
        // 累加次数
        for (int i = 1; i < counts.length; i++) {
            counts[i] += counts[i - 1];
        }

        // 从后往前遍历元素，将它放到有序数组中的合适位置
        int[] newArray = new int[array.length];
        for (int i = array.length - 1; i >= 0; i--) {
            newArray[--counts[array[i] / devider % 10]] = array[i];
        }

        // 将有序数组赋值到array
        for (int i = 0; i < newArray.length; i++) {
            array[i] = newArray[i];
        }
    }
}
