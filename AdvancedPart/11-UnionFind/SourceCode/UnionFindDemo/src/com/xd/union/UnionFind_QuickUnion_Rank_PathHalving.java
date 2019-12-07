package com.xd.union;

public class UnionFind_QuickUnion_Rank_PathHalving extends UnionFind_QuickUnion_Rank {
    private int[] ranks;
    public UnionFind_QuickUnion_Rank_PathHalving(int capacity) {
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
            //然后让当前节点指向祖父节点
            parents[v] = parents[parents[v]];
            //parents[v]为祖父节点，祖父节点重复执行操作
            v = parents[v];
        }
        return v;
    }
}
