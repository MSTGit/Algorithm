package com.xd;

public class CoinChange {
    public static void main(String[] args) {
        System.out.println(generalCoins(41,new int[]{1,5,20,25}));
    }

    static int generalCoins(int n,int[] faces){
        if (n < 1 || faces == null || faces.length == 0) return -1;
        int [] dp = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            int min = Integer.MAX_VALUE;
            for (int face : faces) {
                if (i < face) continue;
                min = Math.min(dp[i - face],min);
            }
            dp[i] = min + 1;
        }
        return dp[n];
    }

    /*
    * 递推（自底向上的调用）
    * */
    static int coins(int n){
        if (n < 1) return -1;
        int[] dp = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            int min = Integer.MAX_VALUE;
            if (i >= 1) min = Math.min(dp[i - 1], min);
            if (i >= 5) min = Math.min(dp[i - 5], min);
            if (i >= 20) min = Math.min(dp[i - 20], min);
            if (i >= 25) min = Math.min(dp[i - 25], min);
            dp[i] = min + 1;
        }
        return dp[n];
    }

    /*
    * 记忆化搜索（自顶向下的调用）
    * */
    static int coins1(int n) {
        if (n < 1) return -1;
        int[] dp = new int[n + 1];
        int[] faces = {1,5,20,25};
        for (int face : faces) {
            if (n < face) break;
            dp[face] = 1;
        }
        dp[1] = dp[5] = dp[20] = dp[25] = 1;
        return coins(n,dp);
    }
    static int coins(int n, int[] dp) {
        if (n < 1)return Integer.MAX_VALUE;
        if (dp[n] == 0) {
            int min1 = Math.min(coins(n - 25,dp),coins(n - 20,dp));
            int min2 = Math.min(coins(n - 5,dp),coins(n - 1,dp));
            dp[n] = Math.min(min1,min2) + 1;
        }
        return dp[n];
    }

    /*
    * 暴力递归，自顶向下的调用，出现了重叠子问题
    * */
    static int coins2(int n) {
        if (n < 1) return Integer.MAX_VALUE;
        if (n == 25 || n == 20 || n == 5 || n ==1) return 1;
        int min1 = Math.min(coins2(n - 25),coins2(n - 20));
        int min2 = Math.min(coins2(n - 5),coins2(n - 1));
        return Math.min(min1,min2) + 1;
    }
}
