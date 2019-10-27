package com.xd.Set.List;

/*
* 双向链表
* */
public class LinkedList<E> extends AbstractList<E> {
    /*
    * 指向头节点
    * */
    private Node first;
    /*
    * 指向尾节点
    * */
    private Node last;
    @Override
    public void clear() {
        size = 0;
        first = null;
        last = null;
    }


    @Override
    public E get(int index) {
        return node(index).element;
    }

    @Override
    public E set(int index, E element) {
        Node<E> node = node(index);
        E old = node.element;
        node.element = element;
        return old;
    }

    @Override
    public void add(int index, E element) {
        rangeCheckForAdd(index);

        if (index == size) {
            Node<E> oldLast = last;
            last = new Node<>(oldLast,element,null);
            if (oldLast == null) {
                first = last;
            } else  {
                oldLast.next = last;
            }
        } else  {
            Node<E> next = node(index);
            Node<E> prev = next.prev;
            Node<E> node = new Node<>(prev,element,next);
            next.prev = node;
            if (prev == null) {
                first = node;
            } else  {
                prev.next = node;
            }
        }
        size++;
    }

    @Override
    public E remove(int index) {
        rangeCheck(index);
        Node<E> node = node(index);
        Node<E> prev = node.prev;
        Node<E> next = node.next;
        if (prev == null) {//等价于index == 0
            first = next;
        } else  {
            prev.next = next;
        }
        if (next == null) {//等价于index == size - 1
            last = prev;
        } else  {
            next.prev = prev;
        }
        size--;
        return node.element;
    }

    @Override
    public int indexOf(E element) {
        Node<E> node = first;
        if (element == null) {
            for (int i = 0; i < size; i++) {
                if (node.element == null) return i;
                node = node.next;
            }
        } else  {
            for (int i = 0; i < size; i++) {
                if (element.equals(node.element)) return i;
                node = node.next;
            }
        }
        return ELEMENT_NOT_FOUND;
    }

    //创建一个私有方法，用来通过索引获取对应的节点
    private Node<E> node(int index) {
        rangeCheck(index);
        if (index < (size >> 1)) {
            //在左边
            Node<E> node = first;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
            return node;
        } else {
            //在右边
            Node<E> node = last;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
            return node;
        }


    }

    //申明一个内部类
    private static class Node<E> {
        E element;
        /*
        * 指向下一个节点
        * */
        Node <E> next;
        /*
        * 指向上一个节点
        * */
        Node <E> prev;
        public Node(Node <E> prev, E element, Node<E> next) {
            this.element = element;
            this.prev = prev;
            this.next = next;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            if (prev != null) {
                sb.append(prev.element);
            } else  {
                sb.append("null");
            }
            sb.append("_").append(element).append("_");
            if (next != null) {
                sb.append(next.element);
            } else {
                sb.append("null");
            }
            return sb.toString();
        }
    }

    /*
     * 自定义系统打印
     * */
    @Override
    public String toString() {
        //希望拼接的格式为 size = 9，[99,12,13]
        StringBuilder string = new StringBuilder();
        Node<E> node = first;
        string.append("size = ").append(size).append(", [");
        for (int i = 0; i < size; i++) {
            if (i != 0) {
                //推荐使用
                string.append(", ");
            }
            string.append(node);
            node = node.next;
            /*
            //这种方式不建议使用，因为会多一次减法运算
            if (i != size - 1) {
                string.append(", ");
            }
             */
        }
        string.append("]");
        return string.toString();
    }
}
