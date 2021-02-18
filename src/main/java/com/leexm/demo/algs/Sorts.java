package com.leexm.demo.algs;

import java.util.Arrays;

/**
 * @author leexm
 * @date 2020-04-01 16:23
 */
public class Sorts {

    /**
     * 选择排序
     *
     * @param nums
     */
    public static void selectionSort(int[] nums) {
        int min = 0;
        for (int i = 0; i < nums.length - 1; i++) {
            min = i;
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[j] < nums[min]) {
                    min = j;
                }
            }
            if (min != i) {
                swap(nums, min, i);
            }
        }
    }

    /**
     * 插入排序
     *
     * @param nums
     */
    public static void insertionSort(int[] nums) {
        for (int i = 1; i < nums.length; i++) {
            int value = nums[i];
            int j = i - 1;
            for (; j >= 0 && nums[j] > value; j--) {
                nums[j + 1] = nums[j];
            }
            nums[j + 1] = value;
        }
    }

    /**
     * 希尔排序，插入排序的改进版
     *
     * @param nums
     */
    public static void shellSort(int[] nums) {
        int n = nums.length;
        // 进行分组，最开始时的增量（gap）为数组长度的一半
        for (int gap = n / 2; gap > 0; gap /= 2) {
            // 对各个分组进行插入排序
            for (int i = gap; i < n; i++) {
                // 将nums[i]插入到所在分组的正确位置上
                int inserted = nums[i];
                int j = i - gap;
                for (; j >= 0 && inserted < nums[j]; j -= gap) {
                    nums[j + gap] = nums[j];
                }
                nums[j + gap] = inserted;
            }
        }
    }

    /**
     * 冒泡排序
     *
     * @param nums
     */
    public static void bubbleSort(int[] nums) {
        for (int i = nums.length - 1; i >= 0; i--) {
            // 标记该次循环是否发生了交换，若无，则说明整个数据有序
            boolean flag = false;
            for (int j = 0; j < i; j++) {
                if (nums[j] > nums[j + 1]) {
                    swap(nums, j, j + 1);
                    flag = true;
                }
            }
            if (!flag) {
                break;
            }
        }
    }

    /**
     * 归并排序
     *
     * @param nums
     */
    public static void mergeSort(int[] nums) {
        int[] tmp = new int[nums.length];
        doMerge(nums, tmp, 0, nums.length - 1);
    }

    /**
     * 分隔成小区间排序，排序后的小区间再合并
     *
     * @param nums 待处理数组
     * @param tmp   临时存放数据
     * @param start 起始下标
     * @param end   结束下标
     */
    private static void doMerge(int[] nums, int[] tmp, int start, int end) {
        if (start == end) {
            return;
        }
        if (start == (end - 1)) {
            if (nums[start] > nums[end]) {
                swap(nums, start, end);
            }
            return;
        }
        int mid = (start + end) / 2;
        doMerge(nums, tmp, start, mid);
        doMerge(nums, tmp, mid + 1, end);

        // 合并
        int i = start, j = mid + 1;
        // tmp 数组中下标
        int index = i;
        for (; i <= mid && j <= end;) {
            if (nums[i] < nums[j]) {
                tmp[index] = nums[i];
                i++;
            } else {
                tmp[index] = nums[j];
                j++;
            }
            index++;
        }
        if (i <= mid) {
            System.arraycopy(nums, i, tmp, index, mid - i + 1);
        } else {
            System.arraycopy(nums, j, tmp, index, end - j + 1);
        }
        System.arraycopy(tmp, start, nums, start, end - start + 1);
    }

    /**
     * 快速排序
     *
     * @param nums
     */
    public static void quickSort(int[] nums) {
        doQuick(nums, 0, nums.length - 1);
    }

    /**
     * 处理区间[start, end]，小的在左边，大的在右边
     *
     * @param nums      待处理的数组
     * @param start     开始下标
     * @param end       结束下标
     */
    private static void doQuick(int[] nums, int start, int end) {
        if (start >= end) {
            return;
        }
        // 取尾元素为基准
        int base = nums[end];

        int i = start;
        int j = end - 1;
        while (true) {
            while (i <= j && nums[i] <= base) {
                i++;
            }
            while (i <= j && nums[j] > base) {
                j--;
            }
            if (i < j) {
                swap(nums, i, j);
            } else {
                break;
            }
        }
        // 将基准调换到正确的位置
        swap(nums, i, end);

        // 对两个子集进行处理
        doQuick(nums, start, i - 1);
        doQuick(nums, i + 1, end);
    }

    /**
     * 计数排序
     * 适合数据范围跨度不大非负整数的数组
     *
     * @param nums
     */
    public static void countingSort(int[] nums) {
        if (nums.length <= 1) {
            return;
        }

        // 查找数组中数据的范围
        int max = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > max) {
                max = nums[i];
            }
        }

        // 计数数组，下标范围[0, top]
        int[] count = new int[max + 1];
        // 遍历数组计数统计
        for (int i = 0; i < nums.length; i++) {
            count[nums[i]]++;
        }

        // 依次累加
        for (int i = 1; i <= max; i++) {
            count[i] = count[i] + count[i - 1];
        }

        // 这种处理方式可以处理 nums[i] 与 count 下标不一致的情况：A12, A36，Y90 这种
        int[] tmp = new int[nums.length];
        for (int i = nums.length - 1; i>= 0; i--) {
            int index = count[nums[i]] - 1;
            tmp[index] = nums[i];
            count[nums[i]]--;
        }
        System.arraycopy(tmp, 0, nums, 0, nums.length);
    }

    private static void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }

    public static void main(String[] args) {
        int[] nums = {34,13,5,7,1,72,61,6,4,5,6,6,6,14};
//        Sorts.selectionSort(nums);
//        Sorts.insertionSort(nums);
//        Sorts.bubbleSort(nums);
//        Sorts.mergeSort(nums);
        Sorts.quickSort(nums);
//        Sorts.countingSort(nums);
//        Sorts.shellSort(nums);
        System.out.println(Arrays.toString(nums));

        nums = new int[]{1,2,3,4,5,6,7,8,9, 10};
//        Sorts.selectionSort(nums);
//        Sorts.insertionSort(nums);
//        Sorts.bubbleSort(nums);
//        Sorts.mergeSort(nums);
        Sorts.quickSort(nums);
//        Sorts.countingSort(nums);
//        Sorts.shellSort(nums);
        System.out.println(Arrays.toString(nums));

        nums = new int[]{10,9,8,7,6,5,4,3,2,1};
//        Sorts.selectionSort(nums);
//        Sorts.insertionSort(nums);
//        Sorts.bubbleSort(nums);
//        Sorts.mergeSort(nums);
        Sorts.quickSort(nums);
//        Sorts.countingSort(nums);
//        Sorts.shellSort(nums);
        System.out.println(Arrays.toString(nums));
    }

}
