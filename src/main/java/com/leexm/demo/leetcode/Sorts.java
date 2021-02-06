package com.leexm.demo.leetcode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 排序
 *
 * @author leexm
 * @date 2021-02-03 23:44
 */
public class Sorts {

    public static void main(String[] args) {
        Sorts sorts = new Sorts();
        int[] nums = {3,2,1,5,6,4};
        System.out.println(sorts.findKthLargest(nums, 2));
    }


    /**
     * 215. 数组中的第K个最大元素
     * https://leetcode-cn.com/problems/kth-largest-element-in-an-array/
     *
     * @param nums
     * @param k
     * @return
     */
    public int findKthLargest(int[] nums, int k) {
        this.quickSort(nums, 0, nums.length - 1, --k);
        return nums[k];
    }

    private void quickSort(int[] nums, int start, int end, int k) {
        if (start >= end) {
            return;
        }
        int base = nums[start];
        int left = start + 1, right = end;
        while (true) {
            while (left <= right && nums[left] >= base) {
                left++;
            }
            while (left <= right && nums[right] < base) {
                right--;
            }
            if (left < right) {
                this.swap(nums, left, right);
            } else {
                break;
            }
        }
        this.swap(nums, start, right);
        if (right > k) {
            this.quickSort(nums, start, right - 1, k);
        } else if (right < k) {
            this.quickSort(nums, right + 1, end, k);
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }


    /**
     * 347. 前 K 个高频元素
     * https://leetcode-cn.com/problems/top-k-frequent-elements/
     *
     * @param nums
     * @param k
     * @return
     */
    public int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int item : nums) {
            Integer sum = map.get(item);
            sum = sum == null ? 1 : sum + 1;
            map.put(item, sum);
        }
        List<Map.Entry<Integer, Integer>> entries = map.entrySet().stream()
                .sorted((o1, o2) -> o2.getValue() - o1.getValue()).collect(Collectors.toList());
        int[] rs = new int[k];
        for (int i = 0; i < k; i++) {
            rs[i] = entries.get(i).getKey();
        }
        return rs;
    }

}
