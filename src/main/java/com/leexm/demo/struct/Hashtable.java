package com.leexm.demo.struct;

/**
 * 使用数组+链表，实现散列表
 *
 * @author leexm
 * @date 2020-04-09 17:15
 */
public class Hashtable<K, V> {

    /**
     * 初始容量大小
     */
    private static final int DEFAULT_INIT_CAPACITY = 8;

    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    private Node<K, V>[] tables;

    /**
     * 元素个数
     */
    private int size = 0;

    /**
     * 扩容阈值
     */
    private int threshold = 0;

    public Hashtable() {
        tables = (Node<K, V>[]) new Node[DEFAULT_INIT_CAPACITY];
        threshold = (int) (DEFAULT_INIT_CAPACITY * DEFAULT_LOAD_FACTOR);
    }

    public V get(K key) {
        int n = tables.length;
        int hash = hash(key);
        int i = (n - 1) & hash;

        Node<K, V> e = null;
        Node<K, V> slot = tables[i];
        if (slot != null) {
            Node<K, V> next = slot;
            while (next != null) {
                // 判断槽位节点
                if (next.hash == hash && (next.key == key || (key != null && key.equals(next.key)))) {
                    e = next;
                    break;
                }
                next = next.next;
            }
        }
        return e == null ? null : e.value;
    }

    public V put(K key, V value) {
        // 槽位下标
        int n = tables.length;
        int hash = hash(key);
        int i = (n - 1) & hash;

        Node<K, V> slot = tables[i];
        if (slot == null) {
            tables[i] = new Node<>(hash, key, value, null);
        } else {
            // 同位素，即 hash、key 值相同的节点
            Node<K, V> e = null;
            while (slot != null) {
                if (slot.hash == hash && (slot.key == key || (key != null && key.equals(slot.key)))) {
                    e = slot;
                    break;
                }
                if (slot.next == null) {
                    slot.next = new Node<>(hash, key, value, null);
                    break;
                }
                slot = slot.next;
            }
            if (e != null) {
                V oldValue = e.value;
                e.value = value;
                return oldValue;
            }
        }

        // 超过阈值，扩容
        if (++size > threshold) {
            resize();
        }
        return null;
    }

    public V remove(K key) {
        int n = tables.length;
        int hash = hash(key);
        int i = (n - 1) & hash;

        Node<K, V> e = null;
        Node<K, V> slot = tables[i];
        if (slot != null) {
            // 判断槽位节点
            if (slot.hash == hash && (slot.key == key || (key != null && key.equals(slot.key)))) {
                e = slot;
            } else if (slot.next != null) {
                Node<K, V> next = slot.next;
                while (next != null) {
                    if (next.hash == hash && (next.key == key || (key != null && key.equals(next.key)))) {
                        e = next;
                        break;
                    }
                    slot = next;
                    next = next.next;
                }
            }
        }
        if (e != null) {
            // 如果删除的是第一个节点
            if (e == tables[i]) {
                tables[i] = e.next;
            } else {    // 如果删除的是中间节点
                slot.next = e.next;
            }
            e.next = null;
            size--;
        }
        return e == null ? null : e.value;
    }

    /**
     * 扩容，倍增
     *
     * @return
     */
    private void resize() {
        int oldCap = tables.length;
        int newCap = oldCap << 1;
        threshold = newCap - (oldCap >> 1);
        Node<K, V>[] newTables = (Node<K, V>[]) new Node[newCap];
        for (int i = 0; i < oldCap; i++) {
            Node<K, V> e = tables[i];
            if (e != null) {
                tables[i] = null;
                if (e.next == null) {
                    newTables[e.hash & (newCap - 1)] = e;
                } else {
                    // 链表拆分成两个链表
                    Node<K, V> loHead = null, loTail = null;
                    Node<K, V> hiHead = null, hiTail = null;
                    Node<K, V> next = e;
                    while (next != null) {
                        // 低位链表
                        if ((next.hash & oldCap) == 0) {
                            if (loTail == null) {
                                loHead = next;
                            } else {
                                loTail.next = next;
                            }
                            loTail = next;
                        } else {        // 高位链表
                            if (hiTail == null) {
                                hiHead = next;
                            } else {
                                hiTail.next = next;
                            }
                            hiTail = next;
                        }
                        next = next.next;
                    }
                    // 将高、低位链表放入新数组响应位置
                    if (loHead != null) {
                        newTables[i] = loHead;
                        // 切断高、低两个链表间的联系
                        loTail.next = null;
                    }
                    if (hiHead != null) {
                        newTables[i + oldCap] = hiHead;
                        hiTail.next = null;
                    }
                }
            }
        }
        tables = newTables;
    }

    /**
     * 取 key 值得 hash 值，参考 HashMap
     *
     * @param key
     * @return
     */
    private int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    class Node<K, V> {
        final int hash;     // key 的 hash 值，避免重复计算
        final K key;
        V value;
        Node<K, V> next;

        Node(int hash, K key, V value, Node<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    public static void main(String[] args) {
        Hashtable<Integer, Integer> hashtable = new Hashtable<>();
        System.out.println(hashtable.remove(2));
        hashtable.put(22,83);
        hashtable.put(39,4);
        hashtable.put(34,88);
        hashtable.put(72,99);
        System.out.println(hashtable.remove(33));
        hashtable.put(58,77);
        hashtable.put(23,61);
        System.out.println(hashtable.remove(34));
        System.out.println(hashtable.remove(66));
        System.out.println(hashtable.remove(90));
        hashtable.put(72,83);
        hashtable.put(50,98);
        hashtable.put(93,97);
        hashtable.put(74,95);
        System.out.println(hashtable.remove(81));
        hashtable.put(56,1);
        hashtable.put(86,80);
        hashtable.put(93,91);
        hashtable.put(13,1);
        System.out.println(hashtable.remove(93));
        hashtable.put(63,11);
        hashtable.put(62,63);
        System.out.println(hashtable.get(63));
        hashtable.put(71,98);
        hashtable.put(97,96);
        hashtable.put(65,47);
        System.out.println(hashtable.remove(93));
        hashtable.put(30,78);
        hashtable.put(31,40);
        hashtable.put(67,86);
        hashtable.put(84,11);
        hashtable.put(3,19);
        hashtable.put(30,97);
        hashtable.put(3,36);
        System.out.println(hashtable.get(63));
        hashtable.put(92,43);
        System.out.println(hashtable.remove(71));
        System.out.println(hashtable.remove(86));
        hashtable.put(77,91);
        hashtable.put(18,29);
        hashtable.put(75,44);
        System.out.println(hashtable.get(59));
        hashtable.put(35,81);
        System.out.println(hashtable.remove(58));
        hashtable.put(12,69);
        System.out.println(hashtable.remove(58));
        System.out.println(hashtable.get(86));

        System.out.println(hashtable.remove(2));
        hashtable.put(3,11);
        hashtable.put(4,13);
        hashtable.put(15,6);
        hashtable.put(6,15);
        hashtable.put(8,8);
        hashtable.put(11,0);
        System.out.println(hashtable.get(11));
    }

}
