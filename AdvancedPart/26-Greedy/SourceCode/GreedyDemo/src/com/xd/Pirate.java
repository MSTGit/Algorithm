package com.xd;

import java.util.Arrays;

public class Pirate {
    public static void main(String[] args) {
        int[] weights = {3,5,4,10,7,14,2,11};
        Arrays.sort(weights);
        int capacity = 30, weight = 0, count = 0;
        for (int i = 0; i < weights.length; i++) {
            int newWeight = weight + weights[i];
            if (newWeight > capacity) break;
            weight = newWeight;
            count++;
            System.out.println(weights[i]);
        }
        System.out.println("一共选择了" + count + "件古董");
    }
}