package com.xd;

import com.xd.BruteForce.BruteForce02;
import com.xd.KMP.KMP;

public class SequenceDemo {
    public static void main(String[] args) {
        testKMP();
    }

    static void testBF(){
        Asserts.test(BruteForce02.indexOf("Hello World" ,"H") == 0);
        Asserts.test(BruteForce02.indexOf("Hello World" ,"d") == 10);
        Asserts.test(BruteForce02.indexOf("Hello World" ,"or") == 7);
        Asserts.test(BruteForce02.indexOf("Hello World" ,"abc") == -1);
    }

    static void testKMP() {
        Asserts.test(KMP.indexOf("Hello World" ,"H") == 0);
        Asserts.test(KMP.indexOf("Hello World" ,"d") == 10);
        Asserts.test(KMP.indexOf("Hello World" ,"or") == 7);
        Asserts.test(KMP.indexOf("Hello World" ,"abc") == -1);
    }
}
