package com.xd.AVLTree;

import java.util.Comparator;

public class AVLTree<E> extends BinarySearchTree<E> {
    public AVLTree(){
        this(null);
    }
    public AVLTree(Comparator<E> comparator) {
        super(comparator);
    }

    @Override
    protected void afterAdd(Node<E> node) {
        while ((node = node.parent) != null) {
            //判断当前node是否平衡
            if (isBalance(node)) {
                //更新高度
                updateHeight(node);
            } else {
                //恢复平衡
                rebalance(node);
                //整棵树恢复平衡
                break;
            }
        }
    }

    @Override
    protected void afterRemove(Node<E> node) {
        while ((node = node.parent) != null) {
            //判断当前node是否平衡
            if (isBalance(node)) {
                //更新高度
                updateHeight(node);
            } else {
                //恢复平衡
                rebalance(node);
            }
        }
    }

    @Override
    protected Node createNode(Object element, Node parent) {
        return new AVLNodel<>(element, parent);
    }

    /*
    * 恢复平衡 方式一：区分不同的旋转方式
    * @param grand 高度最低的那个不平衡节点
    * */
    private void rebalance(Node<E> grand) {
        Node<E> parent = ((AVLNodel<E>)grand).tallerChild();
        Node<E> node = ((AVLNodel<E>)parent).tallerChild();
        if (parent.isLeftChild()) { //L
            if (node.isLeftChild()) {
                //LL
                rotateRight(grand);
            } else  {
                //LR
                rotateLeft(parent);
                rotateRight(grand);
            }
        } else  {//R
            if (node.isLeftChild()) {
                //RL
                rotateRight(parent);
                rotateLeft(grand);
            } else  {
                //RR
                rotateLeft(grand);
            }
        }
    }
    /*
     * 恢复平衡 方式二：统一所有的旋转操作
     * @param grand 高度最低的那个不平衡节点
     * */
    private void rebalance2(Node<E> grand){
        Node<E> parent = ((AVLNodel<E>)grand).tallerChild();
        Node<E> node = ((AVLNodel<E>)parent).tallerChild();
        if (parent.isLeftChild()) { //L
            if (node.isLeftChild()) {
                //LL
                rotate(grand,node.left,node,node.right,parent,parent.right,grand,grand.right);
            } else  {
                //LR
                rotate(grand,parent.left,parent,node.left,node,node.right,grand,grand.right);
            }
        } else  {//R
            if (node.isLeftChild()) {
                //RL
                rotate(grand,grand.left,grand,node.left,node,node.right,parent,parent.right);
            } else  {
                //RR
                rotate(grand,grand.left,grand,parent.left,parent,node.left,node,node.right);
            }
        }
    }

    private void rotate(
            Node<E> r,// 子树的根节点
            Node<E> a,Node<E> b,Node<E> c,
            Node<E> d,
            Node<E> e,Node<E> f,Node<E> g
            ) {
        //让d成为子树的根节点
        d.parent = r.parent;
        if (r.isLeftChild()) {
            r.parent.left = d;
        } else if (r.isRightChild()) {
            r.parent.right = d;
        } else  {
            root = d;
        }
        //a- b- d
        b.left = a;
        if (a != null) {
            a.parent = b;
        }
        b.right = c;
        if (c != null) {
            c.parent = b;
        }
        updateHeight(b);
        //e - f - g

        f.left = e;
        if (e != null) {
            e.parent = f;
        }
        f.right = g;
        if (g != null) {
            g.parent = f;
        }
        updateHeight(f);
        //b - d - f
        d.left = b;
        d.right = f;
        b.parent = d;
        f.parent = d;
        updateHeight(d);
    }
    /*
    * 左旋转
    * */
    private void rotateLeft(Node<E> grand) {
            Node<E> parent = grand.right;
            Node<E> child = parent.left;
            grand.right = child;
            parent.left = grand;
            afterRotate(grand,parent,child);
    }

    /*
     * 右旋转
     * */
    private void rotateRight(Node<E> grand) {
        Node<E> parent = grand.left;
        Node<E> child = parent.right;
        grand.left = child;
        parent.right = grand;
        afterRotate(grand,parent,child);
    }

    private void afterRotate(Node<E> grand,Node<E> parent,Node<E> child){
        //让parent成为子树的根节点
        parent.parent = grand.parent;
        if (grand.isLeftChild()) {
            grand.parent.left = parent;
        } else if (grand.isRightChild()) {
            grand.parent.right = parent;
        } else {
            //grand是root节点
            root = parent;
        }

        //更新child的parent
        if (child != null) {
            child.parent = grand;
        }

        //更新grand的parent
        grand.parent = parent;

        //更新grand，parent的高度
        updateHeight(grand);
        updateHeight(parent);
    }
    private boolean isBalance(Node<E> node) {
        return Math.abs(((AVLNodel<E>)node).balanceFactor()) <= 1;
    }
    private void updateHeight(Node<E> node){
        AVLNodel<E> avlNode = (AVLNodel<E>)node;
        avlNode.updateHeight();
    }

    private static class AVLNodel<E> extends Node<E> {
        /*
         * 维护当前节点的高度
         * */
        int height = 1;

        public AVLNodel(E element, Node<E> parent) {
            super(element, parent);
        }

        /*
        * 获取AVL树的平衡因子
        * */
        public int balanceFactor() {
            int leftHeight = left == null ? 0 : ((AVLNodel<E>)left).height;
            int rightHeight = right == null ? 0 : ((AVLNodel<E>)right).height;
            return leftHeight - rightHeight;
        }

        public void  updateHeight(){
            int leftHeight = left == null ? 0 : ((AVLNodel<E>)left).height;
            int rightHeight = right == null ? 0 : ((AVLNodel<E>)right).height;
            height = 1 + Math.max(leftHeight,rightHeight);
        }

        public AVLNodel<E> tallerChild(){
            int leftHeight = left == null ? 0 : ((AVLNodel<E>)left).height;
            int rightHeight = right == null ? 0 : ((AVLNodel<E>)right).height;
            if (leftHeight > rightHeight) return (AVLNodel<E>)left;
            if (leftHeight < rightHeight) return (AVLNodel<E>)right;
            return isLeftChild() ? (AVLNodel<E>)left : (AVLNodel<E>)right;
        }
    }
}
