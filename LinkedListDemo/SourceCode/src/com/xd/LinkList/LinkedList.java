package com.xd.LinkList;

public class LinkedList <E> extends AbstractList<E>{
    private Node first;

    @Override
    public void clear() {
        size = 0;
        first = null;
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
        System.out.println("index == " + index);
        if (index == 0) {
          first =  new Node<E>(element,first);
        } else  {
            Node<E> prev = node(index - 1);
            prev.next = new Node<>(element,prev.next);
        }

        size++;
    }

    @Override
    public E remove(int index) {
        Node<E> node = first;
        if (index == 0) {
            first = first.next;
        } else  {
            Node<E> prev = node(index - 1);
            node = prev.next;
            prev.next = node.next;
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
        Node<E> node = first;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node;
    }

    //申明一个内部类
    private static class Node<E> {
        E element;
        Node <E> next;
        public Node(E element, Node<E> next) {
            this.element = element;
            this.next = next;
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
            string.append(node.element);
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
