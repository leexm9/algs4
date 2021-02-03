package com.leexm.demo.leetcode;

/**
 * 二分查找
 *
 * @author leexm
 * @date 2021-02-01 23:07
 */
public class BinarySearch {

    public static void main(String[] args) {
        BinarySearch binarySearch = new BinarySearch();
        int[] nums = {5, 6, 6, 7, 8, 8, 9};
        System.out.println(binarySearch.search(nums, 8));
    }

    /**
     * 69. x 的平方根
     * https://leetcode-cn.com/problems/sqrtx/
     *
     * @param x
     * @return
     */
    public int mySqrt(int x) {
        if (x == 0 || x == 1) {
            return x;
        }
        int left = 1, right = x, mid, sqrt;
        while (left <= right) {
            mid = left + (right - left) / 2;
            sqrt = x / mid;
            if (sqrt == mid) {
                return mid;
            } else if (mid > sqrt) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return right;
    }


    /**
     * 34. 在排序数组中查找元素的第一个和最后一个位置
     * https://leetcode-cn.com/problems/find-first-and-last-position-of-element-in-sorted-array/
     *
     * @param nums
     * @param target
     * @return
     */
    public int[] searchRange(int[] nums, int target) {
        int left = this.searchFirst(nums, target);
        int right = this.searchLast(nums, target);
        if (left == -1 || target == -1) {
            return new int[]{-1, -1};
        }
        return new int[]{left, right};
    }

    /**
     * 查找第一次出现的位置
     *
     * @param nums
     * @param target
     * @return
     */
    private int searchFirst(int[] nums, int target) {
        if (nums.length < 1) {
            return -1;
        }
        int left = 0, right = nums.length - 1, mid = 0;
        while (left <= right) {
            mid = left + (right - left) / 2;
            if (nums[mid] > target) {
                right = mid - 1;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                if (mid == 0 || nums[mid - 1] < target) {
                    break;
                }
                right = mid - 1;
            }
        }
        return nums[mid] == target ? mid : -1;
    }

    /**
     * 查找最后一次出现的位置
     *
     * @param nums
     * @param target
     * @return
     */
    private int searchLast(int[] nums, int target) {
        if (nums.length < 1) {
            return -1;
        }
        int left = 0, right = nums.length - 1, mid = 0;
        while (left <= right) {
            mid = left + (right - left) / 2;
            if (nums[mid] > target) {
                right = mid - 1;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                if (mid == nums.length - 1 || nums[mid + 1] > target) {
                    break;
                }
                left = mid + 1;
            }
        }
        return nums[mid] == target ? mid : -1;
    }


    /**
     * 81. 搜索旋转排序数组 II
     * https://leetcode-cn.com/problems/search-in-rotated-sorted-array-ii/
     *
     * @param nums
     * @param target
     * @return
     */
    public boolean search(int[] nums, int target) {
        int left = 0, right = nums.length - 1, mid = 0;
        while (left <= right) {
            mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                return true;
            }

            // 无法判定那一部分是有序的，移动左指针
            if (nums[left] == nums[mid]) {
                left++;
            } else if (nums[mid] <= nums[right]) {
                // 右区间有序的
                if (nums[mid] < target && nums[right] >= target) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            } else if (nums[left] <= nums[mid]) {
                // 左区间有序
                if (nums[left] <= target && nums[mid] > target) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }
        }
        return false;
    }

}
