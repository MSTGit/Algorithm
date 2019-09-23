package com.xd.Queue;

import com.xd.Queue.LinkedList.LinkedList;
import com.xd.Queue.LinkedList.List;

public class Queue<E> {

    private List<E> list = new LinkedList<>();
    public int size() {
        return list.size();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public void enQueue(E element) {
            list.add(element);
    }

    public E deQueue() {
        return list.remove(0);
    }

    public E front() {
        return list.get(0);
    }

    public void clear() {
        list.clear();
    }
}
