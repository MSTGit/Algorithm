package com.xd.Queue;

import com.xd.Queue.Circle.CircleDeque;
import com.xd.Queue.Circle.CircleQueue;
import sun.plugin2.jvm.CircularByteBuffer;

public class QueueDemo {
    public static void main(String[] args) {
        dequeTest();
//        circleQueueTest();
//        circleDequeTest();
    }

    public static void circleDequeTest() {
        CircleDeque<Integer> queue = new CircleDeque<>();

        //8,7,6,5,4,3,2,1,100,101,102,103,104,105,106,107,108,109,null,null,10,9
        for (int i = 0; i < 10; i++) {
            queue.enQueueFront(i +1);
            queue.enQueueRear(i + 100);
        }

        System.out.println(queue);
        for (int i = 0; i < 3; i++) {
            queue.deQueueFront();
            queue.deQueueRear();
        }
        queue.enQueueFront(11);
        queue.enQueueFront(22);//11,7,6,5,4,3,2,1,100,101,102,103,104,105,106,null,null,null,null,null,null,22

        System.out.println(queue);
        while (!queue.isEmpty()){
            System.out.println(queue.deQueueFront());
        }
    }
    public static void circleQueueTest() {
        CircleQueue<Integer> queue = new CircleQueue<>();
        // 0 1 2 3 4 5 6 7 8 9
        for (int i = 0; i < 10; i++) {
            queue.enQueue(i);
        }
        //null null null null null 5 6 7 8 9
        for (int i = 0; i < 5; i++) {
            queue.deQueue();
        }
        //15 16 17 18 19 5 6 7 8 9
        for (int i = 15; i < 23; i++) {
            queue.enQueue(i);
        }
        System.out.println(queue);
        while (!queue.isEmpty()){
            System.out.println(queue.deQueue());
        }
    }

    public static void dequeTest() {
        Deque<Integer> queue = new Deque();
        queue.enQueueFront(11);
        queue.enQueueFront(22);
        queue.enQueueRear(33);
        queue.enQueueRear(44);
        /*头 44 33 11 22 尾*/
        while (!queue.isEmpty()) {
            System.out.println(queue.deQueueRear());
        }
    }
    public static void queueTest() {
        Queue<Integer> queue = new Queue();
        queue.enQueue(11);
        queue.enQueue(22);
        queue.enQueue(33);
        queue.enQueue(44);
        queue.enQueue(55);
        while (!queue.isEmpty()) {
            System.out.println(queue.deQueue());
        }
    }
}
