package com.xd.RBTree;

import com.xd.RBTree.printer.BinaryTrees;

public class RBTreeDemo {
    public static void main(String[] args) {
        System.out.println("RBTreeDemo.main");
        test();
    }

    static void test() {
        RBTree<Integer> rb = new RBTree<>();
        Integer data[] = new Integer[]{
                55,87,56,74,96,22,62,20,70,68,90,50
        };
        for (int i = 0; i < data.length; i++) {
            rb.add(data[i]);
        }
        BinaryTrees.println(rb);
    }
}
