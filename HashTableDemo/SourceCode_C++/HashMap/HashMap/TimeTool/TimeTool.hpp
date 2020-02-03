//
//  TimeTool.hpp
//  Set
//
//  Created by ducktobey on 5/2/2020.
//  Copyright © 2020 ducktobey. All rights reserved.
//

#ifndef TimeTool_hpp
#define TimeTool_hpp

#include <stdio.h>
#include <string>
#include <time.h>
#include <sys/time.h>
using namespace std;
class TimeTool {
    
    //获取毫秒
    static long getCurrentTime()
    {
        struct timeval tv;
        gettimeofday(&tv,NULL);
        return tv.tv_sec * 1000 + tv.tv_usec / 1000;
    }
    static string getTime()
    {
        time_t timep;
        time (&timep);
        char tmp[64];
        strftime(tmp, sizeof(tmp), "%Y-%m-%d %H:%M:%S",localtime(&timep) );
        return tmp;
    }
public:
    template<typename Functor>
   static void test(std::string title,Functor functor) {
       cout << title << endl;
       long begin = getCurrentTime();
       cout << "[开始时间] : " << getTime() << endl;
       functor();
       long end = getCurrentTime();
       cout << "[结束时间] : " << getTime() << endl;
       double delta = (end - begin) / 1000.0;
       cout << "耗时 : " << delta << " 秒" << endl;
    }
    

    
};
#endif /* TimeTool_hpp */
