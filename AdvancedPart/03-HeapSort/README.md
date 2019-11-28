#### 堆排序（Heap Sort）

结合上一讲的内容，发现选择排序可以使用堆排序来进行优化。所以堆排序可以认为是对选择排序的一种优化。因为利用堆来获取最大值时，发现与选择排序时做的事情差不多。

堆排序的执行流程如下

1. 对序列进行原地建堆（heapify）
2. 重复执行以下操作，直到堆的元素数量为1
   - 交换堆顶元素与尾元素
   - 堆的元素减1
   - 对0位置进行一次siftDown操作

假设现在得到的数据如下

![1574858265492](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/03-HeapSort/Resource/1574858265492.png)

将这些数据进行原地建堆后，得到的结果为

![1574858339942](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/03-HeapSort/Resource/1574858339942.png)

建完堆之后，就可以利用堆，直接获取堆中的最大值，从上图来讲的话，就是直接将0号位置与5号位置的元素进行交换就好了，这样就确定好了一个最大值，接下来将堆的size减1，这样就将已经排好序的元素排除在外。然后再修复最大堆的性质，将0号位置的元素执行siftDown操作。siftDown完成后的结果为

![1574858700231](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/03-HeapSort/Resource/1574858700231.png)

重复上面的操作，就可以将50进行归位

![1574858798823](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/03-HeapSort/Resource/1574858798823.png)

再次重复，将43进行归位

![1574858845902](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/03-HeapSort/Resource/1574858845902.png)

38归位

![1574858884873](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/03-HeapSort/Resource/1574858884873.png)

将21进行归位

![1574858940430](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/03-HeapSort/Resource/1574858940430.png)

到现在，堆中只剩下一个元素，就不再进行任何操作。

所以进行优化后的堆排序，其时间复杂度为O(nlogn)，从时间复杂度来看，是远远好于冒泡排序与选择排序的。

那么结合前面二叉堆的内容即实现的代码，所以这里可以快速的实现堆排序

```java
protected void sort() {
    heapSize = array.length;
    //原地建堆
    for (int i = ((heapSize >> 1) - 1); i >= 0; i--) {
        siftDown(i);
    }
    while (heapSize > 1) {
        //交换堆顶与堆尾部元素进行交换
        swap(0,--heapSize);
        //对0位置，进行siftDown
        siftDown(0);
    }
}

private void siftDown(int index) {
    Integer element = array[index];
    int half = heapSize >> 1;
    while (index < half){
        int childIndex = (index << 1) +1;
        Integer child = array[childIndex];
        int rightIndex = childIndex + 1;
        if (rightIndex < heapSize && cmpElements(array[rightIndex],child) > 0) {
            child = array[childIndex = rightIndex];
        }
        if (cmpElements(element,child) > 0) break;
        array[index] = child;
        index = childIndex;
    }
    array[index] = element;
}
```

通过这种方式实现以后，可以结合前面介绍的两种排序算法，进行一个比较。从消耗的时间来看，对10000个元素进行排序，得到的结果是

![1574862214891](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/03-HeapSort/Resource/1574862214891.png)

从这里的结果，可以看到，堆排序的性能是高于选择排序与冒泡排序的。不过，有时候只看消耗的时间还不是完全准确，所以现在也可以从元素的比较次数，交换次数来进行对比，我通过封装以后，同样对10000个元素进行排序，得到三种不同排序算法的最终结果为

![1574863265037](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/03-HeapSort/Resource/1574863265037.png)

从比较结果可以看到，SelectionSort的交换次数是远远小于BubbleSort3的，所以SelectionSort时间比BubbleSort3快，然后HeapSort的比较次数更少，所以效率比SelectionSort效率更高

总结：

1. 堆排序的最好，最坏，平均时间复杂度：O(nlogn)
2. 空间复杂度为O(1)
3. 属于不稳定的排序

