package com.xd.Trie;

import java.util.HashMap;

public class Trie<V> {
    private int size;
    private Node<V> root;
    int size(){
        return size;
    }
    boolean isEmpty() {
        return size == 0;
    }
    V add(String str,V value) {
        keyCheck(str);
        //创建根节点
        if (root == null) {
            root = new Node<>(null);
        }
        Node<V> node = root;
        int len = str.length();
        for (int i = 0; i < len; i++) {
            char c = str.charAt(i);
            boolean emptyChildren = node.children == null;
            Node<V> childNode = emptyChildren ? null : node.children.get(c);
            if (childNode == null) {
                childNode = new Node<>(node);
                childNode.character = c;
                node.children = emptyChildren ? new HashMap<>() : node.children;
                node.children.put(c,childNode);
            }
            node = childNode;
        }
        if (!node.word) {//新增一个单词
            node.word = true;
            node.value = value;
            size++;
            return null;
        }
        V oldvalue = node.value;
        node.value = value;
        return oldvalue;
    }
    V remove(String str) {
        //找到最后一个节点
        Node<V> node = node(str);
        V oldValue = node.value;
        //如果不是单词结尾，不用做任何处理
        if (node == null || !node.word) return null;
        size--;
        //如果还有子节点
        if (node.children != null && !node.children.isEmpty()) {
            node.word = false;//把当前节点的单词结尾标记去掉
            node.value = null;
            return oldValue;
        }
        //如果没有子节点
        Node<V> parent = null;
        while ((parent = node.parent) != null) {
            parent.children.remove(node.character);
            if (parent.word || !parent.children.isEmpty()) {//删掉以后还有其他子节点，就不再往上删除,停止循环
                break;
            }
            node = parent;
        }
        return oldValue;
    }
    boolean startsWith(String prefix) {
        return node(prefix) != null;
    }
    boolean contains(String str) {
        Node<V> node = node(str);
        return node != null && node.word;
    }
    V get(String str) {
        Node<V> node = node(str);
        return node != null && node.word ? node.value : null;
    }
    void clear() {
        size = 0;
        root = null;
    }

    private Node<V> node(String key) {
        keyCheck(key);
        Node<V> node = root;
        int len = key.length();
        for (int i = 0; i < len; i++) {
            if (node == null || node.children == null || node.children.isEmpty()) {
                return null;
            }
            char c = key.charAt(i);
            node = node.children.get(c);
        }
        return node;
    }

    private void keyCheck(String key) {
        if (key == null || key.length() == 0) {
            throw new IllegalArgumentException("key must not be empty");
        }
    }
    private static class Node<V> {
        HashMap<Character,Node<V>> children;
        Node<V> parent;
        V value;
        Character character;
        boolean word;//是否为单词的结尾（是否为完整的一个单词）

        public Node(Node<V> parent) {
            this.parent = parent;
        }
    }


}
