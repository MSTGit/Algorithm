package com.xd.BinarySearchTree.Refactor;

import com.xd.BinarySearchTree.printer.BinaryTrees;

public class BinaryTreeDemo {
    public static void test1() {
        com.xd.BinarySearchTree.Refactor.BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        Integer data[] = new Integer[]{
                7,4,9,2,5,8,11,3,12,1
        };
        for (int i = 0; i < data.length; i++) {
            bst.add(data[i]);
        }
        BinaryTrees.println(bst);
    }
    public static void main(String[] args) {
        System.out.println("BinaryTreeDemo.main");
        test1();
    }
}
