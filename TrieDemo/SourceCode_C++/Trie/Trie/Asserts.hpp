//
//  Asserts.hpp
//  Trie
//
//  Created by ducktobey on 8/2/2020.
//  Copyright © 2020 ducktobey. All rights reserved.
//

#ifndef Asserts_hpp
#define Asserts_hpp

#include <stdio.h>
class Asserts {
public:
    static void test(bool flag) {
        if (!flag) throw "测试不通过";
    }
};
#endif /* Asserts_hpp */
