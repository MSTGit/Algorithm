//
//  main.cpp
//  Trie
//
//  Created by ducktobey on 8/2/2020.
//  Copyright © 2020 ducktobey. All rights reserved.
//

#include <iostream>
#include "Trie.hpp"
#include "HashMap/HashMap.hpp"
#include "Asserts.hpp"
int main(int argc, const char * argv[]) {
    // insert code here...
    std::cout << "Hello, World!\n";
    Trie<int> t;
    t.add("cat",1);
    t.add("dog",2);
    t.add("catalog",3);
    t.add("cast",4);
    t.add("小哥哥",5);
    Asserts::test(t.size() == 5);
    Asserts::test(t.startsWith("do"));
    Asserts::test(t.startsWith("c"));
    Asserts::test(t.startsWith("ca"));
    Asserts::test(t.startsWith("cat"));
    Asserts::test(t.startsWith("cata"));
    Asserts::test(!t.startsWith("hehe"));
    Asserts::test(t.get("小哥哥") == 5);
    Asserts::test(t.remove("cat") == 1);
    Asserts::test(t.remove("catalog") == 3);
    Asserts::test(t.remove("cast") == 4);
    Asserts::test(t.startsWith("小"));
    Asserts::test(t.startsWith("do"));
    Asserts::test(!t.startsWith("c"));
    return 0;
}
