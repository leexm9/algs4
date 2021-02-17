package com.leexm.demo.leetcode;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * 动态规划——子序列问题
 *
 * @author leexm
 * @date 2021-02-17 13:45
 */
public class DpSubsequence {

    public static void main(String[] args) {
        DpSubsequence dpSubsequence = new DpSubsequence();
        System.out.println(dpSubsequence.longestCommonSubsequence("pmjghexybyrgzczy", "hafcdqbgncrcbihkd"));
    }

    /**
     * 300. 最长递增子序列 https://leetcode-cn.com/problems/longest-increasing-subsequence/
     *
     * @param nums
     * @return
     */
    public int lengthOfLIS1(int[] nums) {
        int max = 0;
        int[] dp = new int[nums.length];
        dp[0] = 1;
        for (int i = 1; i < nums.length; i++) {
            dp[i] = 1;
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            max = Math.max(max, dp[i]);
        }
        return max;
    }

    public int lengthOfLIS2(int[] nums) {
        int[] dp = new int[nums.length];
        dp[0] = 1;
        int max = dp[0];
        for (int i = 1; i < nums.length; i++) {
            dp[i] = 1;
            if (nums[i] > nums[i - 1]) {
                dp[i] = dp[i - 1] + 1;
            }
            // 非连续递增的处理
            if (dp[i] != i + 1) {
                for (int j = 0; j < i; j++) {
                    if (nums[j] < nums[i]) {
                        dp[i] = Math.max(dp[i], dp[j] + 1);
                    }
                }
            }
            max = Math.max(max, dp[i]);
        }
        return max;
    }

    public int lengthOfLIS3(int[] nums) {
        int n = nums.length;
        if (n <= 1) {
            return n;
        }

        LinkedList<Integer> list = new LinkedList<>();
        list.add(nums[0]);
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > list.peekLast()) {
                list.add(nums[i]);
            } else {
                this.replace(list, nums[i]);
            }
        }
        System.out.println(list);
        return list.size();
    }

    /**
     * 查找并替换第一个大于 target
     *
     * @param list
     * @param target
     */
    private void replace(LinkedList<Integer> list, int target) {
        int index = this.binSearchMin(list, target, 0, list.size() - 1);
        // 可以找到
        if (index >= 0) {
            list.remove(index);
            list.add(index, target);
        } else {
            // target 大于 list 中任何一个值
            list.clear();
            list.add(target);
        }
    }

    private int binSearchMin(LinkedList<Integer> list, int target, int left, int right) {
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (list.get(mid) >= target) {
                if (mid == 0 || list.get(mid - 1) < target) {
                    return mid;
                }
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return -1;
    }

    public int lengthOfLIS4(int[] nums) {
        int n = nums.length;
        if (n <= 1) {
            return n;
        }
        int len = 0;
        int[] res = new int[n];
        for (int i = 0; i < n; i++) {
            int temp = nums[i];
            int idx = Arrays.binarySearch(res, 0, len, temp);
            idx = idx < 0 ? -idx - 1 : idx;
            res[idx] = temp;
            if (idx == len) {
                len++;
            }
        }
        return len;
    }

    /**
     * 1143. 最长公共子序列
     * https://leetcode-cn.com/problems/longest-common-subsequence/
     * 状态定义： dp[i][j] 表示text1[0~i-1]和text2[0~j-1]的最长公共子序列长度
     * dp[0][0]等于0，等于dp数组总体往后挪了一个，免去了判断出界
     * 转移方程： text1[i-1] == text2[j-1]
     * 当前位置匹配上了: dp[i][j]=dp[i-1][j-1]+1 text1[i-1] ！= text2[j-1]
     * 当前位置没匹配上了 ：dp[i][j]=max(dp[i-1][j],dp[i][j-1]);
     * basecase: 任何一个字符串为0时都是零，初始化时候就完成了basecase是赋值
     *
     *
     * @param text1
     * @param text2
     * @return
     */
    public int longestCommonSubsequence(String text1, String text2) {
        int m = text1.length(), n = text2.length();
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[m][n];
    }

}
