//
//  main.cpp
//  AVLTree
//
//  Created by ducktobey on 2/2/2020.
//  Copyright © 2020 ducktobey. All rights reserved.
//

#include <iostream>
#include "AVLTree.hpp"
int main(int argc, const char * argv[]) {
    // insert code here...
    std::cout << "Hello, World!\n";
    AVLTree<int> bt;
    bt.add(5);
    bt.add(7);
    bt.add(6);
    bt.add(8);
    bt.add(9);
    bt.add(3);
    bt.add(1);
    bt.add(4);
    
    bt.remove(9);
    bt.remove(8);
    bt.remove(7);
    bt.remove(6);
//    bt.Dump();
    return 0;
}
