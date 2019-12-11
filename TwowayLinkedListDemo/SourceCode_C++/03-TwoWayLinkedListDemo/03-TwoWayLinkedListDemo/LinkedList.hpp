//
//  LinkedList.hpp
//  03-TwoWayLinkedListDemo
//
//  Created by bald on 2019/12/7.
//  Copyright © 2019 bald. All rights reserved.
//

#ifndef LinkedList_hpp
#define LinkedList_hpp

#include <stdio.h>
static const int ELEMENT_NOT_FOUND = -1;
using namespace std;
template <typename E>
class LinkedList{
    class Node{
    public:
        Node *m_next;
        Node *m_prev;
        E m_element;
        Node():Node(NULL,NULL,NULL){};
        Node(E element,Node *next,Node *prev):m_element(element),m_next(next),m_prev(prev) {}
        ~Node(){
            cout << "删除了一个元素:该元素的值为 " << m_element << endl;
        }
    };
    
    
public:
    LinkedList();
    ~LinkedList();
    //打印所有元素
    void toString();
    //清楚所有元素
    void clear();
    //获取元素的数量
    int size();
    //判断当前连表是否为空
    bool isEmpty();
    //判断是否包含某个元素
    bool contains(E element);
    //添加元素到尾部
    void add(E element);
    //获取index位置的元素
    E get(int index);
    //重新设置index位置的元素
    E set(int index, E element);
    //在index位置插入一个元素
    void add(int index, E element);
    //删除index位置的元素
    E remove(int index);
    //获取元素的索引
    int indexOf(E element);
private:
    int m_size;
    Node *m_first;
    Node *m_last;
    void outOfBounds();
    void rangeForCheck(int index);
    void rangeCheckForAdd(int index);
    //通过索引获取节点
    Node *node(int index){
        rangeForCheck(index);
        Node *node;
        if (index <= (m_size >> 1)) {
            //说明查找的是链表的左半部分,从左边往右边开始找
            node = m_first;
            for (int i = 0; i < index; i++) {
                node = node->m_next;
            }
        } else {
            //右半部分,从右边往左边开始找
            node = m_last;
            for (int i = m_size - 1; i > index; i--) {
                node = node->m_prev;
            }
        }
        return node;
    }
    
};

template <typename E>
LinkedList<E>::LinkedList() {
    
}
template <typename E>
LinkedList<E>::~LinkedList<E>() {
    
}

template <typename E>
void LinkedList<E>::add(E element) {
    add(m_size, element);
}

template <typename E>
void LinkedList<E>::add(int index, E element) {
    rangeCheckForAdd(index);
    if (index == m_size) {
        //在最尾部的位置添加元素
        Node *oldLast = m_last;
        m_last = new Node(element, NULL, oldLast);//创建一个新的节点
        if (oldLast == NULL) {//说明没有节点
            m_first = m_last;
        } else {
            oldLast->m_next = m_last;
        }
    } else {
        Node *nextNode = node(index);
        Node *prevNode = nextNode->m_prev;
        Node *node = new Node(element, nextNode, prevNode);
        nextNode->m_prev = node;
        if (prevNode == NULL) {//说明没有头节点
            m_first = node;
        } else {
            prevNode->m_next = node;
        }
    }
    m_size++;
}

template <typename E>
void LinkedList<E>::clear() {
    while (m_size > 0) {
        remove(m_size - 1);
    }
}

template <typename E>
E LinkedList<E>::remove(int index) {
    rangeForCheck(index);
    Node *removeNode = node(index);
    E removeElement = removeNode->m_element;
    Node *prevNode = removeNode->m_prev;
    Node *nextNode = removeNode->m_next;
    if (prevNode == NULL) {//删除的是头节点
        m_first = nextNode;
    } else {
        //被删除节点的上一个节点的next 指向被删除节点的下一个
        prevNode->m_next = nextNode;
    }
    
    if (nextNode == NULL) {//删除的是尾节点
        m_last = prevNode;
    } else {
        //被删除节点的下一个节点的prev 指向被删除节点的上一个
        nextNode->m_prev = prevNode;
    }
    m_size--;
    delete removeNode;
    return removeElement;
    
}
template <typename E>
E LinkedList<E>::get(int index){
    return node(index)->m_element;
}

template <typename E>
int LinkedList<E>::size() {
    return m_size;
}

template <typename E>
bool LinkedList<E>::isEmpty() {
    return m_size == 0;
}

template <typename E>
bool LinkedList<E>::contains(E element) {
    return indexOf(element) != ELEMENT_NOT_FOUND;
}

template <typename E>
int LinkedList<E>::indexOf(E element) {
    Node *node = m_first;
    for (int i = 0 ; i < m_size; i++) {
        if (node->m_element == element) {
            return i;
        }
        node = node->m_next;
    }
    return ELEMENT_NOT_FOUND;
}

template <typename E>
E LinkedList<E>::set(int index, E element) {
    Node *n = node(index);
    E oldElement = n->m_element;
    n->m_element = element;
    return oldElement;
}

template <typename E>
void LinkedList<E>::outOfBounds() {
    throw "index out of bounds";
}

template <typename E>
void LinkedList<E>::rangeForCheck(int index) {
    if (index < 0 || index >= m_size) {
        outOfBounds();
    }
}

template <typename E>
void LinkedList<E>::rangeCheckForAdd(int index) {
    if (index < 0 || index > m_size) {
        outOfBounds();
    }
}

//打印元素
template <typename E>
void LinkedList<E>::toString() {
    std::cout << "[";
    for (int i = 0; i<m_size;i++) {
        if (i != 0) {
            cout << ",";
        }
        
        Node *n = node(i);
        Node *prev = n->m_prev;
        Node *next = n->m_next;
        if (prev == NULL) {
            cout<< "NULL";
        } else {
            cout<< prev->m_element;
        }
        cout << "_";
        cout << n->m_element << "_";
        if (next == NULL) {
            cout << "NULL";
        } else {
           cout << next->m_element;
        }
        
    }
    cout<<"]"<<endl;
}
#endif /* LinkedList_hpp */
