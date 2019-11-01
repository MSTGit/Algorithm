package com.xd.PriorityQueue.Queue;

import com.xd.PriorityQueue.Heap.BinaryHeap;

import java.util.Comparator;

public class PriorityQueue<E> {
    private BinaryHeap<E> heap;
    public PriorityQueue(Comparator<E> comparator) {
        heap = new BinaryHeap<>(comparator);
    }
    public PriorityQueue() {
        this(null);
    }
    public int size() {
        return heap.size();
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }

    public void enQueue(E element) {
        heap.add(element);
    }

    public E deQueue() {
        return heap.remove();
    }

    public E front() {
        return heap.get();
    }

    public void clear() {
        heap.clear();
    }
}
