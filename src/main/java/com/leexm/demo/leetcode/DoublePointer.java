package com.leexm.demo.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * 双指针问题
 * 1. 双指针主要用于遍历数组，两个指针指向不同的元素，从而协同完成任务。也可以延伸到多个数组的多个指针。
 * 2. 若两个指针指向同一数组，遍历方向相同且不会相交，则也称为滑动窗口(两个指针包围的区域即为当前的窗口)，经常用于区间搜索。
 * 3. 若两个指针指向同一数组，但是遍历方向相反，则可以用来进行搜索，待搜索的数组往往是排好序的。
 *
 * @author leexm
 * @date 2021-01-31 16:33
 */
public class DoublePointer {

    public static void main(String[] args) {
        String s = "aa", t = "aa";
        DoublePointer doublePointer = new DoublePointer();
        String str = doublePointer.minWindow(s, t);
        System.out.println(str);
    }

    /**
     * 167. 两数之和 II - 输入有序数组
     * https://leetcode-cn.com/problems/two-sum-ii-input-array-is-sorted/
     *
     * @param numbers
     * @param target
     * @return
     */
    public int[] twoSum(int[] numbers, int target) {
        return this.twoSum(numbers, target, 0, numbers.length - 1);
    }

    private int[] twoSum(int[] numbers, int target, int start, int end) {
        int mid = start + (end - start) / 2;
        if (numbers[mid] > target) {
            this.twoSum(numbers, target, start, mid);
        } else if (numbers[mid] + numbers[end] < target) {
            this.twoSum(numbers, target, mid + 1, end);
        }
        while (start < end) {
            int sum = numbers[start] + numbers[end];
            if (sum < target) {
                start++;
            } else if (sum > target) {
                end--;
            } else {
                return new int[]{start, end};
            }
        }
        return new int[]{};
    }


    /**
     * 88. 合并两个有序数组
     * https://leetcode-cn.com/problems/merge-sorted-array/
     *
     * @param nums1
     * @param m
     * @param nums2
     * @param n
     */
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int i = (m--) + (n--) - 1;
        while (n >= 0 && m >= 0) {
            if (nums1[m] > nums2[n]) {
                nums1[i--] = nums1[m--];
            } else {
                nums1[i--] = nums2[n--];
            }
        }
        while (n >= 0) {
            nums1[i--] = nums2[n--];
        }
    }


    /**
     * 142. 环形链表 II
     * https://leetcode-cn.com/problems/linked-list-cycle-ii/
     *
     * @param head
     * @return
     */
    public ListNode detectCycle(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        do {
            if (fast == null || fast.next == null) {
                return null;
            }
            fast = fast.next.next;
            slow = slow.next;
        } while (fast != slow);
        // 存在环
        fast = head;
        while (fast != slow) {
            fast = fast.next;
            slow = slow.next;
        }
        return fast;
    }


    /**
     * 76. 最小覆盖子串
     * https://leetcode-cn.com/problems/minimum-window-substring/
     *
     * @param s
     * @param t
     * @return
     */
    public String minWindow(String s, String t) {
        if (s.length() < t.length()) {
            return "";
        }

        Map<Character, Integer> charMap = new HashMap<>();
        /**
         * 使用 hash：
         * 一方面便于查找待查的字符；
         * 另一方面标记各个字符出现的次数即需要在子串中查找的个数
         */
        for (char ch : t.toCharArray()) {
            Integer num = charMap.get(ch);
            charMap.put(ch, num == null ? 1 : num + 1);
        }
        // 最小字符
        String findStr = "";
        // 找到字符的个数
        int findChars = 0;
        int left = 0, right = 0;
        do {
            char lch = s.charAt(left);
            // 移动左指针
            if (!charMap.containsKey(lch)) {
                left++;
                if (right < left) {
                    right = left;
                }
            } else {
                // 找到子串，判断左边下一个字符 或者 右指针已经到头了
                if (findChars == t.length() || right >= s.length()) {
                    Integer num = charMap.get(lch);
                    if (num != null) {
                        // 注意，字符次数为 0 时的意义
                        if (num >= 0) {
                            findChars--;
                        }
                        charMap.put(lch, ++num);
                    }
                    left++;
                } else {
                    char rch = s.charAt(right++);
                    Integer num = charMap.get(rch);
                    if (num != null) {
                        if (num > 0) {
                            findChars++;
                        }
                        charMap.put(rch, --num);
                    }
                }
            }
            // 子串
            if (findChars == t.length() && (findStr.length() == 0 || findStr.length() > (right - left))) {
                findStr = s.substring(left, right);
            }
        } while (right < s.length() || left < s.length());

        return findStr;

    }

}
