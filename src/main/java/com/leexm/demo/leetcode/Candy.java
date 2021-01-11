package com.leexm.demo.leetcode;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * 贪心算法：
 * 一群孩子站成一排，每一个孩子有自己的评分。现在需要给这些孩子发糖果，规则是如果一 个孩子的评分比自己身旁的一个孩子要高，
 * 那么这个孩子就必须得到比身旁孩子更多的糖果;所 有孩子至少要有一个糖果。求解最少需要多少个糖果。
 *
 * Input: [1,0,2]
 * Output: 5
 *
 * @author leexm
 * @date 2021-01-12 01:32
 */
public class Candy {

    public static void main(String[] args) {
        int[] children = {1, 0, 2, 4, 3};
        int[] candies = distributeCandy(children);
        System.out.println(Arrays.toString(candies));
        System.out.println(Arrays.stream(candies).sum());
    }

    public static int[] distributeCandy(int[] children) {
        int[] candies = IntStream.generate(() -> 1).limit(children.length).toArray();
        for (int i = 0; i < children.length - 1; i++) {
            if (children[i] < children[i + 1]) {
                candies[i + 1] = candies[i] + 1;
            }
        }
        for (int i = children.length - 1; i > 0; i--) {
            if (children[i - 1] > children[i]) {
                candies[i - 1] = Math.max(candies[i - 1], candies[i] + 1);
            }
        }
        return candies;
    }

}
