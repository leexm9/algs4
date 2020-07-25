package com.leexm.demo.struct;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 红黑树
 *  1、根节点是黑色的
 *  2、每个叶子节点都是黑色的空节点，也就是说，叶子节点不存储数据
 *  3、每个红色节点的两个子节点都是黑色的
 *  4、从每个叶子到根的所有路径上不能有两个连续的红色节点
 *  5、从任一节点到其每个叶子的所有路径都包含相同数目的黑色结点
 *
 * 插入操作，红黑树规定，插入的节点必须是红色的。而且，二叉查找树中新插入的节点都是放在叶子节点上
 *  1、如果插入节点的父节点是黑色的，那我们什么都不用做，它仍然满足红黑树的定义
 *  2、如果插入的节点是根节点，那我们直接改变它的颜色，把它变成黑色就可以了
 *  3、其他情况都会违背红黑树的定义，于是我们就需要进行调整，调整的过程包含两种基础的操作：左右旋转和改变颜色
 *
 * @author leexm
 * @date 2020-04-22 10:38
 */
public class RedBlackBST<T extends Comparable<? super T>> {

    private Node<T> root;

    /** 叶子节点 */
    private final Node<T> leaf = new Node<>();

    public RedBlackBST() {
    }

    public Node<T> getRoot() {
        return root;
    }

    /**
     * 中序遍历
     *
     * @return
     */
    public List<T> inOrder() {
        List<T> list = new ArrayList<>();
        Node<T> node = root;
        Stack<Node<T>> stack = new Stack<>();
        while (node != null || !stack.isEmpty()) {
            if (node != leaf) {
                stack.push(node);
                node = node.left;
            } else {
                node = stack.pop();
                list.add(node.data);
                if (node.right != leaf) {
                    node = node.right;
                }
            }
        }
        return list;
    }

    /**
     * 查找一个元素
     *
     * @param data
     * @return
     */
    public Node<T> find(T data) {
        Node<T> node = root;
        while (node != null && node != leaf) {
            int diff = data.compareTo(node.data);
            if (diff == 0) {
                break;
            } else if (diff < 0) {
                node = node.left;
            } else {
                node = node.right;
            }
        }
        return node == leaf ? null : node;
    }

    /**
     * 向红黑树中插入数据
     *
     * @param data
     * @return
     */
    public void insert(T data) {
        root = insert(root, data);
    }

    private Node<T> insert(Node<T> root, T data) {
        return null;
    }

    private class Node<T extends Comparable<? super T>> {
        private T data;
        private Node<T> left, right;
        private boolean isRed;

        public Node() {
        }

        public Node(T data) {
            this.data = data;
        }
    }

}
