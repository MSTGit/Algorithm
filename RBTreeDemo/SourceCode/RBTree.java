package com.xd.RBTree;

import java.util.Comparator;

public class RBTree<E> extends BinaryBalanceSearchTree<E> {
    private static final boolean RED = false;
    private  static final boolean BLACK = true;
    public RBTree(){
        this(null);
    }
    public RBTree(Comparator<E> comparator) {
        super(comparator);
    }


    //给节点染色
    private  Node<E> color(Node<E> node ,boolean color) {
        if (node == null)return node;
        //染色
        ((RBNode<E>)node).color = color;
        return node;
    }

    //将节点染成红色
    private Node<E> red(Node<E> node) {
       return color(node,RED);
    }

    //将节点染成黑色
    private Node<E> black(Node<E> node) {
        return color(node,BLACK);
    }

    //获取当前节点的颜色
    private boolean colorOf(Node<E> node) {
        return node == null ? BLACK : ((RBNode<E>)node).color;
    }

    //判断当前节点是否为黑色
    private boolean isBlack(Node<E> node) {
        return colorOf(node) == BLACK;
    }
    //判断当前节点是否为红色
    private boolean isRed(Node<E> node) {
        return colorOf(node) == RED;
    }


    @Override
    protected void afterAdd(Node node) {
        //拿到新添加节点的父节点
        Node<E> parent = node.parent;
        //添加的是根节点或者上溢到了根节点
        if (parent == null) {
            //直接将根节点染成黑色
            black(node);
            return;
        }
        //如果父节点是黑色，则不需要做任何处理
        if (isBlack(parent)) return;
        //拿到当前节点的叔父节点
        Node<E> uncle = parent.sibling();
        //拿到当前节点的祖父节点,然后再把祖父几点染为红色
        Node<E> grand = red(parent.parent);
        if (isRed(uncle)){//uncle节点是红色
            black(parent);//将父节点染为黑色
            black(uncle);//将叔父节点染为黑色
            //把祖父节点当做是新添加的节点
            afterAdd(grand);
            return;
        }
        //叔父节点不是红色
        if (parent.isLeftChild()){//L
            if (node.isLeftChild()) {//LL
                black(parent);
            } else {
                //LR
                black(node);
                rotateLeft(parent);
            }
            rotateRight(grand);
        } else { //R
            if (node.isLeftChild()) {
                //RL
                black(node);
                rotateRight(parent);
            } else {
                //RR
                black(parent);
            }
            rotateLeft(grand);
        }

    }

