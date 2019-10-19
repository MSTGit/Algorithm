package com.xd.RBTree;

import java.util.Comparator;

public class AVLTree<E> extends BinaryBalanceSearchTree<E> {
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

    @Override
    protected void rotate(Node<E> r, Node<E> a, Node<E> b, Node<E> c, Node<E> d, Node<E> e, Node<E> f, Node<E> g) {
        super.rotate(r, a, b, c, d, e, f, g);
        updateHeight(b);
        updateHeight(f);
        updateHeight(d);
    }

    @Override
    protected void afterRotate(Node<E> grand, Node<E> parent, Node<E> child) {
        super.afterRotate(grand, parent, child);
        //更新grand，parent的高度
        updateHeight(grand);
        updateHeight(parent);
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
