package com.xd.LinkList;

public class LinkedListDemo {
    public static void main(String[] args) {
        System.out.println("LinkedListDemo.main");
        List<Integer> list = new LinkedList<>();
        list.add(20);
        list.add(0,10);
        list.add(30);
        list.add(list.size(),40);
        list.remove(1);
        System.out.println(list);
    }
}
