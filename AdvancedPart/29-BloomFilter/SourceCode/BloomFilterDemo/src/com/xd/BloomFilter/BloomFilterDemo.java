package com.xd.BloomFilter;

public class BloomFilterDemo {
    public static void main(String[] args) {
        BloomFilter<Integer> bf = new BloomFilter<>(1_0000_0000,0.01);
        for (int i = 1; i <= 1_00_0000; i++) {
            bf.put(i);
        }
        int count = 0;
        for (int i = 1_00_0001; i <= 2_00_0000; i++) {
            if (bf.contains(i)) {
                count++;
            }
        }
        System.out.println(count);
    }
}
