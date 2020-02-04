//
//  TreeMap.hpp
//  Map
//
//  Created by ducktobey on 5/2/2020.
//  Copyright © 2020 ducktobey. All rights reserved.
//

#ifndef TreeMap_hpp
#define TreeMap_hpp

#include <stdio.h>
#include <queue>
template <typename K, typename V>
class TreeMap {
    class Node {
    public:
        K m_key;
        V m_value;
        Node *m_left = nullptr;//左子节点
        Node *m_right = nullptr;//右子节点
        Node *m_parent = nullptr;//父节点
        bool m_color = false;//表示红色
        Node() : m_left(nullptr),m_right(nullptr) {}
        Node(K key, V value, Node *parent):m_key(key),m_value(value),m_parent(parent),m_left(nullptr),m_right(nullptr) {};
        ~Node() {
            delete m_left; delete m_right;
        }
        bool isLeaf() { return m_left == NULL && m_right == NULL; }
        bool hasTwoChildren() { return m_left != NULL && m_right != NULL; }
        bool isLeftChild() { return m_parent && this == m_parent->m_left; }
        bool isRightChild() { return m_parent && this == m_parent->m_right; }
        Node *sibling() {
            if (isLeftChild()) { return m_parent->m_right; }
            if (isRightChild()) { return m_parent->m_left; }
            return nullptr;
        }
    };
    //内部成员变量
    int m_size = 0;
    Node *m_root = nullptr;
    bool m_red = false;
    bool m_black = true;
    void elementNodeNullCheck(K element) {
        if (element == NULL && element != 0) {
            throw "key must not be null";
        }
    }
    Node *node(K key){
        Node *node = m_root;
        while (node) {
            if (node->m_key == key) {
                return node;
            } else if (node->m_key > key) {
                node = node->m_left;
            } else {
                node = node->m_right;
            }
        }
        return nullptr;
    }
    Node * predecessor(Node *node){//找前驱节点
        if (!node) return nullptr;
        Node *p = node->m_left;
        if (p) {//如果有左子树,就查找左子树中,最右的节点
            while (p->m_right) {
                p = p->m_right;
            }
            //直到找到了最右的节点以后,返回该节点,就为前驱节点
            return p;
        }
        //如果没有左子树,就从父节点中继续查找//node == node->m_parent->m_left 表示要在左子树中查找
        while (node->m_parent && node == node->m_parent->m_left) {
            node = node->m_parent;
        }
        //能来到这里,就说明node->m_parent 为空或者node == node->m_parent->m_right.
        return node->m_parent;
    }
    Node * successor(Node *node){//找后继节点<与找前驱节点相反>
        if (!node) return nullptr;
        Node *p = node->m_right;
        if (p) {
            while (p->m_left) {
                p = p->m_left;
            }
            return p;
        }
        while (node->m_parent && node == node->m_parent->m_right) {
            node = node->m_parent;
        }
        return node->m_parent;
    }
    void remove(Node *node){
        if (!node) return;
        
        m_size--;
        if (node->hasTwoChildren()) {
            Node *s = successor(node);
            node->m_element = s->m_element;
            node = s;
        }
        Node *replacement = node->m_left ? node->m_left : node->m_right;
        if (replacement) {
            replacement->m_parent = node->m_parent;
            if (!node->m_parent) {
                m_root->m_right = nullptr;
                m_root->m_left = nullptr;
                delete m_root;
                m_root = replacement;
            } else if (node == node->m_parent->m_left) {
                if (node->m_left) {
                    node->m_left = nullptr;
                } else {
                    node->m_right = nullptr;
                }
                node->m_parent->m_left = replacement;
                delete node;
            } else {
                if (node->m_left) {
                    node->m_left = nullptr;
                } else {
                    node->m_right = nullptr;
                }
                node->m_parent->m_right = replacement;
                delete node;
            }
            afterRemove(replacement);
        } else if (node == m_root) {
            m_root = nullptr;
            afterRemove(node);
            delete m_root;
        } else {
            if (node == node->m_parent->m_left) {
                node->m_parent->m_left = nullptr;
            } else {
                node->m_parent->m_right = nullptr;
            }
            afterRemove(node);
            delete node;
        }
    }
    
    void rotateLeft(Node *grand) {
        Node *parent = grand->m_right;
        Node *node = parent->m_left;
        grand->m_right = parent->m_left;
        parent->m_left = grand;
        afterRotate(grand, parent, node);
    }
    void rotateRight(Node *grand) {
        Node *parent = grand->m_left;
        Node *node = parent->m_right;
        grand->m_left = node;
        parent->m_right = grand;
        afterRotate(grand, parent, node);
    }
    
    void afterRotate(Node *grand, Node *parent, Node *child) {
        parent->m_parent = grand->m_parent;
        if (grand->isLeftChild()) {
            grand->m_parent->m_left = parent;
        } else if(grand->isRightChild()){
            grand->m_parent->m_right = parent;
        } else {
            m_root = parent;
        }
        if (child) child->m_parent = grand;
        grand->m_parent = parent;
    }
    
    Node *color(Node *node,bool color) {
        if (!node) return node;
        node->m_color = color;
        return node;
    }
    
    Node *red(Node *node) {
        node->m_color = m_red;
        return node;
    }
    
    Node *black(Node *node) {
        node->m_color = m_black;
        return node;
    }
    
    bool colorOf(Node *node) {
        return node ? node->m_color : m_black;
    }
    
    bool isBlack(Node *node) {
        return colorOf(node) == m_black;
    }
    
    bool isRed(Node *node) {
        return colorOf(node) == m_red;
    }
    
