package com.leexm.demo.leetcode;

import java.util.Arrays;

/**
 * 动态规划——背包问题
 *
 * 关于空间优化：
 * 在动态规划中，如果第i个状态只与第i-1个状态有关，而不与其他的例如第i - k(0 < k < i)个状态有关，
 * 那么意味着此时在空间上有优化的空间，我们可以采用滚动数组或者从后往前的方式填表来代替开辟更高维度的数组。
 *
 * 滚动数组可以理解，但另一种方式是从后往前的方式填表，这是为什么呢？
 * 我们可以举个例子，假设一个状态方程为 dp[i][j] = dp[i-1][j-1] + 1;
 * 如果采用从后向前填表，那么我们的dp[i-1][j-1]应该是上一轮计算的结果，因为这一轮我们还没有更新过这个值
 * 但如果采用从前往后填表，那么我们的dp[i-1][j-1]应该是这一轮计算的结果，因为这一轮我们已经更新过这个值
 * 但是我们这个二维dp数组是最初的三维dp数组的一个优化，因此，在状态迁移时，我们需要的是上一轮计算的dp[i-1][j-1]
 * 这就是为什么我们要从后往前填表了，主要是保留上一轮计算的结果不被覆盖。
 *
 * @author leexm
 * @date 2021-02-17 21:55
 */
public class Knapsack {

    public static void main(String[] args) {
        Knapsack knapsack = new Knapsack();
        int[] weights = {2, 9, 4, 5};
        int[] values = {3, 4, 5, 6};
        // System.out.println(knapsack.knapsack(weights, values, 4, 8));
        // System.out.println(knapsack.canPartition2(new int[]{1, 9, 3, 5}));
        System.out.println(knapsack.findMaxForm(new String[] {"10", "0001", "111001", "1", "0"}, 5, 3));
    }

    /**
     * 0-1背包问题
     * https://zhuanlan.zhihu.com/p/107139719
     *
     * @param weights   各物品的重量
     * @param values    各物品的价值
     * @param N         物品数量
     * @param capacity  背包容量
     * @return
     */
    public int knapsack(int[] weights, int[] values, int N, int capacity) {
        int[][] dp = new int[N + 1][capacity + 1];
        for (int i = 1; i <= N; i++) {
            int w = weights[i - 1], v = values[i - 1];
            for (int j = 1; j <= capacity; j++) {
                if (j >= w) {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - w] + v);
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }
        return dp[N][capacity];
    }

    /**
     * 416. 分割等和子集
     * https://leetcode-cn.com/problems/partition-equal-subset-sum/
     *
     * @param nums
     * @return
     */
    public boolean canPartition1(int[] nums) {
        int sum = Arrays.stream(nums).sum();
        if (sum % 2 == 1) {
            return false;
        }
        int capacity = sum / 2;
        int[][] dp = new int[nums.length + 1][capacity + 1];
        for (int i = 1; i <= nums.length; i++) {
            int v = nums[i - 1];
            for (int j = 1; j <= capacity; j++) {
                if (j >= v) {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - v] + v);
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }

        for (int i = 0; i <= nums.length; i++) {
            if (dp[i][capacity] == capacity) {
                return true;
            }
        }
        return false;
    }

    public boolean canPartition2(int[] nums) {
        int sum = Arrays.stream(nums).sum();
        if (sum % 2 == 1) {
            return false;
        }
        int capacity = sum / 2;
        boolean[][] dp = new boolean[nums.length + 1][capacity + 1];
        for (int i = 0; i <= nums.length; i++) {
            dp[i][0] = true;
        }
        for (int i = 1; i <= nums.length; i++) {
            int v = nums[i - 1];
            for (int j = v; j <= capacity; j++) {
                dp[i][j] = dp[i - 1][j] || dp[i - 1][j - v];
            }
        }
        return dp[nums.length][capacity];
    }

    /**
     * 474. 一和零
     * https://leetcode-cn.com/problems/ones-and-zeroes/
     *
     * @param strs
     * @param m
     * @param n
     * @return
     */
    public int findMaxForm(String[] strs, int m, int n) {
        if (strs.length == 0) {
            return 0;
        }
        // dp[i][j]表示使用 i 个0 和 j 个1 能表示的字符串的最大数量
        int[][] dp = new int[m + 1][n + 1];
        for (String s : strs) {
            // 统计字符串中一和零的数量。
            int zeros = 0, ones = 0;
            for (char c : s.toCharArray()) {
                if (c == '0') {
                    zeros++;
                } else {
                    ones++;
                }
            }

            /**
             * 对当前字符串：选还是不选
             * 不选当前字符串 dp[i][j], 选当前字符串 dp[j - zeros][k - zeros] + 1 取二者最大值
             */
            for (int i = m; i >= zeros; i--) {
                for (int j = n; j >= ones; j--) {
                    dp[i][j] = Math.max(dp[i][j], dp[i - zeros][j - ones] + 1);
                }
            }
        }
        return dp[m][n];
    }


    /**
     * 322. 零钱兑换
     * https://leetcode-cn.com/problems/coin-change/
     *
     * @param coins
     * @param amount
     * @return
     */
    public int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1);
        dp[0] = 0;

        for (int i = 1; i <= amount; i++) {
            for (int c : coins) {
                if (c <= i) {
                    dp[i] = Math.min(dp[i], dp[i - c] + 1);
                }
            }
        }
        return dp[amount] > amount ? -1 : dp[amount];
    }

}
