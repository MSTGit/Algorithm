//
//  main.cpp
//  PriorityQueue
//
//  Created by ducktobey on 7/2/2020.
//  Copyright © 2020 ducktobey. All rights reserved.
//

#include <iostream>
#include "PriorityQueue.hpp"
int main(int argc, const char * argv[]) {
    // insert code here...
    std::cout << "Hello, World!\n";
    PriorityQueue<int> pq;

//    for (int i = 1; i < 40; i ++) {
//        pq.enQueue(i);
//    }
    pq.enQueue(4);
    pq.enQueue(1);
    pq.enQueue(10);
    
    
    int size = pq.size();
    for (int i = 0; i < size; i++) {
        std::cout << pq.deQueue() << std::endl;
    }
    return 0;
}
