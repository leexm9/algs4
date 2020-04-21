package com.leexm.demo.algs;

import java.util.Arrays;

/**
 * 使用位运算交换两个数
 *
 * @author leexm
 * @date 2020-04-11 14:47
 */
public class Swap {

    public static void main(String[] args) {
        int x = 10, y = 23;
        x = x ^ y;
        y = x ^ y;
        x = x ^ y;
        System.out.println(String.format("x = %d, y = %d", x, y));

        int[] array = new int[4];
        Arrays.stream(array).forEach(System.out::println);
    }

}
