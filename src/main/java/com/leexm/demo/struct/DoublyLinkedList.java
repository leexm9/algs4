package com.leexm.demo.struct;

import java.util.LinkedList;

/**
 * 双向链表
 *
 * @author leexm
 * @date 2020-03-27 10:33
 */
public class DoublyLinkedList<T> {

    private Node<T> head;
    private Node<T> tail;

    public DoublyLinkedList() {}

    private void add(T obj) {
        Node<T> node = new Node<>(obj, tail, null);
        if (tail == null) {
            head = node;
        } else {
            tail.next = node;
        }
        tail = node;
    }

    /**
     * 取链表
     * 
     * @param i，1:取首节点，-1:取尾节点
     * @return
     */
    public Node<T> getList(int i) {
        return i > 0 ? head : tail;
    }

    private static class Node<T> {
        private T value;
        private Node<T> pre;
        private Node<T> next;

        public Node(T value, Node<T> pre, Node<T> next) {
            this.value = value;
            this.pre = pre;
            this.next = next;
        }
    }

    public static void main(String[] args) {
        DoublyLinkedList<String> list = new DoublyLinkedList<>();
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        list.add("6");
        Node<String> head = list.getList(1);
        do {
            System.out.print(head.value);
            System.out.print("-");
            head = head.next;
        } while (head != null);
        System.out.println();

        Node<String> tail = list.getList(-1);
        do {
            System.out.print(tail.value);
            System.out.print("-");
            tail = tail.pre;
        } while (tail != null);
    }

}
