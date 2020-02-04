//
//  TreeSet.hpp
//  Map
//
//  Created by ducktobey on 5/2/2020.
//  Copyright © 2020 ducktobey. All rights reserved.
//

#ifndef TreeSet_hpp
#define TreeSet_hpp

#include <stdio.h>
#include "../TreeMap/TreeMap.hpp"
template <typename  E>
class TreeSet {
    TreeMap<E, nullptr_t> m_tm;
public:
public:
    int size();
    bool isEmpty();
    void clear();
    bool contain(E element);
    void add(E element);
    void remove(E element);
    template<typename Functor>
    void traversal(Functor functor) {
        m_tm.traversal([functor](E element,nullptr_t t) ->bool {
            if (functor(element)) {
                return true;
            }
            return false;
        });
    }
};

template <typename E>
int TreeSet<E>::size() {
    return m_tm.size();
}
template <typename E>
bool TreeSet<E>::isEmpty() {
    return m_tm.size();
}
template <typename E>
void TreeSet<E>::clear() {
    m_tm.clear();
}
template <typename E>
bool TreeSet<E>::contain(E element) {
    return m_tm.containsKey(element);
}
template <typename E>
void TreeSet<E>::add(E element) {
    
    m_tm.put(element, nullptr);
}
template <typename E>
void TreeSet<E>::remove(E element) {
    m_tm.remove(element);
}
#endif /* TreeSet_hpp */
