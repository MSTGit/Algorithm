//
//  main.cpp
//  Set
//
//  Created by ducktobey on 5/2/2020.
//  Copyright © 2020 ducktobey. All rights reserved.
//

#include <iostream>
#include "ListSet/ListSet.hpp"
#include "TreeSet/TreeSet.hpp"
#include "TimeTool/TimeTool.hpp"
using namespace std;
int main(int argc, const char * argv[]) {
    // insert code here...
    std::cout << "Hello, World!\n";
    TreeSet<int> treeSet;
    ListSet<int> listSet;
    double count = 20000000;
    TimeTool::test("TreeSet", [&treeSet,count](void) -> void{
        for (int i = 0; i < count; i++) {
            treeSet.add(i);
        }
        for (int i = 0; i < count; i++) {
            treeSet.contain(i);
        }
        for (int i = 0; i < count; i++) {
            treeSet.remove(i);
        }
    });
    
    TimeTool::test("ListSet", [&listSet,count](void) -> void{
        for (int i = 0; i < count; i++) {
            listSet.add(i);
        }
        for (int i = 0; i < count; i++) {
            listSet.contain(i);
        }
        for (int i = 0; i < count; i++) {
            listSet.remove(i);
        }
    });
    
    treeSet.add(1);
    treeSet.add(2);
    treeSet.add(4);
    treeSet.add(3);
    treeSet.add(2);
    treeSet.add(1);
    //treeSet.toString();
    treeSet.traversal([](int element) -> bool{
        //cout << element << endl;
        if (element == 1) {

            return true;
        }
        return false;
    });
    return 0;
}

