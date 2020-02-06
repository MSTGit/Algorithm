//
//  main.cpp
//  RedBlackTree
//
//  Created by ducktobey on 3/2/2020.
//  Copyright © 2020 ducktobey. All rights reserved.
//

#include <iostream>
#include "RedBlackTree.hpp"
int main(int argc, const char * argv[]) {
    // insert code here...
    std::cout << "Hello, World!\n";
    RedBlackTree<int> bt;
    bt.add(1);
    bt.add(2);
    bt.add(3);
    bt.add(4);
    bt.add(5);
    bt.add(6);
    bt.add(7);
    bt.add(8);
    bt.add(9);
    bt.add(10);
    bt.add(11);
    bt.add(12);
    bt.add(13);
    bt.add(14);
//    bt.add(5);
//    bt.add(6);
//    bt.add(7);
//    bt.add(8);
    bt.Dump();
    bt.remove(1);
    bt.Dump();
    bt.remove(2);
    bt.Dump();
    bt.remove(3);
    bt.Dump();
    bt.remove(4);
    bt.Dump();
    bt.remove(5);
    bt.Dump();
    bt.remove(6);
    bt.Dump();
    bt.remove(7);
    bt.Dump();
    bt.remove(8);
    bt.Dump();
    bt.remove(9);
    bt.Dump();
    bt.remove(10);
    bt.Dump();
    bt.remove(11);
    bt.Dump();
    bt.remove(12);
    bt.Dump();
    bt.remove(13);
    bt.Dump();
    bt.remove(14);
    bt.Dump();

    return 0;
}
