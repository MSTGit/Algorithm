package com.xd.union;

public class UnionFind_QuickUnion_Rank_PathCompression  extends UnionFind_QuickUnion_Rank {
    private int[] ranks;
    public UnionFind_QuickUnion_Rank_PathCompression(int capacity) {
        super(capacity);
        ranks = new int[capacity];
        for (int i = 0; i < ranks.length; i++) {
            ranks[i] = 1;
        }
    }

    @Override
    public int find(int v) {
        rangeCheck(v);
        if (v != parents[v]) {
            //修改v的父节点，通过递归调用，最终找到根节点，将v的父节点指向根节点
            //顺便将整条路径节点的父节点都修改为根节点
            parents[v] = find(parents[v]);
        }
        return parents[v];
    }
}
