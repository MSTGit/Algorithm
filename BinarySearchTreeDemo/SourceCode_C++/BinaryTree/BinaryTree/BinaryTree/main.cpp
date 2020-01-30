//
//  main.cpp
//  BinaryTree
//
//  Created by ducktobey on 29/1/2020.
//  Copyright © 2020 ducktobey. All rights reserved.
//

#include <iostream>
#include "BinarySearchTree.hpp"
#include <vector>
#include <string>
#include <sstream>
#include <algorithm>
#include <random>
int main(int argc, const char * argv[]) {
    // insert code here...
    std::cout << "Hello, World!\n";
    {
        BinarySearchTree<int> bt;
        std::random_device rd;
        std::mt19937 rng(rd());
//        for (int i = 0; i < 10; i ++) {
//            const int Min=0, Max=100;
//
//            std::uniform_int_distribution<int> dist(Min,Max);
//            bt.add(dist(rng));
//        }

        bt.add(40);
        bt.add(50);
        bt.isComplete();
        for (int i = 0; i < 1; i++) {
            bt.add(4);
            bt.add(7);
            bt.add(8);
            bt.add(6);
            bt.add(5);
            bt.add(2);
            bt.add(1);
            bt.add(3);
            bt.add(9);
            bt.Dump();
//            bt.clear();
            //bt.preorder();
            bt.postorder();
//            for (int i = 1; i < 10; i++) {
//                bt.remove(i);
//                bt.Dump();
//                //std::cout << "---------------\n";
//            }
        }
//        bt.add(4);
//        bt.Dump();
//        bt.add(7);
//        bt.Dump();
//        bt.add(8);
//        bt.Dump();
//        bt.add(6);
//        bt.Dump();
//        bt.add(5);
//        bt.Dump();
//        bt.add(2);
//        bt.Dump();
//        bt.add(1);
//        bt.Dump();
//        bt.add(3);
//        bt.Dump();
//        bt.add(9);
//        bt.Dump();

    }
    return 0;
}
