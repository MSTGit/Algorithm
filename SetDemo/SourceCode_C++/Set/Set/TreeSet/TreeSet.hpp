//
//  HashSet.hpp
//  Set
//
//  Created by ducktobey on 5/2/2020.
//  Copyright © 2020 ducktobey. All rights reserved.
//

#ifndef HashSet_hpp
#define HashSet_hpp
/*
 集合底层通过红黑树实现
 */
#include <stdio.h>
#include "RedBlackTree.hpp"
template <typename  E>
class TreeSet {
    RedBlackTree<E> *m_tree = new RedBlackTree<E>;
public:
    int size();
    bool isEmpty();
    void clear();
    bool contain(E element);
    void add(E element);
    void remove(E element);
    template<typename Functor>
    void traversal(Functor functor) {
        m_tree->inorder([functor](E element) ->bool {
            if (functor(element)) {
                return true;
            }
            return false;
        });
    }
    void toString();
    ~TreeSet() {
        delete m_tree;
    }
};
template <typename E>
int TreeSet<E>::size() {
    return m_tree->size();
}
template <typename E>
bool TreeSet<E>::isEmpty() {
    return m_tree->size();
}
template <typename E>
void TreeSet<E>::clear() {
    m_tree->clear();
}
template <typename E>
bool TreeSet<E>::contain(E element) {
    return m_tree->contains(element);
}
template <typename E>
void TreeSet<E>::add(E element) {

    m_tree->add(element);
}
template <typename E>
void TreeSet<E>::remove(E element) {
    m_tree->remove(element);
}

template <typename E>
void TreeSet<E>::toString() {
    m_tree->Dump();
}

#endif /* HashSet_hpp */
