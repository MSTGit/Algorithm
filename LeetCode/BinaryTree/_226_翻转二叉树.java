package com.xd.LeetCode.BinaryTree;

/*
* 题目链接：https://leetcode-cn.com/problems/invert-binary-tree/
*/

public class _226_翻转二叉树 {
    public TreeNode invertTree(TreeNode root) {
        if (root == null) return root;
        TreeNode tmp = root.left;
        root.left = root.right;
        root.right = tmp;
        invertTree(root.left);
        invertTree(root.right);
        return root;
    }
}
