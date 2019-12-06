#### 桶排序（Bucket Sort）

前面介绍了9种不同的排序算法，那现在就直接来看以下桶排序的执行流程

1. 创建一定数量的桶（比如用数组，链表作为桶）
2. 按照一定的规则（不同类型的数据，规则不同），将序列中的元素均匀分配到对应的桶
3. 分别对每个桶进行单独排序
4. 将所有非空桶的元素合并成有序序列

例如现在需要对下图中的小数进行排序

![1575463515502](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/10-BucketSort/Resource/1575463515502.png)

由于现在有8个元素，就可以创建8个桶，每个桶都是一个数组，然后利用元素值 * 待排序元素数量 得到每一个元素的索引

![1575463775791](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/10-BucketSort/Resource/1575463775791.png)

然后再对每个桶进行单独排序，最终每个桶排序后的结果为

![1575463817391](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/10-BucketSort/Resource/1575463817391.png)

然后再将非空桶中的元素进行合并，最终合并后的结果为

![1575463872016](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/10-BucketSort/Resource/1575463872016.png)

所以，基于小数的桶排序算法如下

```java
protected void sort() {
    List<Double>[] buckets = new List[array.length];
    for (int i = 0; i < array.length; i++) {
        int bucketIndex = (int)(array[i] * array.length);
        List<Double> bucket = buckets[bucketIndex];
        if (bucket == null) {
            bucket = new LinkedList<>();
            buckets[bucketIndex] = bucket;
        }
        bucket.add(array[i]);
    }

    int index = 0;
    for (int i = 0; i < buckets.length; i++) {
        if (buckets[i] == null) continue;
        buckets[i].sort(null);
        for (Double d :
                buckets[i]) {
            array[index++] = d;
        }
    }
    System.out.println(array);
}
```

需要注意，不同的数据类型，桶排序的实现是不一样的，所以无法给出统一的算法。可以作为一种思路来进行了解。

