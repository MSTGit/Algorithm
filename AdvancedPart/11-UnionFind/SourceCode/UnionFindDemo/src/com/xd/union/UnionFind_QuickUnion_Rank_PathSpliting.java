package com.xd.union;

public class UnionFind_QuickUnion_Rank_PathSpliting extends UnionFind_QuickUnion_Rank {
    private int[] ranks;
    public UnionFind_QuickUnion_Rank_PathSpliting(int capacity) {
        super(capacity);
        ranks = new int[capacity];
        for (int i = 0; i < ranks.length; i++) {
            ranks[i] = 1;
        }
    }

    @Override
    public int find(int v) {
        rangeCheck(v);
        while (v != parents[v]) {
            //将当前节点的父节点保存下来
            int p = parents[v];
            //然后让当前节点指向祖父节点
            parents[v] = parents[parents[v]];
            //更新节点,重复执行操作
            v = p;
        }
        return v;
    }
}
