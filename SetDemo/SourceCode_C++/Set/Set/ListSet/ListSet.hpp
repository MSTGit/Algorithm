//
//  ListSet.hpp
//  Set
//
//  Created by ducktobey on 5/2/2020.
//  Copyright © 2020 ducktobey. All rights reserved.
//

#ifndef ListSet_hpp
#define ListSet_hpp
/*
 集合底层通过链表实现
 */
#include <stdio.h>
#include "LinkedList.hpp"
template <typename E>
class ListSet {
    LinkedList<E> *m_list = new LinkedList<E>;
public:
    int size();
    bool isEmpty();
    void clear();
    bool contain(E element);
    void add(E element);
    void remove(E element);
    void traversal(bool (*func)(E));
    void toString();
    ~ListSet() {
        delete m_list;
    }
};
template <typename E>
int ListSet<E>::size() {
    return m_list->size();
}
template <typename E>
bool ListSet<E>::isEmpty() {
    return m_list->size();
}
template <typename E>
void ListSet<E>::clear() {
    m_list->clear();
}
template <typename E>
bool ListSet<E>::contain(E element) {
    return m_list->contains(element);
}
template <typename E>
void ListSet<E>::add(E element) {
    int index = m_list->indexOf(element);
    if (index != ELEMENT_NOT_FOUND) {
        m_list->set(index, element);
    } else {
        m_list->add(element);
    }
}
template <typename E>
void ListSet<E>::remove(E element) {
    int index = m_list->indexOf(element);
    if (index != ELEMENT_NOT_FOUND) {
        m_list->remove(index);
    }
}
template <typename E>
void ListSet<E>::traversal(bool (*func)(E)) {
    if (!func) return;
    int size = m_list->size();
    for (int i = 0; i < size; i++) {
        if (func(m_list->get(i))) {
            return;
        }
    }
}
template <typename E>
void ListSet<E>::toString() {
    m_list->toString();
}
#endif /* ListSet_hpp */
