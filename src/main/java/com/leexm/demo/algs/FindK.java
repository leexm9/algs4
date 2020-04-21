package com.leexm.demo.algs;

import java.util.Arrays;

/**
 * 查找未排序数据中第 K 个大的数据
 *
 * @author leexm
 * @date 2020-04-02 16:29
 */
public class FindK {

    /**
     * 操作范围 [start, end] 分治算法，只处理符合要求的部分
     * 
     * @param nums
     * @param start
     * @param end
     * @param k
     */
    public static void findK(int[] nums, int start, int end, int k) {
        if (start >= end) {
            return;
        }
        // 取尾元素做基准
        int base = nums[end];
        int i = start;
        int j = end - 1;
        while (true) {
            while (i <= j && nums[i] <= base) {
                i++;
            }
            while (i < j && nums[j] >= base) {
                j--;
            }
            if (i < j) {
                swap(nums, i, j);
            } else {
                break;
            }
        }
        // 基准归位
        swap(nums, i, end);
        // 第 k 大元素应该在的下标
        int index = k - 1;
        // 只处理符合要求的部分，i==index，直接退出了
        if (i > index) {
            findK(nums, start, i - 1, k);
        }
        if (i < index) {
            findK(nums, i + 1, end, k);
        }
    }

    private static void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }

    public static void main(String[] args) {
        int[] nums = {34, 13, 5, 7, 1, 72, 61, 6, 14};
        int k = 7;
        findK(nums, 0, nums.length - 1, k);
        System.out.println(nums[k - 1]);
        Sorts.quickSort(nums);
        System.out.println(Arrays.toString(nums));
    }

}
