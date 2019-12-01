//
//  LinkedList.hpp
//  02-LinkedListDemo
//
//  Created by bald on 2019/11/30.
//  Copyright © 2019 bald. All rights reserved.
//

#ifndef LinkedList_hpp
#define LinkedList_hpp

#include <stdio.h>
static const int ELEMENT_NOT_FOUND = -1;
using namespace std;
template <typename E>
class LinkedList {
    class Node {
    public:
        E m_element;
        Node *m_next;
        Node():Node(NULL,NULL){};
        Node(E element, Node *next):m_element(element),m_next(next) {
            
        }
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
    Node *m_head;
    int m_size;
    void outOfBounds();
    void rangeForCheck(int index);
    void rangeCheckForAdd(int index);
    Node *node(int index) {
        rangeForCheck(index);
        Node *node = m_head;
        for (int i = 0; i < index; i++) {
            node = node->m_next;
        }
        return node;
    }
};
template <typename E>
LinkedList<E>::LinkedList() {
    
}
template <typename E>
LinkedList<E>::~LinkedList() {
    cout << "LinkedList::~LinkedList()" << endl;
}
template <typename E>
void LinkedList<E>::clear() {
//    m_size = 0;
//    Node *node = m_head;
//    while (node != NULL) {
//        Node *tempNode = node;
//        node = node->m_next;
//        delete tempNode;
//    }
    while (m_size > 0) {
        remove(m_size - 1);
    }
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
void LinkedList<E>::add(E element) {
    add(m_size, element);
}
template <typename E>
E LinkedList<E>::get(int index) {
    return node(index)->m_element;
}
template <typename E>
E LinkedList<E>::set(int index, E element) {
    
    return NULL;
}

template <typename E>
void LinkedList<E>::add(int index, E element) {
    rangeCheckForAdd(index);
    if (index == 0) {
        //添加的是第一个元素
        m_head = new LinkedList::Node(element,NULL);
    } else{
        Node *prevNode = node(index - 1);
        Node *node = new LinkedList::Node(element, NULL);
        prevNode->m_next = node;
    }
    m_size++;
}
template <typename E>
E LinkedList<E>::remove(int index) {
    //如果删除的是头节点
    Node *node = m_head;
    E element;
    if (index == 0) {
        element = m_head->m_element;
        delete m_head;
        m_head = m_head->m_next;
    } else {
        //不是头节点,则取到index节点的上一个节点
        Node *prevNode = LinkedList::node(index - 1);
        //通过上一个节点获取到当前节点
        node = prevNode->m_next;
        //将上一个节点的next指向当前节点的next
        prevNode->m_next = node->m_next;
        //保存当前节点中的元素
        element = node->m_element;
        //销毁当前节点申请的堆空间
        delete node;
    }
    m_size--;
    return element;
}
template <typename E>
int LinkedList<E>::indexOf(E element) {
    Node *node = m_head;
    for (int i = 0 ; i < m_size; i++) {
        if (node->m_element == element) return i;
        node = node->m_next;
    }
    return ELEMENT_NOT_FOUND;
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
    std::cout << "[ ";
    for (int i = 0; i<m_size;i++) {
        if (i != 0) {
            cout << ",";
        }
        E element = get(i);
        cout<<element;
    }
    cout<<" ]"<<endl;
}
#endif /* LinkedList_hpp */
