//
//  main.cpp
//  Map
//
//  Created by ducktobey on 5/2/2020.
//  Copyright © 2020 ducktobey. All rights reserved.
//

#include <iostream>
#include "TreeMap/TreeMap.hpp"
#include "TimeTool/TimeTool.hpp"
#include "TreeSet/TreeSet.hpp"
#include <array>
int main(int argc, const char * argv[]) {
    // insert code here...
    std::cout << "Hello, World!\n";
//    TreeSet<int> ts;
//    ts.add(1);
//    ts.add(2);
//    ts.add(3);
//    ts.add(4);
//    ts.add(5);
//    ts.add(6);
//    ts.add(7);
//    ts.add(8);
//    ts.traversal([](int element) -> bool{
//        cout << element << endl;
//        return false;
//    });
//    return 0;
    TreeMap<int ,int> tm;
    int count = 10000;
    //生成10W个100以内的随机函数
    int nums[10000];
    srand((int)time(0));  // 产生随机种子  把0换成NULL也行
    for (int i = 0 ; i < count; i++) {
        nums[i] = rand()%100;
    }
    TimeTool::test("TreeMap", [&tm,count,nums](void)->void{
        for (int i = 0; i < count; i++) {
            int num = nums[i];
            int numCount = tm.get(num);
            tm.put(num, ++numCount);
        }
    });
//    tm.put(1, 2);
//    tm.put(2, 3);
//    tm.put(3, 4);
//    tm.put(4, 5);
//    tm.put(5, 6);
    
    tm.traversal([&count](int key, int value)->bool {
        std::cout << key << "_" << value << std::endl;
        count -= value;
//        if (key == 4) {
//            return true;
//        }
        return false;
    });
    std::cout << "当前剩余 : " << count << endl;
//    bool containsKey = tm.containsKey(1);
//    bool containsKey1 = tm.containsKey(2);
//    bool containsValue = tm.containsValue(1);
//    bool containsValue1 = tm.containsValue(2);
//    tm.put(1 ,3);
//    int value = tm.get(1);
    return 0;
}
