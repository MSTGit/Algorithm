//
//  LinkedHashMap.hpp
//  HashMap
//
//  Created by ducktobey on 7/2/2020.
//  Copyright © 2020 ducktobey. All rights reserved.
//

#ifndef LinkedHashMap_hpp
#define LinkedHashMap_hpp

#include <stdio.h>
using namespace std;
template <typename K, typename V>
class LinkedHashMap {
    public :class LinkedNode {
    public:
        K m_key;
        V m_value;
        LinkedNode *m_left = nullptr;//左子节点
        LinkedNode *m_right = nullptr;//右子节点
        LinkedNode *m_parent = nullptr;//父节点
        LinkedNode *m_prev = nullptr;
        LinkedNode *m_next = nullptr;
        bool m_color = false;//表示红色
        LinkedNode() : m_left(nullptr),m_right(nullptr) {}
        LinkedNode(K key, V value, LinkedNode *parent):m_key(key),m_value(value),m_parent(parent),m_left(nullptr),m_right(nullptr) {}
        ~LinkedNode() {
//            cout << "删除了一个元素 : " << m_value << endl;
//            if (m_left) delete m_left;
//            if (m_right) delete m_right;
        }
        bool isLeaf() { return m_left == NULL && m_right == NULL; }
        bool hasTwoChildren() { return m_left != NULL && m_right != NULL; }
        bool isLeftChild() { return m_parent && this == m_parent->m_left; }
        bool isRightChild() { return m_parent && this == m_parent->m_right; }
        LinkedNode *sibling() {
            if (isLeftChild()) { return m_parent->m_right; }
            if (isRightChild()) { return m_parent->m_left; }
            return nullptr;
        }
    };
protected:
    //内部成员变量
    int m_size = 0;
    bool m_red = false;
    bool m_black = true;
    LinkedNode *m_frist = nullptr;
    LinkedNode *m_last = nullptr;
//    static constexpr int DEFAULT_CAPACITY = 1 << 4;//哈希表的默认容量
    static constexpr float DEFAULT_LOAD_FACTOR = 0.75f;//哈希表装填因子
    ArrayList<LinkedNode *> *m_table = new ArrayList<LinkedNode *>(DEFAULT_CAPACITY);
    void elementNodeNullCheck(K element) {
//        if (element == NULL && element != 0) {
//            throw "key must not be null";
//        }
    }
    LinkedNode *node(K key){
        int hashIndex = index(key);
        LinkedNode *node = m_table->get(hashIndex); //[hashIndex];
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
    
    LinkedNode * predecessor(LinkedNode *node){//找前驱节点
        if (!node) return nullptr;
        LinkedNode *p = node->m_left;
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
    LinkedNode * successor(LinkedNode *node){//找后继节点<与找前驱节点相反>
        if (!node) return nullptr;
        LinkedNode *p = node->m_right;
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
    
    void remove(LinkedNode *node){
        if (!node) return;
        m_size--;
        if (node->hasTwoChildren()) {
            LinkedNode *s = successor(node);
            node->m_key = s->m_key;
            node = s;
        }
        LinkedNode *replacement = node->m_left ? node->m_left : node->m_right;
        LinkedNode *root = m_table->get(index(node->m_key));
        if (replacement) {
            replacement->m_parent = node->m_parent;
            if (!node->m_parent) {
                root->m_right = nullptr;
                root->m_left = nullptr;
                delete root;
                root = replacement;
                m_table->set(index(node->m_key), root);
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
        } else if (node == root) {
            m_table->set(index(node->m_key), nullptr);
            delete root;
            root = nullptr;
            afterRemove(node);
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
    
    void rotateLeft(LinkedNode *grand) {
        LinkedNode *parent = grand->m_right;
        LinkedNode *node = parent->m_left;
        grand->m_right = parent->m_left;
        parent->m_left = grand;
        afterRotate(grand, parent, node);
    }
    void rotateRight(LinkedNode *grand) {
        LinkedNode *parent = grand->m_left;
        LinkedNode *node = parent->m_right;
        grand->m_left = node;
        parent->m_right = grand;
        afterRotate(grand, parent, node);
    }
    
    void afterRotate(LinkedNode *grand, LinkedNode *parent, LinkedNode *child) {
        parent->m_parent = grand->m_parent;
        if (grand->isLeftChild()) {
            grand->m_parent->m_left = parent;
        } else if(grand->isRightChild()){
            grand->m_parent->m_right = parent;
        } else {
            m_table->set(index(grand->m_key), parent);
        }
        if (child) child->m_parent = grand;
        grand->m_parent = parent;
    }
    
    LinkedNode *color(LinkedNode *node,bool color) {
        if (!node) return node;
        node->m_color = color;
        return node;
    }
    
    LinkedNode *red(LinkedNode *node) {
        node->m_color = m_red;
        return node;
    }
    
    LinkedNode *black(LinkedNode *node) {
        node->m_color = m_black;
        return node;
    }
    
    bool colorOf(LinkedNode *node) {
        return node ? node->m_color : m_black;
    }
    
    bool isBlack(LinkedNode *node) {
        return colorOf(node) == m_black;
    }
    
    bool isRed(LinkedNode *node) {
        return colorOf(node) == m_red;
    }
    
    void afterPut(LinkedNode *node) {
        LinkedNode *parent = node->m_parent;
        if (!parent) {
            black(node);
            return;
        }
        
        if (isBlack(parent)) return;
        LinkedNode *uncle = parent->sibling();
        LinkedNode *grand = parent->m_parent;
        if (isRed(uncle)) {
            black(parent);
            black(uncle);
            red(grand);
            afterPut(grand);
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
    
    void afterRemove(LinkedNode *node) {
        if (isRed(node)) {
            black(node);
            return;
        }
        
        LinkedNode *parent = node->m_parent;
        if (!parent) return;
        
        bool left = parent->m_left == nullptr || node->isLeftChild();
        LinkedNode *sibling = left ? parent->m_right : parent->m_left;
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
    
    //通过key计算哈希索引
    int index(K key) {
        return (int)(hashWithKey(key) & (m_table->size() - 1));
    }
    
    //通过扰动计算,对key计算哈希值
    long hashWithKey(K key) {
        std::hash<K> hash;
        return hash.operator()(key);
    }
    
    //哈希表扩容
    void resize(){
        //如果当前的比例小于装填因子,就不做任何处理
        if ((m_size * 1.0) / m_table->size() <= DEFAULT_LOAD_FACTOR) return;
        //扩容,容量变为原来的两倍
        auto oldValue = m_table;
        int newSize = oldValue->size() << 1;
        //cout << "扩容 : 旧容量 = " << oldValue->size() << " 新容量 = " << newSize << endl;
        m_table = new ArrayList<LinkedNode *>(newSize);
        //将数组中的每一个位置赋空
        for (int i = 0; i < newSize; i++) {
            m_table->add(nullptr);
        }
        //将原来的元素,重新赋值给新的表
        queue<LinkedNode *> q;
        for (int i = 0; i < oldValue->size(); i++) {
            LinkedNode *root = oldValue->get(i);
            if (!root) continue;
            q.push(root);
            while (!q.empty()) {
                LinkedNode *node = q.front();
                q.pop();
                put(node->m_key, node->m_value);//将元素的k-v重新保存到新的哈希表中
                if (node->m_left) q.push(node->m_left);
                if (node->m_right) q.push(node->m_right);
            }
        }
        delete oldValue;
    }

    LinkedNode *createNode(K key,V value, LinkedNode *parent) {
        LinkedNode *node = new LinkedNode(key,value,parent);
        if (!m_frist) {
            m_frist = m_last = node;
        } else {
            m_last->m_next = node;
            node->m_prev = m_last;
            m_last = node;
        }
        return node;
    }
    
public:
    LinkedHashMap();
    ~LinkedHashMap();
    int size();
    bool isEmpty();
    virtual void clear();
    void put(K key,V value);
    V get(K key);
    void remove(K key);
    bool containsKey(K key);
    virtual bool containsValue(V vlaue);
    template<typename Functor>
    void traversal(Functor functor) {// bool func(int,int) -> [](K key,V value)->bool{}
        if (!m_size) return;
        std::queue<LinkedNode *> q;
        for (int i = 0; i < m_table->size(); i++) {
            LinkedNode *node = m_table->get(i);
            if (!node) continue;//如果当前位置的节点为空,就跳过当前节点,继续下一个
            q.push(node);
            while (!q.empty()) {
                node = q.front();
                q.pop();
                if (functor(node->m_key,node->m_value))return;
                if (node->m_left) q.push(node->m_left);
                if (node->m_right) q.push(node->m_right);
            }
        }
    }
};

template <typename K, typename V>
LinkedHashMap<K,V>::LinkedHashMap() {
    for (int i = 0; i < DEFAULT_CAPACITY; i++) {
        m_table->add(nullptr);
    }
    
}

template <typename K, typename V>
LinkedHashMap<K,V>::~LinkedHashMap() {
    if (m_table) delete m_table;
//    if (m_last) delete m_last;
//    if (m_frist) delete m_frist;
    m_frist = nullptr;
    m_last = nullptr;
    m_table = nullptr;
}

template <typename K, typename V>
int LinkedHashMap<K,V>::size() {
    return m_size;
}
template <typename K, typename V>
bool LinkedHashMap<K,V>::isEmpty() {
    return m_size;
}
template <typename K, typename V>
void LinkedHashMap<K,V>::clear() {
    m_size = 0;
    for (int i = 0; i < m_table->size(); i++) {
        LinkedNode *node = m_table->get(i);
        if (!node) continue;
        delete node;
        node = nullptr;
    }
    if (m_last) delete m_last;
    if (m_frist) delete m_frist;
    m_frist = nullptr;
    m_last = nullptr;
}
template <typename K, typename V>
void LinkedHashMap<K,V>::remove(K key) {
    remove(node(key));
}
template <typename K, typename V>
void LinkedHashMap<K,V>::put(K key, V value) {
    elementNodeNullCheck(key);
    resize();
    //通过key计算哈希表的缩影
    int hashIndex = index(key);
    LinkedNode *root = m_table->get(hashIndex);
    if (root == NULL) {
        //添加的是第一个元素,创建根节点
        root = this->createNode(key, value, NULL);//new Node(key,value,NULL);
        m_table->set(hashIndex, root);
        m_size++;
        afterPut(root);
        return;
    }
    //添加的不是第一个节点
    LinkedNode *node = root;
    LinkedNode *parent = root;
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
    LinkedNode *newNode = createNode(key,value,parent);
    if (key > parent->m_key) {//添加到右边
        parent->m_right = newNode;
    } else if (key < parent->m_key) {//添加到左边
        parent->m_left = newNode;
    }
    m_size++;
    afterPut(newNode);
}

template <typename K, typename V>
V LinkedHashMap<K,V>::get(K key) {
    LinkedNode *findNode = node(key);
    return findNode ? findNode->m_value : NULL;
}
template <typename K, typename V>
bool LinkedHashMap<K,V>::containsKey(K key) {
    return node(key) != nullptr;
}

template <typename K, typename V>
bool LinkedHashMap<K,V>::containsValue(V value) {
    LinkedNode *node = m_frist;
    while (node) {
        if (value == node->m_value) {
            return true;
        }
        node = node->m_next;
    }
    return false;
}





#endif /* LinkedHashMap_hpp */
