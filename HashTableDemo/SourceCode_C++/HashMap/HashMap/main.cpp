//
//  main.cpp
//  HashMap
//
//  Created by ducktobey on 6/2/2020.
//  Copyright © 2020 ducktobey. All rights reserved.
//

#include <iostream>
#include "HashMap/HashMap.hpp"
#include "TimeTool/TimeTool.hpp"
#include "LinkedHashMap/LinkedHashMap.hpp"
using namespace std;
int main(int argc, const char * argv[]) {
    // insert code here...
    std::cout << "Hello, World!\n";
    LinkedHashMap<int, int> lhm;
    int count = 10000;
    TimeTool::test("LinkedHashMap", [&lhm,count](void)->void{
        for (int i = 0; i < count; i++) {
            lhm.put(i, i);
            
        }
        for (int i = 0; i < count; i++) {
            lhm.containsValue(i);
        }
        for (int i = 0; i < count; i++) {
            lhm.remove(i);
        }
    });
    HashMap<int , int> *pointHM = new HashMap<int, int>;
    HashMap<int, int> hm;
    TimeTool::test("HashMap", [&hm,count](void)->void{
        for (int i = 0; i < count; i++) {
            hm.put(i, i);
            
        }
        for (int i = 0; i < count; i++) {
            hm.containsValue(i);
        }
        for (int i = 0; i < count; i++) {
            hm.remove(i);
        }
    });
    for (int i = 0 ; i < 20; i ++) {
        hm.put(i, i);
    }
    hm.traversal([](int key, int value)->bool {
        cout << key << "_" << value << endl;
        return false;
    });
    return 0;
}
