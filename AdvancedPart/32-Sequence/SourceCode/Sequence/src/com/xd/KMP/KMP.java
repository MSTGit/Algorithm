package com.xd.KMP;

public class KMP {
    public static int indexOf(String text, String pattern) {
        if (text == null || pattern == null) return -1;
        char[] textChars = text.toCharArray();
        int tlen = textChars.length;
        if (tlen == 0) return -1;
        char[] patternChars = pattern.toCharArray();
        int plen = patternChars.length;
        if (plen == 0) return -1;
        if (tlen < plen) return -1;
        //定义一个next表
        int[] next = next(pattern);
        int pi = 0, ti = 0, lenDelta = tlen - plen;
        while (pi < plen && ti - pi <= lenDelta) {
            //pi小于0，说明是0号位置失配，如果进入if判断的话，就会执行++操作，巧妙的将-1变为了0
            if (pi < 0 || textChars[ti] == patternChars[pi]) {
                ti++;
                pi++;
            } else {
                pi = next[pi];
            }
        }
        if (pi == plen) {
            //说明找到了
            return ti - pi;
        }
        return -1;
    }

    private static int[] next(String pattern) {
        char[] chars = pattern.toCharArray();
        int[] next = new int[chars.length];
        next[0] = -1;
        int i = 0;
        int n = -1;
        int iMax = chars.length - 1;
        while (i < iMax) {//i < iMax 是因为后面会做++操作，操作完成后，就变为了i <= iMax
            if (n < 0 || pattern.charAt(i) == pattern.charAt(n)) {
                ++i;
                ++n;
                if (pattern.charAt(i) == pattern.charAt(n)) {
                    next[i] = next[n];
                } else {
                    next[i] = n;
                }
            } else {
                //失配
                n = next[n];
            }
        }
        return next;
    }
}
