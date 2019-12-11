//
//  main.cpp
//  03-TwoWayLinkedListDemo
//
//  Created by bald on 2019/12/7.
//  Copyright © 2019 bald. All rights reserved.
//

#include <iostream>
#include "LinkedList.hpp"
using namespace std;
int main(int argc, const char * argv[]) {
    // insert code here...
    std::cout << "Hello, World!\n";
    LinkedList<int> list;
    list.add(1);
    list.add(2);
    list.add(3);
    list.add(4);
    list.add(5);
    list.set(0, 5);
//    list.remove(2);
    list.toString();
    return 0;
}
