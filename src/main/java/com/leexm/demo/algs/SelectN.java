package com.leexm.demo.algs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 从集合（没有重复元素）中选取 N 个元素
 *
 * @author leexm
 * @date 2021-01-10 21:13
 */
public class SelectN {

    public static void main(String[] args) {
        int[] data = {1, 2, 3, 4, 5};
        List<int[]> list = selectN(data, 2);
        list.forEach(item -> System.out.println(Arrays.toString(item)));
    }

    /**
     * 选取 N 个
     * 
     * @param source
     *            没有重复元素的非空数组
     * @param n
     *            n 小于 数组元素的个数
     * @return
     */
    public static List<int[]> selectN(int[] source, int n) {
        List<int[]> list = new ArrayList<>();
        int len = source.length;
        if (n == len) {
            list.add(source);
            return list;
        } else if (n == 0) {
            return list;
        } else if (n == 1) {
            for (int value : source) {
                int[] temp = new int[] {value};
                list.add(temp);
            }
            return list;
        }
        // 计算结果个数
        int sum = 1;
        for (int i = 0; i < n; i++) {
            sum = sum * (len - i);
        }
        for (int i = 1; i <= n; i++) {
            sum = sum / i;
        }

        List<int[]> arrays = new ArrayList<>();
        arrays.add(source);
        for (int i = 1; i < n; i++) {
            int[] temp = new int[len];
            System.arraycopy(source, i, temp, 0, len - i);
            System.arraycopy(source, 0, temp, len - i, i);
            arrays.add(temp);
        }
        do {
            for (int i = 0; i < len; i++) {
                int[] temp = new int[n];
                for (int j = 0; j < n; j++) {
                    temp[j] = arrays.get(j)[i];
                }
                list.add(temp);
                sum--;
                if (sum == 0) {
                    break;
                }
            }
            for (int i = 1; i < n; i++) {
                int[] array = arrays.get(i);
                cycleArray(array);
            }
        } while (sum > 0);
        return list;
    }

    /**
     * 将数组元素往前进一位，头元素放置在末尾
     * 
     * @param array
     */
    private static void cycleArray(int[] array) {
        int top = array[0];
        if (array.length - 2 >= 0) {
            System.arraycopy(array, 1, array, 0, array.length - 2);
        }
        array[array.length - 1] = top;
    }

}
