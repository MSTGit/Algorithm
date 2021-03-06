package com.xd.Set.Tree;



import java.util.LinkedList;
import java.util.Queue;

public class BinaryTree<E> {
    protected int size;
    protected Node<E> root;

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        root = null;
        size = 0;
    }

    public void preOrder(Visitor<E> visitor) {}
    private void preOrder(Node<E> node , Visitor<E> visitor) {
        if (node == null || visitor == null) return;
        visitor.visit(node.element);
        preOrder(node.left,visitor);
        preOrder(node.right,visitor);
    }

    public void inOrder(Visitor<E> visitor) {inOrder(root,visitor);}
    private void inOrder(Node<E> node , Visitor<E> visitor) {
        if (node == null || visitor == null) return;
        preOrder(node.left,visitor);
        visitor.visit(node.element);
        preOrder(node.right,visitor);
    }

    public void postOrder(Visitor<E> visitor) {}
    private void postOrder(Node<E> node ,Visitor<E> visitor) {
        if (node == null || visitor == null) return;
        preOrder(node.left,visitor);
        visitor.visit(node.element);
        preOrder(node.right,visitor);
    }

    public void levelOrder(Visitor<E> visitor) {
        if (root == null || visitor == null) return;
        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            Node<E> node = queue.poll();
            visitor.visit(node.element);
            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
        }
    }

    public boolean isComplete() {
        if (root == null) return false;
        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(root);
        boolean leaf = false;
        while (!queue.isEmpty()) {
            Node<E> node = queue.poll();
            if (leaf && !node.isLeaf()) return false;
            if (node.left != null) {
                queue.offer(node.left);
            } else if (node.right != null) {
                //node.left == null && node.right != null
                return false;
            }
            if (node.right != null) {
                queue.offer(node.right);
            } else {
                /*
                 * node.left == null && node.right == null
                 * node.left != null && node.right == null
                 * */
                leaf = true;
            }


        }
        return true;
    }

    public int height() {
        return height(root);
    }

    private int height(Node<E> node) {
        if (node == null) return 0;
        return 1 + Math.max(height(node.left),height(node.right));
    }

    protected Node<E> createNode(E element, Node<E> parent){
        return new Node<>(element,parent);
    }


    /*
     * 使用程序遍历实现
     * */
    public int height1() {
        if (root == null) return 0;
        int height = 0;//树的高度
        int levelSize = 1;//存储着每一层元素的数量
        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            Node<E> node = queue.poll();
            levelSize--;
            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
            if (levelSize == 0) { //即将访问下一层
                levelSize = queue.size();
                height++;
            }
        }
        return height(root);
    }



    public static  interface Visitor<E> {
        void visit(E element);
    }


    protected static class Node<E> {
        E element;
        /*
         * 左节点
         * */
        Node<E> left;
        /*
         * 右节点
         * */
        Node<E> right;
        /*
         * 父节点
         * */
        Node<E> parent;

        public Node(E element,Node<E> parent) {
            this.element = element;
            this.parent = parent;
        }

        public boolean isLeaf() {
            return left == null && right == null;
        }

        public boolean hasTwoChildren() {
            return left != null && right != null;
        }
        /*
        * 判断是否为父节点的左子树
        * */
        public boolean isLeftChild(){
            return parent != null && this == parent.left;
        }

        /*
        * 判断是否为父节点的右子树
        * */
        public boolean isRightChild() {
            return parent != null && this == parent.right;
        }

        /*
        * 获取兄弟节点
        * */
        public Node<E> sibling(){
            if (isLeftChild()){
                return parent.right;
            }
            if (isRightChild()){
                return parent.left;
            }
            //没有父节点，因此没有兄弟节点
            return null;
        }

    }

    protected Node<E> predecessor(Node<E> node) {
        if (node == null) return null;
        Node<E> p = node.left;
        if (p != null) {
            //从左子树中找前驱
            while (p.right != null) {
                p = p.right;
            }
            return p;
        }
        //从父节点，祖父节点中寻找前驱节点
        while (node.parent != null && node == node.parent.left) {
            node = node.parent;
        }
        //node.parent == null
        //node == node.parent.right
        return node.parent;
    }



    protected Node<E> successor(Node<E> node) {
        if (node == null) return null;
        Node<E> p = node.right;
        if (p != null) {
            //从右子树中找前驱
            while (p.left != null) {
                p = p.left;
            }
            return p;
        }
        //从父节点，祖父节点中寻找前驱节点
        while (node.parent != null && node == node.parent.right) {
            node = node.parent;
        }
        //node.parent == null
        //node == node.parent.right
        return node.parent;
    }
}
