package com.leexm.demo.struct;

/**
 * 单向链表实现
 *
 * @author leexm
 * @date 2019-09-24 00:00
 */
public class LruCache {

    /** 最大容量 */
    private int capacity;

    /** 链表节点数 */
    private int size = 0;

    private Node head;

    public LruCache(int capacity) {
        this.capacity = capacity;
    }

    public void put(String key, Object value) {
        if (capacity == 1) {
            head = new Node(key, value);
        }

        Node cur = head;
        Node prev = head;

        if (head == null) {
            head = new Node(key, value);
            size++;
            return;
        }

        if (head.key.equals(key)) {
            head.value = value;
            return;
        }

        cur = cur.next;
        while (cur != null) {
            if (cur.key.equals(key)) {
                break;
            }
            prev = cur;
            cur = cur.next;
        }

        // 查找的值存在，更新 value，并移到队首
        if (cur != null) {
            cur.value = value;
            prev.next = cur.next;
            cur.next = head;
            head = cur;
        } else {
            // 查找的值不存在
            Node tmp = new Node(key, value);
            tmp.next = head;
            head = tmp;

            // 移除最后一个节点
            if (size == capacity) {
                cur = head;
                while (cur.next != null && cur.next.next != null) {
                    cur = cur.next;
                }
                cur.next = null;
            } else {
                size++;
            }
        }
    }

    public Object get(String key) {
        Node cur = head;
        Node prev = head;

        if (head == null) {
            return null;
        }

        if (cur.key.equals(key)) {
            return cur.value;
        }

        // 遍历链表查找
        cur = cur.next;
        while (cur != null) {
            if (cur.key.equals(key)) {
                break;
            }
            prev = cur;
            cur = cur.next;
        }

        // 表示链表遍历完
        if (cur == null) {
            return null;
        }

        // 到这里说明找到了节点，移除节点并添加到队首
        prev.next = cur.next;
        cur.next = head;
        head = cur;
        return cur.value;
    }

    private class Node {
        private String key;
        private Object value;
        private Node next;

        Node(String key, Object value) {
            this.key = key;
            this.value = value;
        }
    }

    public static void main(String[] args) {
        LruCache cache = new LruCache( 2 );
        cache.put("1", "A");
        cache.put("2", "B");
        // 返回 A
        System.out.println(cache.get("1"));
        // 去除 key 2
        cache.put("3", "C");
        // 返回 -1 (未找到key 2)
        System.out.println(cache.get("2"));
        // 返回 C
        System.out.println(cache.get("3"));
        // 去除key 1
        cache.put("4", "D");
        // 返回 -1 (未找到 key 1)
        System.out.println(cache.get("1"));
        // 返回 C
        System.out.println(cache.get("3"));
        //返回 D
        System.out.println(cache.get("4"));
    }

}
