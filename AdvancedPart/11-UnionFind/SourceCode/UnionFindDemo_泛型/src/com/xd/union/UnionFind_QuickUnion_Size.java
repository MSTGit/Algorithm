package com.xd.union;

/*
* 基于size的优化
* */
public class UnionFind_QuickUnion_Size  extends UnionFind_QuickUnion {
    private int[] sizes;
    public UnionFind_QuickUnion_Size(int capacity) {
        super(capacity);
        sizes = new int[capacity];
        for (int i = 0; i < sizes.length; i++) {
            sizes[i] = 1;
        }
    }

    @Override
    public void union(int v1, int v2) {
        int p1 = find(v1);
        int p2 = find(v2);
        if (p1 == p2) return;
        //size少的，嫁接到size多的上
        if (sizes[p1] > sizes[p2]) {
            parents[p2] = p1;//将p2嫁接到p1上
            sizes[p1] += sizes[p2];//更新size
        } else {
            parents[p1] = p2;
            sizes[p2] += sizes[p1];
        }
    }
}
