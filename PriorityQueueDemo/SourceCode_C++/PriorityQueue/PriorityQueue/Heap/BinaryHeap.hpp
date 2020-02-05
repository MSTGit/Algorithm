//
//  Heap.hpp
//  PriorityQueue
//
//  Created by ducktobey on 7/2/2020.
//  Copyright © 2020 ducktobey. All rights reserved.
//

#ifndef Heap_hpp
#define Heap_hpp

#include <stdio.h>
#include "../ArrayList/ArrayList.hpp"
template <typename T>
class BinaryHeap {
//    int m_size;
    ArrayList<T> *m_elements = new ArrayList<T>;
    void emptyCheck() {
        if (m_elements->isEmpty()) {
            throw "Heap is empty";
        }
    }
    void siftUp(int index) {
        T element = m_elements->get(index);
        while (index > 0) {
            int parentIndex = (index - 1) >> 1;
            T parent = m_elements->get(parentIndex);
            if (parent >= element) {//最大堆
                break;
            }
            m_elements->set(index, parent);
            index = parentIndex;
        }
        m_elements->set(index, element);
    }
    
    void siftDown(int index) {
        T element = m_elements->get(index);
        int half = m_elements->size() >> 1;//计算非叶子接地的数量
        while (index < half) {//判断当前节点是否有子节点
            //左子节点的索引
            int childIndex = (index << 1) + 1;
            T child = m_elements->get(childIndex);
            //右子节点是索引
            int rightIndex = childIndex + 1;
            T right = m_elements->get(rightIndex);
            if (rightIndex < m_elements->size() && right > child) {
                //如果右子节点的值,大于左子节点的值,就更新子节点的值,然后更新childIndex的值
                child = m_elements->get(childIndex = rightIndex);
            }
            if (element > child) {
                break;
            }
            m_elements->set(index, child);
            index = childIndex;
        }
        m_elements->set(index, element);
    }
public:
    bool isEmpty();
    void clear();
    void add(T element);
    int size();
    T get();
    T remove();
    T replace(T element);
};
template <typename T>
int BinaryHeap<T>::size() {
    return m_elements->size();
}
template <typename T>
bool BinaryHeap<T>::isEmpty() {
    return m_elements->isEmpty();;
}
template <typename T>
void BinaryHeap<T>::clear() {
    m_elements->clear();
}
template <typename T>
void BinaryHeap<T>::add(T element) {
    m_elements->add(element);
    siftUp(m_elements->size() - 1);
}
template <typename T>
T BinaryHeap<T>::get() {
    emptyCheck();
    return m_elements->get(0);
}
template <typename T>
T BinaryHeap<T>::remove(){
    emptyCheck();
    T root = m_elements->remove(0);
    int lastIndex = m_elements->size();
    m_elements->set(0, m_elements->get(lastIndex));
//    m_elements->set(lastIndex, NULL);
    siftDown(0);
    return root;
}
template <typename T>
T BinaryHeap<T>::replace(T element) {
    //将更节点替换为新的节点
    T root = nullptr;
    if (m_elements->size()) {//如果堆中有元素
        root = m_elements->get(0);//获取现在堆中最大的元素
        m_elements->set(0, element);//将更新的元素,放到第一位
        siftDown(0);//对第一位进行下滤操作
    } else {
        add(element);
    }
    return root;
}
#endif /* Heap_hpp */
