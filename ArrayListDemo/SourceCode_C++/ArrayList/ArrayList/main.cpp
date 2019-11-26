//
//  main.cpp
//  ArrayList
//
//  Created by bald on 2019/11/17.
//  Copyright © 2019 bald. All rights reserved.
//

#include <iostream>
#include "ArrayList.hpp"
using namespace std;
int main(int argc, const char * argv[]) {
    // insert code here...

    {
        ArrayList<int> array;
        array.add(1);
        array.add(2);
        array.add(3);
        array.add(4);
        array.add(0, 0);
        array.remove(0);
        array.remove(array.size() - 1);
        cout << "当前数组元素个数:" << array.size() << endl;
        
        if (array.get(0) != 1) {
            throw "出错了";
        }
        for (int i = 0 ; i < 100; i++) {
            array.add(i);
        }
        array.display();
        cout << "当前数组元素个数:" << array.size() << endl;
//        cout << array << endl;
    }
    return 0;
}
