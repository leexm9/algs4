package com.leexm.demo.leetcode;

import com.leexm.demo.algs.Sorts;
import com.leexm.demo.util.Format;

import java.util.*;

/**
 * 贪心算法 保证每次操作都是局部最优的，从而使最后得到的结果是全局最优的。
 *
 * @author leexm
 * @date 2021-01-29 00:15
 */
public class Greedy {

    public static void main(String[] args) {
        Greedy greedy = new Greedy();
//        int[][] people = {{7,0},{4,4},{7,1},{5,0},{6,1},{5,2}};
        int[][] people = {{4,3},{9,0},{5,1},{9,1},{6,0},{0,6},{1,1},{7,3},{9,2},{1,7}};
        Format.printMatrix(greedy.reconstructQueue2(people));
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

    /**
     * 605. 种花问题 https://leetcode-cn.com/problems/can-place-flowers/
     *
     * @param flowerbed
     * @param n
     * @return
     */
    public boolean canPlaceFlowers(int[] flowerbed, int n) {
        for (int i = 0; i < flowerbed.length; i++) {
            if (flowerbed[i] == 0) {
                flowerbed[i] = 2;
            }
        }
        int sum = 0;
        for (int i = 0; i < flowerbed.length; i++) {
            if (flowerbed[i] == 1) {
                if (i != flowerbed.length - 1) {
                    flowerbed[i + 1] = 0;
                }
            }
            if (flowerbed[i] == 2 && i < flowerbed.length - 1) {
                if (flowerbed[i + 1] == 2) {
                    flowerbed[i + 1] = 0;
                }
                if (flowerbed[i + 1] == 1) {
                    flowerbed[i] = 0;
                }

            }
            if (flowerbed[i] == 2) {
                sum++;
            }
        }
        return sum >= n;
    }

    /**
     * 452. 用最少数量的箭引爆气球
     * https://leetcode-cn.com/problems/minimum-number-of-arrows-to-burst-balloons/
     *
     * @param points
     * @return
     */
    public int findMinArrowShots(int[][] points) {
        if (points.length == 0) {
            return 0;
        }
        Arrays.sort(points, (point1, point2) -> {
            if (point1[1] > point2[1]) {
                return 1;
            } else if (point1[1] < point2[1]) {
                return -1;
            } else {
                return 0;
            }
        });

        int flag = points[0][1];
        int arrow = 1;
        for (int i = 0; i < points.length; i++) {
            if (points[i][0] > flag) {
                flag = points[i][1];
                arrow++;
            }
        }
        return arrow;
    }

    /**
     * 763. 划分字母区间
     * https://leetcode-cn.com/problems/partition-labels/
     *
     * @param S
     * @return
     */
    public List<Integer> partitionLabels(String S) {
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < S.length(); i++) {
            char ch = S.charAt(i);
            map.put(ch, i);
        }
        List<Integer> rs = new ArrayList<>();
        int i = 0;
        int begin = i;
        int end = i;
        while (i < S.length() && end < S.length() - 1) {
            char ch = S.charAt(i);
            int range = map.get(ch);
            if (i <= end) {
                if (range > end) {
                    end = range;
                }
            } else {
                rs.add(end - begin + 1);
                begin = i;
                end = range;
            }
            i++;
        }
        rs.add(end - begin + 1);
        return rs;
    }

    /**
     * 122. 买卖股票的最佳时机 II
     * https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-ii/
     *
     * @param prices
     * @return
     */
    public int maxProfit(int[] prices) {
        int[] rs = new int[prices.length];
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] >= prices[i - 1]) {
                // 1：买，2：卖
                rs[i - 1] = 1;
                rs[i] = 2;
            }
        }
        int sum = 0;
        int flag = 0;
        for (int i = 0; i < prices.length; i++) {
            if (rs[i] == 1 && flag == 0) {
                sum -= prices[i];
                flag += rs[i];
            }
            if (rs[i] == 2 && flag == 1) {
                sum += prices[i];
                flag = 0;
            }
        }
        return sum;
    }

    /**
     * 406. 根据身高重建队列
     * https://leetcode-cn.com/problems/queue-reconstruction-by-height/
     *
     * @param people
     * @return
     */
    public int[][] reconstructQueue1(int[][] people) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < people.length; i++) {
            int[] tmp = people[i];
            map.computeIfAbsent(tmp[0], k -> new ArrayList<>()).add(tmp[1]);
        }
        for (Map.Entry<Integer, List<Integer>> entry : map.entrySet()) {
            Collections.sort(entry.getValue());
        }
        int[] indexs = map.keySet().stream().sorted(Comparator.naturalOrder()).mapToInt(Integer::intValue).toArray();

        int end = -1;
        for (int i = 0; i < indexs.length; i++) {
            int idx = indexs[i];
            List<Integer> list1 = map.get(idx);
            for (int j = 0; j < list1.size(); j++) {
                int num = list1.get(j) - j;
                int k = end;
                for (; k >= 0 && people[k][1] > num; k--) {
                    people[k + 1] = people[k];
                    people[k + 1][1] = people[k + 1][1] - 1;
                }
                people[k + 1] = new int[]{idx, num};
                end++;
            }
        }

        for (int i = 0; i < people.length; i++) {
            List<Integer> list1 = map.get(people[i][0]);
            people[i][1] = list1.get(0);
            list1.remove(0);
        }
        return people;
    }

    public int[][] reconstructQueue2(int[][] people) {
        // 排序，第一个元素降序，第二个元素升序
        Arrays.sort(people, (p1, p2) -> {
            if (p1[0] == p2[0]) {
                return p1[1] - p2[1];
            } else {
                return p2[0] - p1[0];
            }
        });

        List<int[]> list = new ArrayList<>();
        for (int i = 0; i < people.length; i++) {
            if (list.size() < people[i][1]) {
                list.add(people[i]);
            } else {
                list.add(people[i][1], people[i]);
            }
        }

        return list.toArray(new int[list.size()][]);
    }

    /**
     * 665. 非递减数列
     * https://leetcode-cn.com/problems/non-decreasing-array/
     *
     * @param nums
     * @return
     */
    public boolean checkPossibility(int[] nums) {
        int[] copy = new int[nums.length];

        // 从左往右，重置数据
        System.arraycopy(nums, 0, copy, 0, nums.length);
        int num1 = 0;
        for (int i = 1; i < copy.length; i++) {
            if (copy[i] < copy[i - 1]) {
                copy[i] = copy[i - 1];
                num1++;
            }
        }
        if (num1 <= 1) {
            return true;
        }

        // 从右往左，重置数据
        System.arraycopy(nums, 0, copy, 0, nums.length);
        int num2 = 0;
        for (int i = copy.length - 2; i >= 0; i--) {
            if (copy[i] > copy[i + 1]) {
                copy[i] = copy[i + 1];
                num2++;
            }
        }
        return num2 <= 1;
    }

}
