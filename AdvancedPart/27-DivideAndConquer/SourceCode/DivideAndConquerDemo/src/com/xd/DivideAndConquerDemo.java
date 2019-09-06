package com.xd;

public class DivideAndConquerDemo {

    public static void main(String[] args) {
        int[] nums = {-2,1,-3,4,-1,2,1,-5,4};
        System.out.println(maxSubarray(nums));
    }

    static int maxSubarray(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        return maxSubarray(nums,0,nums.length);
    }

    //计算[begin,end),最大连续子序列的和
    static int maxSubarray(int[] nums,int begin, int end) {
        if (end - begin < 2) return nums[begin];
        int mid = (begin + end) >> 1;
        int leftMax = Integer.MIN_VALUE;
        int leftSum = 0;
        for (int i = mid - 1; i >= begin; i--) {
            leftSum += nums[i];
            leftMax = Math.max(leftMax,leftSum);
        }
        int rightMax = Integer.MIN_VALUE;
        int rightSum = 0;
        for (int i = mid; i < end; i++) {
            rightSum += nums[i];
            rightMax = Math.max(rightMax,rightSum);
        }
        int max = leftMax + rightMax;
        return Math.max(max,Math.max(maxSubarray(nums,begin,mid),maxSubarray(nums,mid,end)));
    }
    static int maxSubarray2(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        int max = Integer.MIN_VALUE;
        for (int begin = 0; begin < nums.length; begin++) {
            int sum = 0;
            for (int end = begin; end < nums.length; end++) {
                sum += nums[end];
                max = Math.max(max,sum);
            }
        }
        return max;
    }

    static int maxSubarray1(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        int max = Integer.MIN_VALUE;
        for (int begin = 0; begin < nums.length; begin++) {
            for (int end = begin; end < nums.length; end++) {
                int sum = 0;
                for (int i = begin; i < end; i++) {
                    sum += nums[i];
                }
                max = Math.max(max,sum);
            }
        }
        return max;
    }
}
