package com.leexm.demo.leetcode;

import com.leexm.demo.InputUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * leetcode - 234
 * 使用单链表解决回文判断
 *
 * @author leexm
 * @date 2020-03-27 09:47
 */
public class Palindrome {

    public boolean isPalindrome(ListNode head) {
        if (head == null || head.next == null) {
            return true;
        }
        // 快慢指针定位中间节点
        ListNode fast = head;
        ListNode slow = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }

        // 反转后半段
        ListNode pre = null;
        ListNode cur = slow;
        while (slow.next != null) {
            slow = slow.next;
            cur.next = pre;
            pre = cur;
            cur = slow;
        }
        // 处理最后一个节点
        slow.next = pre;

        boolean flag = true;
        do {
            if (head.val != slow.val) {
                flag = false;
                break;
            }
            head = head.next;
            slow = slow.next;
        } while (slow != null);
        return flag;
    }

    private static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    private static ListNode stringToListNode(String input) {
        // Generate array from the input
        int[] nodeValues = InputUtils.string2IntegerArray(input);

        // Now convert that list into linked list
        ListNode dummyRoot = new ListNode(0);
        ListNode ptr = dummyRoot;
        for(int item : nodeValues) {
            ptr.next = new ListNode(item);
            ptr = ptr.next;
        }
        return dummyRoot.next;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while ((line = in.readLine()) != null) {
            ListNode head = stringToListNode(line);
            boolean ret = new Palindrome().isPalindrome(head);
            System.out.print(ret);
        }
    }

}
