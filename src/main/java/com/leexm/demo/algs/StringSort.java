package com.leexm.demo.algs;

import java.util.Arrays;

/**
 * 对等长字符串数组排序
 *
 * @author leexm
 * @date 2020-04-03 00:09
 */
public class StringSort {

    public static void main(String[] args) {
        // 等长字符串
        String[] strs = {"1she2", "2sel3", "3seat", "1by2e", "3the2", "5she2", "4she7", "2sel1", "4are2", "2sur3"};
        // 字符串长度
        int size = strs[0].length();
        for (int i = size - 1; i>= 0; i--) {
            stringSort(strs, i);
        }
        System.out.println(Arrays.toString(strs));

        // 不等长字符串
        strs = new String[]{"she", "sells", "the", "hantao", "daneng", "dou", "she", "like", "li", "min", "doudou", "a", "banana"};
        int max = strs[0].length();
        for (int i = 0; i < strs.length; i++) {
            if (strs[i].length() > max) {
                max = strs[i].length();
            }
        }
        // 使用 0 补全成等长的字符串
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < max; i++) {
            builder.append("0");
        }
        for (int i = 0; i < strs.length; i++) {
            int diff = max - strs[i].length();
            strs[i] = strs[i] + builder.subSequence(0, diff).toString();
        }
        for (int i = max - 1; i>= 0; i--) {
            stringSort(strs, i);
        }
        for (int i = 0; i < strs.length; i++) {
            strs[i] = strs[i].replaceAll("0", "");
        }
        System.out.println(Arrays.toString(strs));
    }

    /**
     * 根据字符串的第 index 位，对字符串排序
     * 用到计数排序算法的原理
     *
     * @param strs      原数组
     * @param index     原数组的下标 index 位
     * @return
     */
    private static void stringSort(String[] strs, int index) {
        int[] count = new int[(int) 'z' + 1];
        for (String str : strs) {
            count[str.charAt(index)]++;
        }
        for (int i = 1; i < count.length; i++) {
            count[i] = count[i - 1] + count[i];
        }
        String[] desc = new String[strs.length];
        for (int i = strs.length - 1; i >= 0; i--) {
            int id = strs[i].charAt(index);
            int dx = count[id] - 1;
            desc[dx] = strs[i];
            count[id]--;
        }
        System.arraycopy(desc, 0, strs, 0, strs.length);
    }

}
