package com.xd.LeetCode.Stack;

import java.util.HashMap;
import java.util.Stack;

public class _20_有效的括号 {
    private static HashMap<Character,Character> map = new HashMap<>();
    static {
        map.put('(',')');
        map.put('[',']');
        map.put('{','}');
    }
    /*
    * 通过hashmap与栈的方式实现
    * */
    public boolean isValid(String s) {
        int len = s.length();
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < len; i++) {
            char c = s.charAt(i);
            if (map.containsKey(c)) {
                //左括号
                stack.push(c);
            } else {
                //右括号
                if (stack.isEmpty()) return false;
                if (stack.pop() != map.get(c)) return  false;
            }
        }
        return stack.isEmpty();
    }

    /*
    * 使用栈的方式实现
    * */
    public boolean isValid1(String s) {
        int len = s.length();
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < len; i++) {
            char c = s.charAt(i);
            if (c == '(' || c == '{' || c == '[') {
                //左括号
                stack.push(c);
            } else {
                //右括号
                if (stack.isEmpty()) return false;
                char left = stack.pop();
                if (left == '(' && c != ')') return false;
                if (left == '{' && c != '}') return false;
                if (left == '[' && c != ']') return false;
            }
        }
        return stack.isEmpty();
    }

    /*
    * 使用低效率的方式来判断
    * */
    public boolean isValid2(String s) {
        while (s.contains("{}")|| s.contains("()") || s.contains("[]")) {
            s = s.replace("{}","");
            s = s.replace("[]","");
            s = s.replace("()","");
        }
        return s.isEmpty();
    }
}
