package com.xd.union;
/*
* 基于rank的优化
* */
public class UnionFind_QuickUnion_Rank  extends UnionFind_QuickUnion {
    private int[] ranks;
    public UnionFind_QuickUnion_Rank(int capacity) {
        super(capacity);
        ranks = new int[capacity];
        for (int i = 0; i < ranks.length; i++) {
            ranks[i] = 1;
        }
    }

    @Override
    public void union(int v1, int v2) {
        int p1 = find(v1);
        int p2 = find(v2);
        if (p1 == p2) return;
        //rank值小的，嫁接到rank值大的树上
        if (ranks[p1] > ranks[p2]) {
            parents[p2] = p1; //将矮的树，嫁接到高的树上
            //由于是矮的树嫁接到高的树上，所以不需要更新树高
        } else if (ranks[p1] < ranks[p2]){
            parents[p1] = p2;
        } else {
            //树高相等，进行合并，所以树高会增加1
            parents[p1] = p2;
            ranks[p2] += 1;
        }
    }
}
