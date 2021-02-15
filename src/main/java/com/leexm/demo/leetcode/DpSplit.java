package com.leexm.demo.leetcode;

import java.util.*;

/**
 * 动态规划——分割类问题
 *
 * @author leexm
 * @date 2021-02-15 15:44
 */
public class DpSplit {

    public static void main(String[] args) {
        DpSplit dpSplit = new DpSplit();
        System.out.println(dpSplit.wordBreak("ab", Arrays.asList("a", "b", "dog", "sand", "dog")));
    }


    /**
     * 279. 完全平方数
     * https://leetcode-cn.com/problems/perfect-squares/
     *
     * @param n
     * @return
     */
    public int numSquares(int n) {
        int[] dp = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            dp[i] = Integer.MAX_VALUE;
        }
        dp[0] = 0;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j * j <= i; j++) {
                dp[i] = Math.min(dp[i], dp[i - j * j] + 1);
            }
        }
        return dp[n];
    }


    /**
     * 91. 解码方法
     * https://leetcode-cn.com/problems/decode-ways/
     *
     * @param s
     * @return
     */
    public int numDecodings(String s) {
        if (s.startsWith("0")) {
            return 0;
        }
        int[] dp = new int[s.length() + 1];
        dp[0] = 1;
        dp[1] = 1;
        char pre = s.charAt(0);
        int i = 1;
        for (; i < s.length(); i++) {
            char cur = s.charAt(i);
            if (pre == '0' || pre > '2' ) {
                if (cur == '0') {
                    break;
                }
                dp[i + 1] = dp[i];
            } else if (cur == '0') {
                dp[i + 1] = dp[i - 1];
            } else if (pre == '2' && cur > '6') {
                dp[i + 1] = dp[i];
            } else {
                dp[i + 1] = dp[i] + dp[i - 1];
            }
            pre = cur;
        }
        if (i < s.length()) {
            return 0;
        }
        return dp[s.length()];
    }


    /**
     * 139. 单词拆分
     * https://leetcode-cn.com/problems/word-break/
     *
     * @param s
     * @param wordDict
     * @return
     */
    public boolean wordBreak(String s, List<String> wordDict) {
        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true;
        for (int i = 1; i <= s.length(); i++) {
            for (String word : wordDict) {
                int len = word.length();
                if (i >= len && s.substring(i - len, i).equals(word)) {
                    dp[i] = dp[i] || dp[i - len];
                }
            }
        }
        return dp[s.length()];
    }

}
