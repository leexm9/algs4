package com.leexm.demo.struct;

/**
 * 单向链表
 *
 * @author leexm
 * @date 2020-03-27 10:04
 */
public class SinglyLinkedList<T> {

    /** 头节点 */
    private Node<T> head;

    /**
     * 尾节点，只在添加节点时使用
     */
    private Node<T> tail;

    public SinglyLinkedList() {
    }

    private void add(T obj) {
        Node<T> node = new Node<>(obj, null);
        if (tail == null) {
            head = node;
        } else {
            tail.next = node;
        }
        tail = node;
    }

    public Node<T> getList() {
        return head;
    }

    /**
     * 反转单向链表
     *
     * @param head
     * @return
     */
    public static <T> Node<T> reverse(Node<T> head) {
        Node<T> pre = null;
        Node<T> cur = head;
        while (cur != null) {
            head = head.next;
            cur.next = pre;
            pre = cur;
            cur = head;
        }
        return pre;
    }

    /**
     * 检测单向链表中是否是环
     *
     * @param head
     * @return true:存在环
     */
    public static <T> boolean checkCircle(Node<T> head) {
        if (head == null) {
            return false;
        }
        Node<T> fast = head.next;
        Node<T> slow = head;
        boolean flag = false;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    private static class Node<T> {
        private T value;
        private Node next;

        private Node(T value, Node next) {
            this.value = value;
            this.next = next;
        }
    }

    public static void main(String[] args) {
        SinglyLinkedList<String> list = new SinglyLinkedList<>();
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        list.add("6");
        Node<String> head = list.getList();
        do {
            System.out.print(head.value);
            System.out.print("-");
            head = head.next;
        } while (head != null);
        System.out.println();

        Node<String> reverse = SinglyLinkedList.reverse(list.getList());
        do {
            System.out.print(reverse.value);
            System.out.print("-");
            reverse = reverse.next;
        } while (reverse != null);
    }

}
