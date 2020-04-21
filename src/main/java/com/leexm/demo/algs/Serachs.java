package com.leexm.demo.algs;

/**
 * @author leexm
 * @date 2020-04-07 15:00
 */
public class Serachs {

    /**
     * 二分查找
     * 查找 k 出现的位置
     *
     * @return -1 表示没匹配到
     */
    public static int binarySerach(int[] arr, int k) {
        return binary1(arr, 0, arr.length, k);
//        return binary2(arr, k);
    }

    /**
     * 递归查找，查找区间[start, end]。查找不到返回 -1
     *
     * @param arr
     * @param low
     * @param high
     * @param k
     * @return
     */
    private static int binary1(int[] arr, int low, int high, int k) {
        if (low > high) {
            return -1;
        }
        // (start + end) / 2，反之 start、end 比较大，出现溢出
        int mid = low + (high - low) / 2;
        if (arr[mid] == k) {
            return mid;
        } else if (arr[mid] > k) {
            return binary1(arr, low, mid - 1, k);
        } else {
            return binary1(arr, mid + 1, high, k);
        }
    }

    /**
     * 非递归方案
     *
     * @param arr
     * @param k
     * @return
     */
    private static int binary2(int[] arr, int k) {
        int low = 0;
        int high = arr.length - 1;
        while (low <= high) {
            // 加法优先级大于 位运算
            int mid = low + ((high - low) >> 1);
            if (arr[mid] == k) {
                return mid;
            } else if (arr[mid] > k) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return -1;
    }

    /**
     * 查找第一个等于给定值的元素
     *
     * @param arr
     * @param k
     * @return
     */
    public static int serachEq(int[] arr, int k) {
        int low = 0;
        int high = arr.length - 1;
        while (low <= high) {
            int mid = low + ((high - low) >> 1);
            if (arr[mid] < k) {
                low = mid + 1;
            } else if (arr[mid] > k) {
                high = mid - 1;
            } else {
                if (mid == 0 || arr[mid -1] != k) {
                    return mid;
                } else {
                    high = mid - 1;
                }
            }
        }
        return -1;
    }

    /**
     * 查找最后一个等于给定值的元素
     *
     * @param arr
     * @param k
     * @return
     */
    public static int searchEqLast(int[] arr, int k) {
        int low = 0;
        int high = arr.length - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (arr[mid] < k) {
                low = mid + 1;
            } else if (arr[mid] > k) {
                high = mid - 1;
            } else {
                if (mid == high || arr[mid + 1] != k) {
                    return mid;
                }
                low = mid + 1;
            }
        }
        return -1;
    }

    /**
     * 查找第一个大于或等于给定值得元素
     *
     * @param arr
     * @param k
     * @return
     */
    public static int searchGq(int[] arr, int k) {
        int low = 0;
        int high = arr.length - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (arr[mid] >= k) {
                if (mid == 0 || arr[mid - 1] < k) {
                    return mid;
                }
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return -1;
    }

    /**
     * 查找最后一个小于或等于给定值的元素
     *
     * @param arr
     * @param k
     * @return
     */
    public static int searchLqLast(int[] arr, int k) {
        int low = 0;
        int high = arr.length - 1;
        while (low <= high) {
            int mid = low + ((high - low) >> 1);
            if (arr[mid] <= k) {
                if ((mid == arr.length - 1) || arr[mid + 1] > k) {
                    return mid;
                }
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] arr = {1,2,3,4,5,6,7,8,9, 10};
        System.out.println(String.format("查找元素 5 的位置：%d", serachEq(arr, 5)));

        arr = new int[]{1, 4, 5, 5, 6, 6, 6, 6, 7, 13, 14, 34, 61, 72};
        System.out.println(String.format("查找第一个等于 6 的元素的位置：%d", serachEq(arr, 6)));
        System.out.println(String.format("查找最后一个等于 6 的元素的位置：%d", searchEqLast(arr, 6)));
        System.out.println(String.format("查找第一个大于或等于 6 的元素的位置：%d", searchGq(arr, 6)));
        System.out.println(String.format("查找最后一个小于或等于 6 的元素的位置：%d", searchLqLast(arr, 6)));
    }

}
