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
        List<int[]> list = selectN(data, 3);
        System.out.println(list.size());
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

        List<int[]> arrays = new ArrayList<>();
        arrays.add(source);
        for (int i = 1; i < n; i++) {
            int[] temp = new int[len - i];
            System.arraycopy(source, i, temp, 0, len - i);
            arrays.add(temp);
        }
        int index = n - 1;
        while (index > 0) {
            // 根据最后一个数组取元素
            while (arrays.get(n - 1).length > 0) {
                for (int i = 0; i < arrays.get(n - 1).length; i++) {
                    int j = i;
                    int[] temp = arrays.stream().mapToInt(item -> item[j]).toArray();
                    list.add(temp);
                }
                arrays.set(n - 1, cycleArray(arrays.get(n - 1)));
            }
            arrays.set(index, cycleArray(arrays.get(index)));
            for (int i = index + 1; i < n; i++) {
                arrays.set(i, cycleArray(arrays.get(i - 1)));
            }
            if (arrays.get(index).length == 0) {
                index--;
            }
        }
        return list;
    }

    /**
     * 将数组元素往前进一位，头元素放置在末尾
     * 
     * @param array
     */
    private static int[] cycleArray(int[] array) {
        if (array.length == 0) {
            return new int[0];
        }
        int[] temp = new int[array.length - 1];
        System.arraycopy(array, 1, temp, 0, temp.length);
        return temp;
    }

}
