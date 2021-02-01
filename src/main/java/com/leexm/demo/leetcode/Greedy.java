package com.leexm.demo.leetcode;

import com.leexm.demo.algs.Sorts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 贪心算法
 * 保证每次操作都是局部最优的，从而使最后得到的结果是全局最优的。
 *
 * @author leexm
 * @date 2021-01-29 00:15
 */
public class Greedy {

    public static void main(String[] args) {
    }

    /**
     * 455. 分发饼干
     * https://leetcode-cn.com/problems/assign-cookies/
     *
     * @param g
     * @param s
     * @return
     */
    public int findContentChildren(int[] g, int[] s) {
        if (s.length == 0) {
            return 0;
        }
        Sorts.quickSort(g);
        Sorts.quickSort(s);
        int i = 0, j = 0;
        int sum = 0;
        while (i < s.length && j < g.length) {
            if (s[i++] >= g[j]) {
                sum++;
                j++;
            }
        }
        return sum;
    }


    /**
     * 135. 分发糖果
     * https://leetcode-cn.com/problems/candy/
     *
     * @param ratings
     * @return
     */
    public int candy(int[] ratings) {
        if (ratings.length < 2) {
            return ratings.length;
        }
        int[] candies = new int[ratings.length];
        candies[0] = 1;
        for (int i = 1; i < ratings.length; ++i) {
            if (ratings[i] > ratings[i - 1]) {
                candies[i] = candies[i - 1] + 1;
            } else {
                candies[i] = 1;
            }
        }
        for (int i = ratings.length - 1; i > 0; --i) {
            if (ratings[i] < ratings[i - 1] && candies[i - 1] <= candies[i]) {
                candies[i - 1] = candies[i] + 1;
            }
        }
        int sum = 0;
        for (int i = 0; i < candies.length; ++i) {
            sum += candies[i];
        }
        return sum;
    }


    /**
     * 435. 无重叠区间
     * https://leetcode-cn.com/problems/non-overlapping-intervals/
     *
     * @param intervals
     * @return
     */
    public int eraseOverlapIntervals(int[][] intervals) {
        if (intervals.length == 0) {
            return 0;
        }
        // 对二维数组排序
        intervals = Arrays.stream(intervals).sorted((o1, o2) -> o1[0] - o2[0]).sorted((o1, o2) -> o1[1] - o2[1])
                .toArray(int[][]::new);
        int[] pre = intervals[0];
        List<int[]> remove = new ArrayList<>();
        for (int i = 1; i < intervals.length; i++) {
            int[] t = intervals[i];
            if (t[0] <= pre[0] || t[0] < pre[1]) {
                remove.add(t);
            } else {
                pre = t;
            }
        }
        return remove.size();
    }

}
