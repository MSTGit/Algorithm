package com.xd.Sort.SortClass;

import com.xd.Sort.SortClass.Cmp.Sort;

public class CountingSort extends Sort<Integer> {

    protected void sort0() {
        //找出最大值
        int max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }

        //开辟内存空间，存储每个整数出现的次数
        int[] counts = new int[max + 1];
        //统计每个整数出现的次数
        for (int i = 0; i < array.length; i++) {
            counts[array[i]]++;
        }
        //根据整数出现次数，对整数进行排序
        int index = 0;
        for (int i = 0; i < counts.length; i++) {
            if (counts[i] > 0) {
                while (counts[i]-- > 0) {
                    array[index++] = i;
                }
            }
        }
    }


    protected void sort1() {
        //找出最值
        int max = array[0];
        int min = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
            if (array[i] < min) {
                min = array[1];
            }
        }
        //开辟内存空间，存储次数
        int[] counts = new int[max - min + 1];
        //统计每个整数出现的次数
        for (int i = 0; i < array.length; i++) {
            counts[array[i] - min]++;
        }


        //累加次数
        for (int i = 1; i < counts.length; i++) {
            counts[i] += counts[i - 1];
        }

        //从后往前开始遍历元素，将它放到有序数组中的合适位置
        int[] tempArray = new int[array.length];
        for (int i = array.length - 1; i >= 0 ; i--) {
            tempArray[--counts[array[i] - min]] = array[i];
        }
        //将有序数组覆盖到array
        for (int i = 0; i < array.length; i++) {
            array[i] = tempArray[i];
        }
    }

    protected void sort() {
        // 找出最值
        int max = array[0];
        int min = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
            if (array[i] < min) {
                min = array[i];
            }
        }

        // 开辟内存空间，存储次数
        int[] counts = new int[max - min + 1];
        // 统计每个整数出现的次数
        for (int i = 0; i < array.length; i++) {
            counts[array[i] - min]++;
        }
        // 累加次数
        for (int i = 1; i < counts.length; i++) {
            counts[i] += counts[i - 1];
        }

        // 从后往前遍历元素，将它放到有序数组中的合适位置
        int[] newArray = new int[array.length];
        for (int i = array.length - 1; i >= 0; i--) {
            newArray[--counts[array[i] - min]] = array[i];
        }

        // 将有序数组赋值到array
        for (int i = 0; i < newArray.length; i++) {
            array[i] = newArray[i];
        }
    }
}
