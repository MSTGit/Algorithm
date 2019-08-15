package com.xd;

import java.util.Arrays;

public class CoinChange {
    public static void main(String[] args) {
       // coinChange(new Integer[]{25,10,5,1},41);
    }
    static void coinChange(Integer[] faces,int money) {
        Arrays.sort(faces,(Integer f1, Integer f2) -> f2 - f1);
        int coins = 0,i = 0;
        while (i < faces.length) {
            if (money < faces[i]) {
                i++;
                continue;
            }
            money -= faces[i];
            System.out.println(faces[i]);
            coins++;
        }
        System.out.println(coins);
    }
    static void coinChange() {
        int[] faces = {25,10,5,1};
        Arrays.sort(faces);
        int money = 41,coins = 0;
        for (int i = faces.length - 1; i >= 0 ; i--) {
            if (money < faces[i])  continue;
            money -= faces[i];
            coins++;
            System.out.println(faces[i]);
            i = faces.length ;
        }
        System.out.println(coins);
    }
}
