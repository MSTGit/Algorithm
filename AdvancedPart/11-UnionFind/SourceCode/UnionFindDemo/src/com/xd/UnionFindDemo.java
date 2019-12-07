package com.xd;

import com.xd.tools.Asserts;
import com.xd.tools.Times;
import com.xd.union.*;

public class UnionFindDemo {
    static final int  count = 1000000;
    public static void main(String[] args) {
        System.out.println("UnionFindDemo.main");

//        test(new UnionFind_QuickFind(12));
//        test(new UnionFind_QuickUnion(12));
//        test(new UnionFind_QuickUnion_Size(12));
//        test(new UnionFind_QuickUnion_Rank(12));
//        test(new UnionFind_QuickUnion_Rank_PathCompression(12));

//        timeTest(new UnionFind_QuickFind(count));
//        timeTest(new UnionFind_QuickUnion(count));
        timeTest(new UnionFind_QuickUnion_Size(count));
        timeTest(new UnionFind_QuickUnion_Rank(count));
        timeTest(new UnionFind_QuickUnion_Rank_PathCompression(count));
        timeTest(new UnionFind_QuickUnion_Rank_PathSpliting(count));
        timeTest(new UnionFind_QuickUnion_Rank_PathHalving(count));
    }

    static void test(UnionFind qf) {
        qf.union(0,1);
        qf.union(0,3);
        qf.union(0,4);
        qf.union(2,3);
        qf.union(2,5);

        qf.union(6,7);

        qf.union(8,10);
        qf.union(9,10);
        qf.union(9,11);

        Asserts.test(!qf.isSame(0,6));
        Asserts.test(qf.isSame(0,5));

        qf.union(4,6);

        Asserts.test(qf.isSame(2,7));
    }

    static void timeTest(UnionFind uf) {
        uf.union(0,1);
        uf.union(0,3);
        uf.union(0,4);
        uf.union(2,3);
        uf.union(2,5);
        uf.union(6,7);
        uf.union(8,10);
        uf.union(9,10);
        uf.union(9,11);
        Asserts.test(!uf.isSame(0,6));
        Asserts.test(uf.isSame(0,5));
        uf.union(4,6);
        Asserts.test(uf.isSame(2,7));

        Times.test(uf.getClass().getSimpleName(),() -> {
            for (int i = 0; i < count; i++) {
                uf.union((int)(Math.random() * count),(int)(Math.random() * count));
            }

            for (int i = 0; i < count; i++) {
                uf.isSame((int)(Math.random() * count),(int)(Math.random() * count));
            }
        });
    }
}
