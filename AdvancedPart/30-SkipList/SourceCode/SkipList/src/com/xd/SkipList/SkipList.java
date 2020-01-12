package com.xd.SkipList;

import java.util.Comparator;

public class SkipList<K,V> {
    private static final int MAX_LEVEL = 32;
    private static final double P = 0.25;
    private int size;
    private Comparator<K> comparator;
    /*
    * 有效层数
    * */
    private int level;
    /*
    * 不存放任何的K-V
    * */
    private Node<K,V> first;

    public SkipList(Comparator<K> comparator) {
        this.comparator = comparator;
        first = new Node<>();
        first.nexts = new Node[MAX_LEVEL];
    }
    public SkipList() {
        this(null);
    }
    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public V put(K key, V value) {
        keyCheck(key);
        Node<K,V> node = first;
        Node<K,V>[] prevs = new Node[level];//用来存放插入节点的前驱节点
        for (int i = level - 1; i >= 0 ; i--) {
            int cmp = -1;
            while (node.nexts[i] != null && (cmp = compare(node.nexts[i].key,key)) < 0) {
                node = node.nexts[i];//更新node
            }

            if (cmp == 0) {
                //节点本来就存在，覆盖key对应的value
                V oldValue = node.nexts[i].value;
                node.nexts[i].value = value;
                return oldValue;
            }

            //保存前驱节点
            prevs[i] = node;
        }
        //说明当前key 不存在，并且确定前驱节点为node
        int newLevel = randomLevel();
        Node<K,V> newNode = new Node<>(key,value,newLevel);
        //遍历所有的前驱节点,更新每一层的连线
        for (int i = 0; i < newLevel; i++) {
            if (i >= level) {//说明新节点的层数大于原来节点的最大层数，直接从头节点连线到当前节点
                first.nexts[i] = newNode;
            } else {
                newNode.nexts[i] =  prevs[i].nexts[i];
                prevs[i].nexts[i] = newNode;
            }
        }
        //更新跳表的层数
        level = Math.max(level,newLevel);
        size++;
        return null;
    }

    public V get(K key) {
        keyCheck(key);
        Node<K,V> node = first;
        //nexts的索引是0 - （level - 1）
        for (int i = level - 1; i >= 0 ; i--) {
            int cmp = -1;
            while (node.nexts[i] != null && (cmp = compare(node.nexts[i].key,key)) < 0 ) {
                //当前节点的key，小于要找的节点的ke||说明找到当前层的最后了，所以应该会退到上一个节点，然后往下一层继续找
                node = node.nexts[i];//更新node
            }

            if (cmp == 0) {
                //说明找到了
                return node.nexts[i].value;
            }
            //当前节点的key，大于要找的节点的key，进入下一层进行查找
            //返回到上一个节点，进入下一层继续查找
        }
        //直到最后，还没找到，说明不存在
        return null;
    }

    public V remove(K key) {
        keyCheck(key);
        Node<K,V> node = first;
        Node<K,V>[] prevs = new Node[level];
        boolean exist = false;
        for (int i = level - 1; i >= 0 ; i--) {
            int cmp = -1;
            while (node.nexts[i] != null && (cmp = compare(node.nexts[i].key,key)) < 0 ) {
                node = node.nexts[i];
            }
            prevs[i] = node;
            if (cmp == 0) exist = true;
        }
        if (!exist) return null;//说明不存在
        Node<K,V> removedNode = node.nexts[0];
        for (int i = 0; i < removedNode.nexts.length; i++) {
            prevs[i].nexts[i] = removedNode.nexts[i];
        }
        size--;
        //更新跳表的层数
        int newLevel = level;
        while (--newLevel >0 && first.nexts[newLevel] == null ) {
            level = newLevel;
        }
        return removedNode.value;
    }

    private int randomLevel() {
        int level = 1;
        while (Math.random() < P && level < MAX_LEVEL) {
            level++;
        }
        return level;
    }

    private void keyCheck(K key) {
        if (key == null) {
            throw  new IllegalArgumentException("key must not be null.");
        }
    }

    private int compare(K k1,K k2) {
        return comparator != null ? comparator.compare(k1,k2) : ((Comparable<K>)k1).compareTo(k2);
    }

    private static class Node<K,V> {
        K key;
        V value;
        Node<K,V>[] nexts;
        public Node(){}
        public Node(K key, V value, int level) {
            this.key = key;
            this.value = value;
            nexts = new Node[level];
        }

        @Override
        public String toString() {
            return "{" + key  + " : " + value + "}_" + nexts.length;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("一共" + level + "层").append("\n");
        for (int i = level - 1; i >= 0; i--) {
            Node<K,V> node = first;
            while (node.nexts[i] != null) {
                sb.append(node.nexts[i]);
                sb.append(" ");
                node = node.nexts[i];
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
