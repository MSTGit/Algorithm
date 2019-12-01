//
//  main.cpp
//  02-LinkedListDemo
//
//  Created by bald on 2019/11/30.
//  Copyright © 2019 bald. All rights reserved.
//

#include <iostream>
#include "LinkedList.hpp"
using namespace std;
int main(int argc, const char * argv[]) {
    // insert code here...
    std::cout << "Hello, World!\n";
    {
        LinkedList<int> list;
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.toString();
        cout << list.contains(1) << endl;
        cout << list.contains(3) << endl;
        cout << list.size() << endl;
        cout << list.get(0) << endl;
        cout << list.get(1) << endl;
        cout << list.get(2) << endl;
        cout << list.get(3) << endl;
        
        list.remove(0);
        list.remove(0);
        list.remove(0);
        list.remove(0);
        
        list.add(1);
        list.clear();
    }
    return 0;
}
