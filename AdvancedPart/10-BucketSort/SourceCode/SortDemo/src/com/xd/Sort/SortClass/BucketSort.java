package com.xd.Sort.SortClass;

import com.xd.Sort.SortClass.Cmp.Sort;

import java.util.LinkedList;
import java.util.List;

public class BucketSort extends Sort<Double> {
    @Override
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
}
