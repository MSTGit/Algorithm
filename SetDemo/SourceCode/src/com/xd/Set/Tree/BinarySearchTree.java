package com.xd.Set.Tree;

import java.util.Comparator;

public class BinarySearchTree<E> extends BinaryTree<E> {

    private Comparator<E> comparator;

    public BinarySearchTree() {
        this(null);
    }
    public BinarySearchTree(Comparator<E> comparator) {
        this.comparator = comparator;
    }


    public void add(E element) {
        elementNotNullCheck(element);
        if (root == null) {
            //添加的第一个节点
            root = createNode(element,null);
            size++;
            //新添加节点之后的处理
            afterAdd(root);
            return;
        }
        //添加的不是第一个节点
        //找到父节点
        Node<E> node = root;
        Node<E> parent = root;
        int cmp = 0;
        while (node != null) {
            cmp = compare(element,node.element);
            parent = node;
            if (cmp > 0) {
                //右节点
                node = node.right;
            } else if (cmp < 0) {
                //左节点
                node = node.left;
            } else  {
                //相等
                /*
                * 传进来的值，覆盖原来的值
                * */
                node.element = element;
                return;
            }
        }
        //看看插入到父节点的那个位置
        Node<E> newNode = createNode(element,parent);
        if (cmp > 0) {
            //右边
            parent.right = newNode;
        } else {
            parent.left = newNode;
        }
        size++;
        //新添加节点之后的处理
        afterAdd(newNode);
    }

    /*
    * 添加之后的调整
    * @param node 新添加的节点
    * */
    protected void afterAdd(Node<E> node) {}
    /*
     * 删除之后的调整
     * @param node 删除的节点 或者用于取代被删除节点的子节点(当被删除的节点度为1)
     * */
    protected void afterRemove(Node<E> node) {}
    public void remove(E element) {
        remove(node(element));
    }

    private void remove(Node<E> node) {
        if (node == null) return;

        size--;

        if (node.hasTwoChildren()) { // 度为2的节点
            // 找到后继节点
            Node<E> s = successor(node);
            // 用后继节点的值覆盖度为2的节点的值
            node.element = s.element;
            // 删除后继节点
            node = s;
        }

        // 删除node节点（node的度必然是1或者0）
        Node<E> replacement = node.left != null ? node.left : node.right;

        if (replacement != null) { // node是度为1的节点
            // 更改parent
            replacement.parent = node.parent;
            // 更改parent的left、right的指向
            if (node.parent == null) { // node是度为1的节点并且是根节点
                root = replacement;
            } else if (node == node.parent.left) {
                node.parent.left = replacement;
            } else { // node == node.parent.right
                node.parent.right = replacement;
            }

            // 删除节点之后的处理
            afterRemove(replacement);
        } else if (node.parent == null) { // node是叶子节点并且是根节点
            root = null;

            // 删除节点之后的处理
            afterRemove(node);
        } else { // node是叶子节点，但不是根节点
            if (node == node.parent.left) {
                node.parent.left = null;
            } else { // node == node.parent.right
                node.parent.right = null;
            }

            // 删除节点之后的处理
            afterRemove(node);
        }
    }

    private Node<E> node(E element) {
        Node<E> node = root;
        while (node != null) {
            int cmp = compare(element,node.element);
            if (cmp == 0) return node;
            if (cmp > 0) {
                node = node.right;
            } else  {
                node = node.left;
            }
        }
        return null;
    }

    public boolean contains(E element) {
        return node(element) != null;
    }


    private void elementNotNullCheck(E element) {
        if (element == null) {
            throw new IllegalArgumentException("element must not be null");
        }
    }

    /*
    * @return 返回值等于0，代表e1和e2相等，返回值大于0，代表e1大于e2，返回值小于0,代表e1小于e2
    * */
    private int compare(E e1, E e2) {
        if (comparator != null) {
          return   comparator.compare(e1,e2);
        }
        return ((Comparable<E>) e1).compareTo(e2);
    }


}
