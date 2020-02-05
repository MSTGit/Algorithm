//
//  PriorityQueue.hpp
//  PriorityQueue
//
//  Created by ducktobey on 7/2/2020.
//  Copyright © 2020 ducktobey. All rights reserved.
//

#ifndef PriorityQueue_hpp
#define PriorityQueue_hpp

#include <stdio.h>
#include "Heap/BinaryHeap.hpp"
template <typename T>
class PriorityQueue {
    BinaryHeap<T> heap;
public:
    int size();
    bool isEmpty();
    void enQueue(T elememt);
    T deQueue();
    T front();
    void clear();
};
template <typename T>
int PriorityQueue<T>::size() {
    return heap.size();
}
template <typename T>
bool PriorityQueue<T>::isEmpty() {
    return heap.isEmpty();
}
template <typename T>
void PriorityQueue<T>::enQueue(T elememt) {
    heap.add(elememt);
}
template <typename T>
T PriorityQueue<T>::front() {
    return heap.get();
}
template <typename T>
T PriorityQueue<T>::deQueue() {
    return heap.remove();
}
template <typename T>
void PriorityQueue<T>::clear() {
    return heap.clear();
}
#endif /* PriorityQueue_hpp */
