package com.xd.Heap.Heap;

import com.xd.Heap.printer.BinaryTreeInfo;

import java.util.Comparator;
/*
* 二叉堆（最大堆）
* */
public class BinaryHeap<E> extends AbstractHeap<E> implements BinaryTreeInfo {
    private E[] elements;
    private static final int DEFAULT_CAPACITY = 10;

    public BinaryHeap(E[] elements, Comparator<E> comparator) {
        super(comparator);
        if (elements == null || elements.length == 0) {
            this.elements = (E[])new Object[DEFAULT_CAPACITY];
        }else {
            size = elements.length;
            int capacity = Math.max(elements.length,DEFAULT_CAPACITY);
            this.elements = (E[])new Object[capacity];
            for (int i = 0; i < elements.length; i++) {
                this.elements[i] = elements[i];
            }
            heapify();
        }
    }

    public BinaryHeap(E[] elements) {
        this(elements,null);
    }
    public BinaryHeap(Comparator<E> comparator){this(null,comparator);
    }
    public BinaryHeap(){
       this(null,null);
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
    }

    @Override
    public void add(E element) {
        elementNotNullCheck(element);
        ensureCapacity(size +1);
        elements[size++] = element;
        siftUp(size - 1);
    }

    @Override
    public E get() {
        emptyCheck();
        return elements[0];
    }

    @Override
    public E remove() {
        emptyCheck();
        E root = elements[0];
        int lastIndex = --size;
        elements[0] = elements[lastIndex];
        elements[lastIndex] = null;
        siftDown(0);
        return root;
    }

    @Override
    public E replace(E element) {
        elementNotNullCheck(element);
        E root = null;
        if (size == 0) {
            elements[0] = element;
            size++;
        } else {
            root = elements[0];
            elements[0] = element;
            siftDown(0);
        }

        return root;
    }

    /*
    * 批量建堆
    * */
    private void heapify() {
        //自上而下的上滤
//        for (int i = 0; i < size; i++) {
//            siftUp(i);
//        }
        //自下而上的上滤
        for (int i = ((size >> 1) - 1); i >= 0; i--) {
            siftDown(i);
        }
    }

    /*
    * 下滤操作
    * */
    private void siftDown(int index) {
        E element = elements[index];
        //必须保证index的位置是非叶子节点，即 index < 第一个非叶子节点的索引
        //第一个叶子节点的索引，就是非叶子节点是数量
        int half = size >> 1;//half为非叶子节点的数量
        while (index < half){
            //index的节点有2中情况
            //1.只有左子节点
            //2.同时有左有右
            int childIndex = (index << 1) +1;//默认为左子节点的索引
            E child = elements[childIndex];
            //右子节点
            int rightIndex = childIndex + 1;
            //选出左右子节点最大的那个
            if (rightIndex < size && compare(elements[rightIndex],child) > 0) {
                child = elements[childIndex = rightIndex];
            }
            if (compare(element,child) > 0) break;
            //将子节点存放到index的位置
            elements[index] = child;
            //重新设置index
            index = childIndex;
        }
        elements[index] = element;
    }
    /*
    * 让index位置的元素上滤
    * */
    private void siftUp(int index) {
        E element = elements[index];
        while (index > 0) {
            int parentIndex = (index - 1) >> 1;
            E parent = elements[parentIndex];
            if (compare(element,parent) <= 0) {
                break;
            }
            //将父元素存储到index位置
            elements[index] = parent;
            index = parentIndex;//更新当前节点的索引
        }
        elements[index] = element;
    }



    private void emptyCheck(){
        if (size == 0) {
            throw new IndexOutOfBoundsException("Heap is empty");
        }
    }

    private void elementNotNullCheck(E element){
        if (element == null) {
            throw new IllegalArgumentException("element must not be null");
        }
    }

    //数组扩容 保证要有capacity的容量
    private void ensureCapacity(int capacity) {
        int oldCapacity = elements.length;
        if (oldCapacity < capacity) {
            int newCapacity = oldCapacity + (oldCapacity >> 1);
            E[] newElements = (E[]) new Object[newCapacity];
            for (int i = 0; i < size; i++) {
                newElements[i] = elements[i];
            }
            elements = newElements;
        }
    }

    @Override
    public Object root() {
        return 0;
    }

    @Override
    public Object left(Object node) {
        Integer index = (Integer)node;
        index = (index <<1) +1;
        return index >= size ? null : index;
    }

    @Override
    public Object right(Object node) {
        Integer index = (Integer)node;
        index = (index <<1) +2;
        return index >= size ? null : index;
    }

    @Override
    public Object string(Object node) {

        Integer index = (Integer)node;
        return elements[index];
    }
}
