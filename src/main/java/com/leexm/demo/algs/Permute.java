package com.leexm.demo.algs;

/**
 * 输出给定数组中数字所有可能排列
 * f(1,2,...n) = {最后一位是1, f(n-1)} + {最后一位是2, f(n-1)} +...+{最后一位是n, f(n-1)}。
 *
 * @author leexm
 * @date 2020-04-22 13:47
 */
public class Permute {

    public static void main(String[] args) {
        int[] a = {4, 3, 2};
        printPermutations(a, a.length, a.length - 1);
    }

    /**
     * 使用给定数组打印所有可能的数字
     *
     * @param data    数组
     * @param n       数组数量
     * @param k       数组下标
     */
    public static void printPermutations(int[] data, int n, int k) {
        if (k == 0) {
            for (int i = 0; i < n; i++) {
                System.out.print(data[i] + " ");
            }
            System.out.println();
        }
        for (int i = 0; i <= k; i++) {
            int tmp = data[i];
            data[i] = data[k];
            data[k] = tmp;
            printPermutations(data, n, k - 1);
            tmp = data[i];
            data[i] = data[k];
            data[k] = tmp;
        }
    }

}
