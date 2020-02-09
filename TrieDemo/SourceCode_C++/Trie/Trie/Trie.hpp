//
//  Trie.hpp
//  Trie
//
//  Created by ducktobey on 8/2/2020.
//  Copyright © 2020 ducktobey. All rights reserved.
//

#ifndef Trie_hpp
#define Trie_hpp

#include <stdio.h>
#include "HashMap/HashMap.hpp"
template <typename T>
class Trie {
    class Node {
    public:
        HashMap<char, Node*> *m_children = new HashMap<char, Node *>();
        Node *m_parent = nullptr;//保存的是当前位置中,保存的元素
        T m_value = NULL;
        char m_character = 0;
        bool m_word = false;
        Node(Node *parent):m_parent(parent){};
        ~Node() {
            delete m_children;
            m_children = nullptr;
        }
    };
    int m_size = 0;
    Node *m_root = nullptr;
    Node *node(std::string str) {
        Node *node = m_root;
        int strLen = (int)str.length();
        for (int i = 0; i < strLen; i++) {
            if (node == nullptr || node->m_children == NULL || node->m_children->isEmpty()) {
                return nullptr;
            }
            char c = str.at(i);
            node = node->m_children->get(c);
        }
        return node;
    }
public:
    Trie() {
        new HashMap<char, Node *>();
        new Node(nullptr);
    }
    int size();
    bool isEmpty();
    T add(std::string str, T value);
    T remove(std::string str);
    bool startsWith(std::string prefix);
    bool contains(std::string str);
    T get(std::string str);
    void clear();
};
template <typename T>
int Trie<T>::size() {
    return m_size;
}
template <typename T>
bool Trie<T>::isEmpty() {
    return !m_size;
}
template <typename T>
T Trie<T>::add(std::string str, T value) {
    if (!m_root) {
        m_root = new Node(nullptr);//如果根节点不存在,就创建一个根节点
    }
    Node *node = m_root;
    int stringLen = (int)str.length();
    for (int i = 0 ; i < stringLen; i++) {
        //获取字符串中第i位的字符
        char c = str.at(i);
        bool emptyChildren = node->m_children == NULL;
        Node *childNode = emptyChildren ? nullptr : node->m_children->get(c);
        if (!childNode) {//子节点不存在,就创建一个节点
            childNode = new Node(node);
            childNode->m_character = c;
            node->m_children = emptyChildren ? new HashMap<char,Node *>() : node->m_children;
            node->m_children->put(c,childNode);//把新创建的节点,保存到哈希表中
        }
        node = childNode;//继续往下查找
    }
    //到这里,说明已经添加完了
    if (!node->m_word) {//说明不是一个新单词
        node->m_word = true;//标记是一个单词的结尾
        node->m_value = value;//把这个单词保存到该节点中
        m_size++;//说明新增了一个单词
        return NULL;//是一个新单词
    }
    //说明不是一个新单词,更新单词的value
    T oldValue = node->m_value;
    node->m_value = value;
    return oldValue;
}
template <typename T>
T Trie<T>::remove(std::string str) {
    //1.获取当前的节点
    Node *n = node(str);
    //2.如果当前节点不是一个单词,就不做删除,说明单词不存在,直接返回空
    if (!n && !n->m_word) return NULL;
    m_size--;
    T oldValue = n->m_value;
    //能到这里,说明是一个单词
    //3.如果还有子节点
    if (n->m_children && !n->m_children->isEmpty()) {
        //将单词结尾标记置为false
        n->m_word = false;
        n->m_value = NULL;
        return oldValue;
    }
    //4.说明已经没有子节点了
    Node *parent = NULL;
    while ((parent = n->m_parent) != NULL) {
        parent->m_children->remove(n->m_character);//将当前的字符从哈希表中移除
        if (parent->m_word || !parent->m_children->isEmpty()) {//如果父节点是一个单词,或者将当前的字符移除以后,父节点哈希表中还有字符,说明父节点还被其他字符串使用者,就不在处理了
            return oldValue;
        }
        //否则就更新当前的节点,继续删除
        n = parent;
    }
    
    
    
    return oldValue;
}
template <typename T>
bool Trie<T>::startsWith(std::string prefix) {
    return node(prefix);
}
template <typename T>
bool Trie<T>::contains(std::string str) {
      Node *n = node(str);
    return n && n->m_word;
}
template <typename T>
T Trie<T>::get(std::string str) {
    Node *n = node(str);
    if (n && n->m_word) return n->m_value;
    return NULL;
}
template <typename T>
void Trie<T>::clear() {
    m_size = 0;
    delete m_root;
    m_root = nullptr;
}
#endif /* Trie_hpp */
