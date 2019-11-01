package com.xd.PriorityQueue;

import com.xd.PriorityQueue.Queue.PriorityQueue;

public class PriorityQueueDemo {
    public static void main(String[] args) {
        System.out.println("PriorityQueueDemo.main");
        PriorityQueue<Person> queue = new PriorityQueue<>();
        queue.enQueue(new Person("Jack",2));
        queue.enQueue(new Person("Rose",10));
        queue.enQueue(new Person("Jake",5));
        queue.enQueue(new Person("Hames",15));
        while (!queue.isEmpty()) {
            System.out.println(queue.deQueue());
        }
    }
}