    void afterAdd(Node *node) {
        Node *parent = node->m_parent;
        if (!parent) {
            black(node);
            return;
        }
        
        if (isBlack(parent)) return;
        Node *uncle = parent->sibling();
        Node *grand = parent->m_parent;
        if (isRed(uncle)) {
            black(parent);
            black(uncle);
            red(grand);
            afterAdd(grand);
            return;
        }
        
        if (parent->isLeftChild()) {
            if (node->isLeftChild()) {
                rotateRight(grand);
                red(node);
                black(parent);
                red(grand);
            } else {
                rotateLeft(parent);
                rotateRight(grand);
                black(node);
                red(grand);
            }
        } else {
            if (node->isLeftChild()) {
                rotateRight(parent);
                rotateLeft(grand);
                black(node);
                red(grand);
            } else {
                rotateLeft(grand);
                red(node);
                black(parent);
                red(grand);
            }
        }
    }
    
    void afterRemove(Node *node) {
        if (isRed(node)) {
            black(node);
            return;
        }
        
        Node *parent = node->m_parent;
        if (!parent) return;
        
        bool left = parent->m_left == nullptr || node->isLeftChild();
        Node *sibling = left ? parent->m_right : parent->m_left;
        if (!left) {
            if (isRed(sibling)) {
                rotateRight(parent);
                black(sibling);
                red(parent);
                sibling = parent->m_left;
            }

            if (isBlack(sibling->m_left) && isBlack(sibling->m_right)) {
                
                bool parentBlack = isBlack(parent);
                black(parent);
                red(sibling);
                if (parentBlack) {
                    afterRemove(parent);
                }
            } else {

                if (isBlack(sibling->m_left)) {
                    rotateLeft(sibling);
                    sibling = parent->m_left;
                }
                color(sibling, colorOf(parent));
                black(sibling->m_left);
                black(parent);
                rotateRight(parent);
            }
            
        } else {
            if (isRed(sibling)) {
                rotateLeft(parent);
                black(sibling);
                red(parent);
                sibling = parent->m_right;
            }
            if (isBlack(sibling->m_left) && isBlack(sibling->m_right)) {
                
                bool parentBlack = isBlack(parent);
                black(parent);
                red(sibling);
                if (parentBlack) {
                    afterRemove(parent);
                }
            } else {
                if (isBlack(sibling->m_right)) {
                    rotateRight(sibling);
                    sibling = parent->m_right;
                }
                color(sibling, colorOf(parent));
                black(sibling->m_right);
                black(parent);
                rotateLeft(parent);
            }
        }
        
    }
    
    template<typename Functor>
    void traversal(Node *node , Functor functor) {
        static bool stop = false;
        if (!node || stop) return;
        traversal(node->m_left, functor);
        if (functor(node->m_key, node->m_value)) { stop = true;}
        traversal(node->m_right, functor);
    }
    
public:
    TreeMap();
    ~TreeMap();
    int size();
    bool isEmpty();
    void clear();
    void put(K key,V value);
    V get(K key);
    void remove(K key);
    bool containsKey(K key);
    bool containsValue(V vlaue);
    template<typename Functor>
    void traversal(Functor functor) {// bool func(int,int) -> [](K key,V value)->bool{}
        if (!m_root) return;
        traversal(m_root, functor);
        
    }
};

template <typename K, typename V>
TreeMap<K,V>::TreeMap() {
    
}

template <typename K, typename V>
TreeMap<K,V>::~TreeMap() {
    
}

template <typename K, typename V>
int TreeMap<K,V>::size() {
    return m_size;
}
template <typename K, typename V>
bool TreeMap<K,V>::isEmpty() {
    return m_size;
}
template <typename K, typename V>
void TreeMap<K,V>::clear() {
    m_size = 0;
    delete m_root;
    m_root = nullptr;
}
template <typename K, typename V>
void TreeMap<K,V>::remove(K key) {
    remove(node(key));
}
template <typename K, typename V>
void TreeMap<K,V>::put(K key, V value) {
    elementNodeNullCheck(key);
    if (m_root == NULL) {
        //添加的是第一个元素,创建根节点
        m_root = new Node(key,value,NULL);
        m_size++;
        afterAdd(m_root);
        return;
    }
    //添加的不是第一个节点
    Node *node = m_root;
    Node *parent = m_root;
    //循环找到当前节点的父节点
    while (node != NULL) {
        
        parent = node;
        if (key > node->m_key) {
            node = node->m_right;
            
        } else if (key < node->m_key) {
            node = node->m_left;
        } else { //更新value
            node->m_value = value;
            return;
        }
    }
    //
    Node *newNode = new Node(key,value,parent);
    if (key > parent->m_key) {//添加到右边
        parent->m_right = newNode;
    } else if (key < parent->m_key) {//添加到左边
        parent->m_left = newNode;
    }
    m_size++;
    afterAdd(newNode);
}

template <typename K, typename V>
V TreeMap<K,V>::get(K key) {
     Node *findNode = node(key);
    return findNode ? findNode->m_value : NULL;
}
template <typename K, typename V>
bool TreeMap<K,V>::containsKey(K key) {
    return node(key) != nullptr;
}

template <typename K, typename V>
bool TreeMap<K,V>::containsValue(V value) {
    //遍历整棵树
    if (!m_root) return false;
    std::queue<Node *> q;
    q.push(m_root);
    while (!q.empty()) {
        Node *node = q.front();
        q.pop();
        if (node->m_value == value) {
            return true;
        }
        if (node->m_left) q.push(node->m_left);
        if (node->m_right) q.push(node->m_right);
    }
    return false;
}
#endif /* TreeMap_hpp */
