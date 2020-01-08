package com.xd;

public class LongestIncreasingSubsequence {
    public static void main(String[] args) {
        System.out.println(lengthOfLongestIncreasingSubsequence(new int[]{10,2,2,5,1,7,101,18}));
    }

    /*
    * 动态规划
    * */
    static int lengthOfLongestIncreasingSubsequence1(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        int[] dp = new int[nums.length];
        dp[0] = 1;
        int max = 1;
        for (int i = 1; i < dp.length; i++) {
            dp[i] = 1;
            for (int j = 0; j < i; j++) {
                if (nums[i] <= nums[j]) continue;
                dp[i] = Math.max(dp[i],dp[j] + 1);
            }
            max = Math.max(dp[i],max);
        }
        return max;
    }

    /*
    * 二分搜索
    * */
    static int lengthOfLongestIncreasingSubsequence(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        int len = 0;//牌堆的数量
        int[] top = new int[nums.length];//牌顶数组
        //遍历所有的牌
        for (int num : nums) {
            int begin = 0;
            int end = len;
            while (begin < end) {
                int mid = (begin + end) >> 1;
                if (num <= top[mid]) {
                    end = mid;
                } else {
                    begin = mid + 1;
                }
            }
            //覆盖牌顶
            top[begin] = num;
            //检查是否要新建牌堆
            if (begin == len) len++;
        }
        return len;
    }
}
