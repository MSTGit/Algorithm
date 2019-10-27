package com.xd.BinarySearchTree;

import com.xd.BinarySearchTree.printer.BinaryTreeInfo;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

public class BinarySearchTree<E> implements BinaryTreeInfo {
    private int size;
    private Node<E> root;

    @Override
    public Object root() {
        return root;
    }

    @Override
    public Object left(Object node) {
        return ((Node<E>)node).left;
    }

    @Override
    public Object right(Object node) {
        return ((Node<E>)node).right;
    }

    @Override
    public Object string(Object node) {
        return ((Node<E>)node).element;
    }

    private Comparator<E> comparator;

    public BinarySearchTree() {
        this(null);
    }
    public BinarySearchTree(Comparator<E> comparator) {
        this.comparator = comparator;
    }
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

    public void add(E element) {
        elementNotNullCheck(element);
        if (root == null) {
            //添加的第一个节点
            root = new Node<>(element,null);
            size++;
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
        Node<E> newNode = new Node(element,parent);
        if (cmp > 0) {
            //右边
            parent.right = newNode;
        } else {
            parent.left = newNode;
        }
        size++;
    }

    public void remove(E element) {
        remove(node(element));
    }

    private void remove(Node<E> node) {
        if (node == null) return;
        size--;
        //度为2的情况
        if (node.hasTwoChildren()) {
            //找到后继节点
            Node<E> s = successor(node);
            //用后继节点的值，覆盖度为2的节点的值
            node.element = s.element;
            //删除后继节点
            node = s;
        }
        //删除node节点(node的度必然位1或者0)
        Node<E> replacement = node.left != null ? node.left : node.right;
        if (replacement != null) {
            //node是度为1的节点
            //更改parent
            replacement.parent = node.parent;
            //更改parent的left，right的指向
            if (node.parent == null) {
                //node是度为1的节点，并且是根节点
                root = replacement;
            }else if (node == node.parent.left) {
                node.parent.left = replacement;
            } else {
                node.parent.right = replacement;
            }
        } else if (node == root){
            //node是叶子节点.并且是根节点
            root = null;
        } else {
            //node是叶子节点，但不是根节点
            if (node == node.parent.right) {
                node.parent.right = null;
            } else {//node == node.parent.left
                node.parent.left = null;
            }
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

    /*
    * 前序遍历
    * */
    public void preorderTraversal() {
        preorderTraversal(root);
    }

    private void  preorderTraversal(Node<E> node) {
        if (node == null) {
            return;
        }
        System.out.println(node.element);
        preorderTraversal(node.left);
        preorderTraversal(node.right);
    }

    /*
    * 中序遍历
    * */
    public void inorderTraversal(){
        inorderTraversal(root);
    }

    private void inorderTraversal(Node<E> node) {
        if (node == null) return;
        inorderTraversal(node.left);
        System.out.println(node.element);
        inorderTraversal(node.right);
    }

    //后序遍历
    public void postorderTraversal() {
        postorderTraversal(root);
    }

    private void postorderTraversal(Node<E> node) {
        if (node == null) return;
        inorderTraversal(node.left);
        inorderTraversal(node.right);
        System.out.println(node.element);
    }

    //层序遍历
    public void levelorderTraversal() {
        if (root == null) return;
        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            Node<E> node = queue.poll();
            System.out.println(node.element);
            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
        }
    }

    public void preOrder(Visitor<E> visitor) {}
    private void preOrder(Node<E> node , Visitor<E> visitor) {
        if (node == null || visitor == null) return;
        visitor.visit(node.element);
        preOrder(node.left,visitor);
        preOrder(node.right,visitor);
    }

    public void inOrder(Visitor<E> visitor) {}
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

    public static  interface Visitor<E> {
        void visit(E element);
    }

    private void elementNotNullCheck(E element) {
        if (element == null) {
            throw new IllegalArgumentException("element must not be null");
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        toString(root,sb,"");
        return sb.toString();
    }

    private void toString(Node<E> node ,StringBuilder sb, String prefix) {
        if (node == null) return;
        sb.append(prefix).append(node.element).append("\n");
        toString(node.left,sb,prefix + "L - - -");
        toString(node.right,sb,prefix + "R - - -");
    }

    public int height() {
        return height(root);
    }

    private int height(Node<E> node) {
        if (node == null) return 0;
        return 1 + Math.max(height(node.left),height(node.right));
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

    public boolean isComplete() {
        if (root == null) return false;
        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(root);
        boolean leaf = false;
        while (!queue.isEmpty()) {
            Node<E> node = queue.poll();
            if (leaf && !node.isLeaf()) return false;
            if (node.hasTwoChildren()) {
                queue.offer(node.left);
                queue.offer(node.right);
            } else if (node.left == null && node.right != null) {
                return false;
            } else {
                //意味着后遍历的节点，都必须是叶子节点
                leaf = true;
            }
        }
        return true;
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

    private Node<E> predecessor(Node<E> node) {
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


    private Node<E> successor(Node<E> node) {
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

    private static class Node<E> {
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
    }
}
