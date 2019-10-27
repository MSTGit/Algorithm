package com.xd.Stack;

public class StackDemo {
    public static void main(String[] args) {
        System.out.println("StackDemo.main");
        Stack<Integer> stack = new Stack<>();
        stack.push(11);
        stack.push(22);
        stack.push(33);
        stack.push(44);
        stack.push(55);
        stack.push(66);
        stack.push(77);
        while (!stack.isEmpty()){
            System.out.println(stack.pop());
        }
    }
}
