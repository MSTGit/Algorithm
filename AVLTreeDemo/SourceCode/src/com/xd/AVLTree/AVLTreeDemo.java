package com.xd.AVLTree;

import com.xd.AVLTree.printer.BinaryTrees;

public class AVLTreeDemo {
    public static void test1() {
        AVLTree<Integer> avl = new AVLTree<>();
        Integer data[] = new Integer[]{
                7,4,9,2,5,8,11,3,12,1
        };
        for (int i = 0; i < data.length; i++) {
            avl.add(data[i]);
        }
        BinaryTrees.println(avl);
    }
    public static void main(String[] args) {
        System.out.println("BinaryTreeDemo.main");
        test1();
    }
}
