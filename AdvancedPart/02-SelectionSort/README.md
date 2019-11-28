#### 选择排序（Selection Sort）

选择排序，相对于前面的冒泡排序，个人认为更加简单。其执行流程为

1. 从序列中找出最大的那个元素，然后与最末尾的元素交换位置
   - 执行完一轮后，最末尾的那个元素就是最大的元素
2. 忽略1中曾经找到的最大元素，重复执行步骤1

所以转换为代码的话，可以这样来实现

```java
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
```

从元素交换的角度来看，选择排序的交换次数要远远少于冒泡排序，所以平均性能优于冒泡排序。并有以下结论

> 1. 最好，最坏，平均时间复杂度均为：O(n^2)
> 2. 空间复杂度为：O(1)
> 3. 属于不稳定排序

但是相对于冒泡排序，选择排序没有最好情况，因为在冒泡排序时，如果元素提前有序的话，则可以提前结束排序。

那么，选择排序是否有优化的空间呢？

是可以的，结合前面二叉堆部分的介绍，可以知道，在从一堆数组中选择最大值，可以利用堆来完成。所以在下一讲，将介绍堆排序。