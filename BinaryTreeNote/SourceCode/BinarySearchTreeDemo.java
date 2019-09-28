package com.xd.BinarySearchTree;

import com.xd.BinarySearchTree.printer.BinaryTrees;

import java.util.Comparator;

public class BinarySearchTreeDemo {

    //比较方式1
    private static class PersonComparator implements Comparator<Person> {
        @Override
        public int compare(Person e1, Person e2) {
            return e1.getAge() - e2.getAge();
        }
    }

    //比较方式2
    private static class PersonComparator2 implements Comparator<Person> {
        @Override
        public int compare(Person e1, Person e2) {
            return  e2.getAge() - e1.getAge();
        }
    }


    public static void test() {
        Integer data[] = new Integer[]{
                7,4,9,2,5,8,11,3,12,1
        };
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        for (int i = 0; i < data.length; i++) {
            bst.add(data[i]);
        }
        BinaryTrees.println(bst);
    }

    public static void test1() {
        BinarySearchTree<Person> bst = new BinarySearchTree<>();
        Integer data[] = new Integer[]{
                7,4,9,2,5,8,11,3,12,1
        };
        for (int i = 0; i < data.length; i++) {
            bst.add(new Person(data[i]));
        }
        BinaryTrees.println(bst);
    }

    public static void test2() {
        BinarySearchTree<Person> bst = new BinarySearchTree<>(new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                return o2.getAge() - o1.getAge();
            }
        });
        Integer data[] = new Integer[]{
                7,4,9,2,5,8,11,3,12,1
        };
        for (int i = 0; i < data.length; i++) {
            bst.add(new Person(data[i]));
        }
        BinaryTrees.println(bst);
    }


    /*
    * 二叉树的遍历
    * */
    public static void test6() {
        Integer data[] = new Integer[]{
                7,4,9,2,5,8,11,3,12,1
        };
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        for (int i = 0; i < data.length; i++) {
            bst.add(data[i]);
        }
       // BinaryTrees.println(bst);
        //bst.preorderTraversal();
        //bst.inorderTraversal();
        //bst.postorderTraversal();
        //bst.levelorderTraversal();
//        bst.levelOrder(new BinarySearchTree.Visitor<Integer>() {
//            @Override
//            public void visit(Integer element) {
//                System.out.print("_" + element + "_ ");
//            }
//        });

        System.out.println(bst);
        System.out.println(bst.height1());
    }
    public static void main(String[] args) {
        //test();
//        test1();
        test6();
    }
}
