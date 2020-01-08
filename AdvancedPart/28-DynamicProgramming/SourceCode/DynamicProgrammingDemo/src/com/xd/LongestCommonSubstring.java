package com.xd;

public class LongestCommonSubstring {
    public static void main(String[] args) {
        System.out.println(longestCommonSubstring("ABACD","BABCD"));
    }

    static int longestCommonSubstring(String str1,String str2) {
        if (str1 == null || str2 == null) return 0;
        if (str1.length() == 0 || str2.length() == 0) return 0;
        char[] chars1 = str1.toCharArray();
        char[] chars2 = str2.toCharArray();
        char[] rowsChars = chars1,colsChars = chars2;
        if (chars1.length < chars2.length) {
            rowsChars = chars2;
            colsChars = chars1;
        }
        int[] dp = new int[colsChars.length + 1];
        int max = 0;
        for (int row = 1; row <= rowsChars.length; row++) {
            int cur = 0;
            for (int col = 1; col <= colsChars.length; col++) {
                int leftTop = cur;
                cur = dp[col];
                if (chars1[row - 1] == chars2[col - 1]) {
                    dp[col] = leftTop + 1;
                    max = Math.max(max,dp[col]);
                }
                else {
                    dp[col] = 0;
                }
            }
        }
        return max;
    }
    static int longestCommonSubstring1(String str1,String str2) {
        if (str1 == null || str2 == null) return 0;
        if (str1.length() == 0 || str2.length() == 0) return 0;
        int[][] dp = new int[str1.length() + 1][str2.length() + 1];
        char[] chars1 = str1.toCharArray();
        char[] chars2 = str2.toCharArray();
        int max = 0;
        for (int i = 1; i <= chars1.length; i++) {
            for (int j = 1; j <= chars2.length; j++) {
                if (chars1[i - 1] == chars2[j - 1]) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                    max = Math.max(max,dp[i][j]);
                }
//                else {//值默认就是0 所以不用赋值
//                    dp[i][j] = 0;
//                }
            }
        }
        return max;
    }
}
