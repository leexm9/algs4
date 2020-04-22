package com.leexm.demo.struct;

import java.util.Comparator;

/**
 * 最大堆 1、是一个完全二叉树 2、堆中每一个节点的值都必须大于等于其子树中每个节点的值
 *
 * @author leexm
 * @date 2020-04-22 15:25
 */
public class MaxHeap<T extends Comparable<? super T>> {

    /** 默认数组长度 */
    private static final int DEFAULT_CAPACITY = 11;

    /**
     * 使用数组保存数据 0 位置不使用，空元素
     */
    private Object[] elements;

    /** 堆中数据的个数 */
    private int size = 0;

    public MaxHeap() {
        this.elements = new Object[DEFAULT_CAPACITY];
    }

    public MaxHeap(int capacity, Comparator<? super T> comparator) {
        this.elements = new Object[capacity + 1];
    }

    /**
     * 插入元素
     *
     * @param data
     */
    public void insert(T data) {
        if (ifFull()) {
            return;
        }
        size++;
        elements[size] = data;
        // 向上调整数据位置
        siftUp(size, data);
    }

    /**
     * 获取堆中最大元素
     *
     * @return
     */
    public T max() throws NoSuchMethodException {
        if (size < 1) {
            throw new NoSuchMethodException("heap is empty");
        }
        return (T)elements[1];
    }

    public T deleteMax() throws NoSuchMethodException {
        T max = null;
        if (size < 1) {
            throw new NoSuchMethodException("heap is empty");
        } else if (size == 1) {
            max = (T)elements[1];
            elements[size--] = null;
        } else {
            max = (T)elements[1];
            // 将最后一个元素放入堆顶，并进行下沉操作
            T data = (T)elements[size];
            elements[size--] = null;
            elements[1] = data;
            siftDown(1, data);
        }
        return max;
    }

    /**
     * 判断是否已满
     *
     * @return
     */
    private boolean ifFull() {
        // 0 位不使用
        return size >= elements.length - 1;
    }

    /**
     * 向上调整数据的位置
     *
     * @param index
     * @param data
     */
    private void siftUp(int index, T data) {
        while (index > 1) {
            // 父元素位置，parent = 0 就可以终结，故 index > 1 即可
            int parent = index / 2;
            Object e = elements[parent];
            if (data.compareTo((T)e) > 0) {
                elements[parent] = elements[index];
                elements[index] = e;
                index = parent;
            } else {
                break;
            }
        }
    }

    /**
     * 向下调整数据的位置
     *
     * @param index
     * @param data
     */
    private void siftDown(int index, T data) {
        int limit = size / 2;
        while (index <= limit) {
            // 两个子元素位置
            int child = index * 2;
            T c = (T)elements[child];
            int right = child + 1;
            // 取左右子节点中较大的
            if (child <= size && right <= size && c.compareTo((T)elements[right]) < 0) {
                child = right;
                c = (T)elements[right];
            }
            // 小于子节点，与子节点调换
            if (data.compareTo(c) < 0) {
                elements[index] = c;
                elements[child] = data;
                index = child;
            } else {
                break;
            }
        }
    }

    public static void main(String[] args) throws NoSuchMethodException {
        MaxHeap<Integer> heap = new MaxHeap<>();
        heap.insert(63);
        heap.insert(34);
        heap.insert(182);
        heap.insert(88);
        heap.insert(19);

        System.out.println(heap.deleteMax());
        System.out.println(heap.deleteMax());
        System.out.println(heap.deleteMax());
        System.out.println(heap.deleteMax());
        System.out.println(heap.deleteMax());

        heap.insert(12);
        System.out.println(heap.deleteMax());
    }

}
