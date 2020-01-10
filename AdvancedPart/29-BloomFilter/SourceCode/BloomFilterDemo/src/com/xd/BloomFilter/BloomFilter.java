package com.xd.BloomFilter;

public class BloomFilter<T> {

    /*
    * 二进制向量的长度（一共有多少个二进制位）
    * */
    private int bitSize;

    /*
    * 二进制向量
    * */
    private long[] bits;

    /*
    * 哈希函数的个数
    * */
    private int hashSize;

    /*
    * n为数据规模
    * p为误判率(0,1)
    * */
    public BloomFilter(int n,double p) {
        if (n <= 0 || p <= 0 || p >= 1){
            throw new IllegalArgumentException("wrong n or p");
        }
        double ln2 = Math.log(2);
        //计算二进制向量的长度
        bitSize = (int)(- (n * Math.log(p)) / (ln2 * ln2));
        //计算哈希函数的个数
        hashSize = (int)(bitSize * ln2 / n);
        //bits数组的长度
        bits = new long[(int)((bitSize + Long.SIZE - 1)) / Long.SIZE];
    }
    /*
    * 添加元素
    * */
    public void put(T value){
        nullCheck(value);
        //        value.hashCode() > hash1 > 索引1
        //        value.hashCode() > hash2 > 索引2
        //        value.hashCode() > hash3 > 索引3

        int hash1 = value.hashCode();
        int hash2 = hash1 >>> 16;
        for (int i = 1; i <= hashSize; i++) {
            int combinedHash = hash1 + (i * hash2);
            if (combinedHash < 0) {
                combinedHash = ~combinedHash;
            }
            //生成一个二进制位的索引
            int index = combinedHash % bitSize;
            //设置index位置的二进制位为1
            set(index);
        }
    }

    /*
    * 判断一个元素是否存在
    * */
    public boolean contains(T value) {
        nullCheck(value);
        int hash1 = value.hashCode();
        int hash2 = hash1 >>> 16;
        for (int i = 1; i <= hashSize; i++) {
            int combinedHash = hash1 + (i * hash2);
            if (combinedHash < 0) {
                combinedHash = ~combinedHash;
            }
            //生成一个二进制位的索引
            int index = combinedHash % bitSize;
            //获取index位置的二进制位的值是否为1
            if (!get(index)) return false;
        }
        return true;
    }

    /*
    * 查看index位置的值
    * true代表1，false代表0
    * */
    private boolean get(int index) {
        //计算在数组中的哪个long元素中
        int arrayIndex = index / Long.SIZE;
        int offsetIndex = index % Long.SIZE;
        long currentLong = bits[arrayIndex];

        //10001001
        //00001000
        return (currentLong & (1 << offsetIndex)) != 0;
    }

    /*
    * 设置index位的值为1
    * */
    private void set(int index) {
        //计算在数组中的哪个long元素中
        int arrayIndex = index / Long.SIZE;
        int offsetIndex = index % Long.SIZE;
        long currentLong = bits[arrayIndex];
        currentLong |= (1 << offsetIndex);
        bits[arrayIndex] = currentLong;
    }

    private void nullCheck(T value) {
        if (value == null) {
            throw new IllegalArgumentException("Value must not be null.");
        }
    }
}
