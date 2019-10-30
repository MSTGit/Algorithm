package com.xd.HashTable.Map;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

public class TreeMap<K,V> implements Map<K, V> {
    private static final boolean RED = true;
    private static final  boolean BLACK = false;
     private int size;
     private Node<K,V> root;
    private Comparator<K> comparator;
    public TreeMap() {
        this(null);
    }

    public TreeMap(Comparator<K> comparator) {
        this.comparator = comparator;
    }
    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
            size = 0;
            root = null;
    }

    @Override
    /*
    * 这里的返回值是返回的旧值
    * */
    public V put(K key, V value) {
        keyNotNullCheck(key);
        if (root == null) {//添加第一个节点
            root = new Node<K,V>(key,value,null);
            size++;
            //添加新节点后的处理
            afterPut(root);
            return null;
        }
        //添加的不是第一个节点 找到最适合key的节点
        Node<K,V> parent = root;
        Node<K,V> node = root;
        int cmp = 0;
        do {
            cmp = compare(key,node.key);//与节点的key比大小。来决定往左还是往右寻找
            parent = node;//更新parent 一层一层往下遍历
            if (cmp > 0) {
                node = node.right;//遍历右子树
            } else if (cmp < 0) {
                node = node.left;//遍历左子树
            } else { //相等
                node.key = key;
                V oldValue = node.value;
                node.value = value;
                return oldValue;
            }
        }while (node != null);
        //已经找到最合适的node来添加该节点
        //创建新的节点
        Node<K,V> newNode = new Node<>(key,value,parent);
        if (cmp > 0) { //添加右子树
            parent.right = newNode;
        } else {//添加到左子树
            parent.left = newNode;
        }
        size++;
        afterPut(newNode);
        return null;
    }

    @Override
    public V get(K key) {
        Node<K,V> node = node(key);
        return node == null ? null :node.value;
    }

    @Override
    public V remove(K key) {
        return remove(node(key));
    }

    @Override
    public boolean containsKey(K key) {
        return node(key) != null;
    }

    @Override
    public boolean containsValue(V value) {
        if (root == null) return false;//说明当前为空树
        Queue<Node<K, V>> queue = new LinkedList<>();
        queue.offer(root);
        if (!queue.isEmpty()) {
            Node<K,V> node = queue.poll();
            if (valEquals(value,node.value) ) return true;
            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
        }
        return false;
    }

    @Override
    public void traversal(Visitor<K,V> visitor) {
        if (visitor == null) return;
        traversal(root,visitor);
    }

    private void traversal (Node<K,V> node, Visitor<K,V> visitor) {
        if (node == null || visitor.stop) {return;

        }
        traversal(node.left,visitor);
        if (visitor.stop) return;
        visitor.visit(node.key,node.value);
        traversal(node.right,visitor);
    }

    private boolean valEquals(V v1, V v2) {
        return v1 == null ? v2 == null : v1.equals(v2);
    }

    //删除某节点
    private V remove(Node<K,V> node) {
        if (node == null) return null;
        size--;
        V oldValue = node.value;
        if (node.hasTwoChildren()) {//度为2的节点，说明删除的是黑色节点，如红黑树笔记删除部分的节点25，或者节点55的情况
            //找到后继节点
            Node<K,V> s = successor(node);
            //用后继节点的值，覆盖度为2节点的值
            node.key = s.key;
            node.value = s.value;
            //删除后继节点
            node = s;
        }

        //删除node节点，此时node的度必然为1或者0
        Node<K,V> replacement = node.left != null ? node.left : node.right;
        if (replacement != null) {
            //更改parent
            replacement.parent = node.parent;
            //更改parent的left，right指向
            if (node.parent == null) {
                //node是root节点，并且度为1
                root = replacement;
            } else if (node == node.parent.left) {//node是parent的左子树
                node.parent.left = replacement;
            } else {
               // node == node.parent.right
                node.parent.right = replacement;
            }
            //删除之后的处理
            afterRemove(replacement);
        } else if (node.parent == null) {
            //删除的是根节点
            root = null;
        } else {
            //node是叶子节点
            if (node == node.parent.right) {
                node.parent.right = null;
            } else {
                //node == node.parent.left
                node.parent.left = null;
            }
            afterRemove(node);
        }

        return oldValue;
    }

    private void afterRemove(Node<K, V> node) {
        // 如果删除的节点是红色
        // 或者 用以取代删除节点的子节点是红色
        if (isRed(node)) {
            black(node);
            return;
        }

        Node<K, V> parent = node.parent;
        if (parent == null) return;

        // 删除的是黑色叶子节点【下溢】
        // 判断被删除的node是左还是右
        boolean left = parent.left == null || node.isLeftChild();
        Node<K, V> sibling = left ? parent.right : parent.left;
        if (left) { // 被删除的节点在左边，兄弟节点在右边
            if (isRed(sibling)) { // 兄弟节点是红色
                black(sibling);
                red(parent);
                rotateLeft(parent);
                // 更换兄弟
                sibling = parent.right;
            }

            // 兄弟节点必然是黑色
            if (isBlack(sibling.left) && isBlack(sibling.right)) {
                // 兄弟节点没有1个红色子节点，父节点要向下跟兄弟节点合并
                boolean parentBlack = isBlack(parent);
                black(parent);
                red(sibling);
                if (parentBlack) {
                    afterRemove(parent);
                }
            } else { // 兄弟节点至少有1个红色子节点，向兄弟节点借元素
                // 兄弟节点的左边是黑色，兄弟要先旋转
                if (isBlack(sibling.right)) {
                    rotateRight(sibling);
                    sibling = parent.right;
                }

                color(sibling, colorOf(parent));
                black(sibling.right);
                black(parent);
                rotateLeft(parent);
            }
        } else { // 被删除的节点在右边，兄弟节点在左边
            if (isRed(sibling)) { // 兄弟节点是红色
                black(sibling);
                red(parent);
                rotateRight(parent);
                // 更换兄弟
                sibling = parent.left;
            }

            // 兄弟节点必然是黑色
            if (isBlack(sibling.left) && isBlack(sibling.right)) {
                // 兄弟节点没有1个红色子节点，父节点要向下跟兄弟节点合并
                boolean parentBlack = isBlack(parent);
                black(parent);
                red(sibling);
                if (parentBlack) {
                    afterRemove(parent);
                }
            } else { // 兄弟节点至少有1个红色子节点，向兄弟节点借元素
                // 兄弟节点的左边是黑色，兄弟要先旋转
                if (isBlack(sibling.left)) {
                    rotateLeft(sibling);
                    sibling = parent.left;
                }

                color(sibling, colorOf(parent));
                black(sibling.left);
                black(parent);
                rotateRight(parent);
            }
        }
    }

    //通过某节点，查找该节点的后继节点
    private Node<K,V> successor(Node<K,V> node) {
        if (node == null) return null;
        //后继节点，在当前节点的右边 ,然后在右边的节点中，一路往左找，直到为空
        Node<K,V> p = node.right;
        if (p != null) {
            while (p.left != null) {//直到找到p的左节点为空，说明已经找到了叶子节点，停止遍历，返回当前遍历到的叶子节点
                p = p.left;
            }
            return p;
        }
        //如果当前节点没有右子树，则从父节点或者祖父节点中去查找
        if (node.parent != null && node.parent.right != null) {
            node = node.parent;
        }
        return node;
    }

    //通过某节点，查找该节点的前驱节点
    private Node<K, V> predecessor(Node<K, V> node) {
        if (node == null) return null;
        //与查找后继节点相反
        Node<K,V> p = node.right;
        if (p != null){
            while (p.right != null) {
                p = p.right;
            }
            return p;
        }
        //如果当前节点没有左子树，则从父节点或者祖父节点中去查找
        if (node.parent != null && node.parent.left != null) {
            node = node.parent;
        }
        return node;
    }

    //通过key查找节点
    private Node<K,V> node(K key) {
        Node<K,V> node = root;//从根节点开始遍历
        while (node != null) {
            int cmp = compare(key,node.key);
            if (cmp == 0) return node;//两个的key相同，表示找到了对应的节点
            if (cmp < 0){ node = node.left;}
            else if (cmp > 0) {node = node.right;};
        }

        return null;
    }
    //添加节点后的处理
    private void afterPut(Node<K, V> node) {
        //获取当前节点的父节点
        Node<K,V> parent = node.parent;
        //如果是根节点，或者上浮到了根节点
        if (parent == null) {
            //直接把节点染黑
            black(node);
            return;
        }
        //如果父节点是黑色，不用做任何处理
        if (isBlack(parent)) return;
        //获取叔父节点
        Node<K,V> uncle = parent.sibling();
        //获取祖父节点
        // 祖父节点
        Node<K, V> grand = parent.parent;
        if (isRed(uncle)) {//叔父节点为红色，红黑树笔记中，往节点25左右子树中添加子节点的情况，会导致B树节点上溢
            //1.parent,uncle染为BLACK，因为parent和uncle会分别分裂为一个单独的子树
            //2.grand染为红色，并且向上合并，把grand当做新添加的节点向上合并，递归调用修复方法最终修复性质
            black(parent);
            black(uncle);
            afterPut(red(grand));
            return;
        }
        //下面的情况都属于叔父不是红色的情况 属于笔记中，节点50或者72中添加子节点的情况
        //往节点50中添加元素
        if (parent.isRightChild()) {//往黑色节点右边的子树中添加子节点
            if (node.isRightChild()) {
                //添加到右子树的右边 RR (往节点50的右边添加元素52的情况)
                black(parent);
                red(grand);
                rotateLeft(grand);
            } else {
                //添加到右子树的左边 RL (往节点50的左边添加48的情况)
                red(grand);
                black(node);
                rotateRight(parent);
                rotateLeft(grand);
            }

        } else {//往黑色节点左边的子树中添加子节点
            if (node.isRightChild()) {
                //添加的左子树的右边 LR（往节点72添加74的情况）
                black(node);
                red(grand);
                rotateLeft(parent);
                rotateRight(grand);
            } else {
                //添加到左子树的左边 LL （往节点72的左边添加元素60的情况）
                //1.把parent染黑，grand染红
                black(parent);
                red(grand);
                rotateRight(grand);
            }

        }
    }

    //向左旋转
    private void rotateLeft(Node<K, V> grand) {
        Node<K,V> parent = grand.right;
        Node<K,V> child = parent.left;//保存parent的左子树
        parent.left = grand;
        grand.right = child;
        afterRotate(grand,parent,child);
    }

    //向右旋转 左右旋转是反过来的
    private void rotateRight(Node<K, V> grand) {
        Node<K,V> parent = grand.left;
        Node<K,V> child = parent.right;//保存parent的右子树
        parent.right = grand;
        grand.left = child;
        afterRotate(grand,parent,child);
    }

    private void afterRotate(Node<K,V> grand,Node<K,V> parent,Node<K,V> child) {
        //让parent成为子树的根节点 更新parent的parent
        parent.parent = grand.parent;
        //维护grand.parent的左右子树
        if (grand.isRightChild()) {
            grand.parent.right = parent;
        } else if (grand.isLeftChild()) {
            grand.parent.left = parent;
        } else  {//是根子树
            root = parent;
        }
        //更新child的parent
        if (child != null) {
            child.parent = grand;
        }
        //更新grand的parent
        grand.parent = parent;

    }

    //辅助函数
    //将节点染色
    private Node<K,V> color(Node<K,V> node,boolean color) {
        if (node == null) return node;//如果节点为空，不做任何处理
        node.color = color;
        return node;
    }
    //将节点染为红色
    private Node<K,V> red(Node<K,V> node) {
        return color(node,RED);
    }
    //将讲点染为黑色
    private Node<K,V> black(Node<K,V> node) {
        return color(node,BLACK);
    }
    //获取当前节点的颜色
    private boolean colorOf(Node<K, V> node) {
        return node == null ? BLACK : node.color;
    }

    //判断当前节点是否为黑色
    private boolean isBlack(Node<K, V> node) {
        return colorOf(node) == BLACK;
    }

    //判断当前节点是否为红色
    private boolean isRed(Node<K, V> node) {
        return colorOf(node) == RED;
    }

    //比较两个节点中Key的大小
    private int compare(K k1,K k2) {
        if (comparator != null) {
            return comparator.compare(k1,k2);
        }
        return  ((Comparable<K>)k1).compareTo(k2);
    }

    //检查key是否为空
    private void keyNotNullCheck(K key) {
        if (key == null) {
            throw new IllegalArgumentException("key must not be null");
        }
    }
    private static class Node<K,V> {
        K key;
        V value;
        boolean color = RED;
        Node<K,V> left;
        Node<K,V> right;
        Node<K,V> parent;
        private Node(K key,V value,Node<K,V> parent){
            this.key = key;
            this.value = value;
            this.parent = parent;
        }

        public boolean isLeaf(){return left == null && right == null;}
        public boolean hasTwoChildren() {return left != null && right != null;}
        public boolean isLeftChild() {return parent != null && this == parent.left;}
        public boolean isRightChild() {return parent != null && this == parent.right;}
        public Node<K, V> sibling() {//获取兄弟节点
            if (isLeftChild()) {
                return parent.right;
            }
            if (isRightChild()) {
                return parent.left;
            }
            return null;
        }
    }
}