    @Override
    protected void afterRemove(Node<E> node) {
        //如果被删除的节点是红色
        // 或者用于取代node的子节点是红色
        if (isRed(node)){
            black(node);
            return;
        }
        Node<E> parent = node.parent;
        //删除的是根节点
        if (parent == null) return;
        //删除的是黑色叶子节点【下溢】
        //判断被删除的node是左还是右
        boolean left = parent.left == null || node.isLeftChild();
        Node<E> sibling = left ? parent.right : parent.left;
        if (left) {//被删除的节点在左边，兄弟节点在右边
            if (isRed(sibling)){//兄弟节点是红色
                black(sibling);
                red(parent);
                rotateLeft(parent);
                //更换兄弟
                sibling = parent.right;
            }
            //兄弟节点必然是黑色
            if (isBlack(sibling.left) && isBlack(sibling.right)) {
                //兄弟节点没有一个红色子节点，不能借，父节点只能向下与兄弟合并
                boolean parentBlack = isBlack(parent);
                black(parent);
                red(sibling);
                if (parentBlack) {
                    //父节点是黑色，父节点有向下合并，因此父节点的位置又产生了下溢，此时当做父节点被删除掉了
                    afterRemove(parent);
                }
            } else {//兄弟节点至少有一个红色子节点,向兄弟节点借元素
                if (isBlack(sibling.right)) {//兄弟节点的右边是黑色，兄弟要先旋转
                    rotateRight(sibling);
                    sibling = parent.right;
                }
                color(sibling,colorOf(parent));
                black(sibling.right);
                black(parent);
                rotateRight(parent);
            }
        } else {//被删除的节点在右边，兄弟节点在左边
            if (isRed(sibling)){//兄弟节点是红色
                black(sibling);
                red(parent);
                rotateRight(parent);
                //更换兄弟
                sibling = parent.left;
            }
            //兄弟节点必然是黑色
            if (isBlack(sibling.left) && isBlack(sibling.right)) {
                //兄弟节点没有一个红色子节点，不能借，父节点只能向下与兄弟合并
                boolean parentBlack = isBlack(parent);
                black(parent);
                red(sibling);
                if (parentBlack) {
                    //父节点是黑色，父节点有向下合并，因此父节点的位置又产生了下溢，此时当做父节点被删除掉了
                    afterRemove(parent);
                }
            } else {//兄弟节点至少有一个红色子节点,向兄弟节点借元素
                if (isBlack(sibling.left)) {//兄弟节点的左边是黑色，兄弟要先旋转
                    rotateLeft(sibling);
                    sibling = parent.left;
                }
                color(sibling,colorOf(parent));
                black(sibling.left);
                black(parent);
                rotateRight(parent);
            }
        }
    }
//    protected void afterRemove(Node<E> node,Node<E> replacement) {
//        //如果被删除的节点是红色，不需要做任何处理，直接删除
//        if (isRed(node)) return;
//        //用于取代node的子节点是红色
//        if (isRed(replacement)){
//            black(replacement);
//            return;
//        }
//        Node<E> parent = node.parent;
//        //删除的是根节点
//        if (parent == null) return;
//        //删除的是黑色叶子节点【下溢】
//        //判断被删除的node是左还是右
//        boolean left = parent.left == null || node.isLeftChild();
//        Node<E> sibling = left ? parent.right : parent.left;
//        if (left) {//被删除的节点在左边，兄弟节点在右边
//            if (isRed(sibling)){//兄弟节点是红色
//                black(sibling);
//                red(parent);
//                rotateLeft(parent);
//                //更换兄弟
//                sibling = parent.right;
//            }
//            //兄弟节点必然是黑色
//            if (isBlack(sibling.left) && isBlack(sibling.right)) {
//                //兄弟节点没有一个红色子节点，不能借，父节点只能向下与兄弟合并
//                boolean parentBlack = isBlack(parent);
//                black(parent);
//                red(sibling);
//                if (parentBlack) {
//                    //父节点是黑色，父节点有向下合并，因此父节点的位置又产生了下溢，此时当做父节点被删除掉了
//                    afterRemove(parent,null);
//                }
//            } else {//兄弟节点至少有一个红色子节点,向兄弟节点借元素
//                if (isBlack(sibling.right)) {//兄弟节点的右边是黑色，兄弟要先旋转
//                    rotateRight(sibling);
//                    sibling = parent.right;
//                }
//                color(sibling,colorOf(parent));
//                black(sibling.right);
//                black(parent);
//                rotateRight(parent);
//            }
//        } else {//被删除的节点在右边，兄弟节点在左边
//            if (isRed(sibling)){//兄弟节点是红色
//                black(sibling);
//                red(parent);
//                rotateRight(parent);
//                //更换兄弟
//                sibling = parent.left;
//            }
//            //兄弟节点必然是黑色
//            if (isBlack(sibling.left) && isBlack(sibling.right)) {
//                //兄弟节点没有一个红色子节点，不能借，父节点只能向下与兄弟合并
//                boolean parentBlack = isBlack(parent);
//                black(parent);
//                red(sibling);
//                if (parentBlack) {
//                    //父节点是黑色，父节点有向下合并，因此父节点的位置又产生了下溢，此时当做父节点被删除掉了
//                    afterRemove(parent,null);
//                }
//            } else {//兄弟节点至少有一个红色子节点,向兄弟节点借元素
//                if (isBlack(sibling.left)) {//兄弟节点的左边是黑色，兄弟要先旋转
//                    rotateLeft(sibling);
//                    sibling = parent.left;
//                }
//                color(sibling,colorOf(parent));
//                black(sibling.left);
//                black(parent);
//                rotateRight(parent);
//            }
//        }
//    }


    @Override
    public String toString() {
        return "99999";
    }

    @Override
    protected Node createNode(Object element, Node parent) {
        return  new RBNode(element, parent);
    }

    private static class RBNode<E> extends Node<E> {
        boolean color = RED;
        public RBNode(E element, Node<E> parent) {
            super(element, parent);
        }

        @Override
        public String toString() {
            String str = "";
            if (color == RED) {
                str = "R_";
            }
            return str + element.toString();
        }
    }
}
