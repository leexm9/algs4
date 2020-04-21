package com.leexm.demo.struct;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 自平衡二叉查找树
 * 自平衡二叉树：
 *      1、任一节点的左、右子树均为 AVL 树
 *      2、任一节点左、右子树高度差的绝对值不超过 1
 * @author leexm
 * @date 2020-04-20 17:47
 */
public class AVLTree<T extends Comparable<? super T>> {

    private AVLNode<T> root;

    public AVLNode<T> find(T data) {
        AVLNode<T> node = root;
        while (node != null) {
            int i = node.data.compareTo(data);
            if (i == 0) {
                break;
            } else if (i > 0) {
                node = node.left;
            } else {
                node = node.right;
            }
        }
        return node;
    }

    /**
     * 将 data 插入到 AVL 树中，并返回调整后的 AVL 树
     *
     * @param data
     * @return
     */
    public void insert(T data) {
        root = insert(root, data);
    }

    /**
     * 将 data 插入 node 所代表的的树，并返回根节点
     *
     * @param node
     * @param data
     * @return
     */
    private AVLNode<T> insert(AVLNode<T> node, T data) {
        if (node == null) {
            return new AVLNode<>(data);
        }
        int i = data.compareTo(node.data);
        // 插入左子树
        if (i < 0) {
            node.left = insert(node.left, data);
        }
        // 插入右子树
        else if (i > 0) {
            node.right = insert(node.right, data);
        } else {
            return node;
        }
        // 更新 node 的高度
        node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
        return balance(node);
    }

    public void delete(T data) {
        if (root == null) {
            return;
        }
        root = delete(root, data);
    }

    private AVLNode<T> delete(AVLNode<T> node, T data) {
        int diff = data.compareTo(node.data);
        if (diff < 0) {
            node.left = delete(node.left, data);
        } else if (diff > 0) {
            node.right = delete(node.right, data);
        } else {
            if (node.left != null && node.right != null) {
                AVLNode<T> min = findMin(node.right);
                node.data = min.data;
                // 转为删除右子树中的最小节点
                node.right = delete(node.right, min.data);
            } else if (node.left != null) {
                return node.left;
            } else {
                return node.right;
            }
        }
        node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
        return balance(node);
    }

    /**
     * 查找 node 树中的最小节点
     * @param node
     * @return
     */
    private AVLNode<T> findMin(AVLNode<T> node) {
        AVLNode<T> p = node;
        while (p != null && p.left != null) {
            p = p.left;
        }
        return p;
    }

    /**
     * 重平衡 node 树
     *
     * @param node
     * @return
     */
    private AVLNode<T> balance(AVLNode<T> node) {
        int diff = getHeight(node.left) - getHeight(node.right);
        // 左子树导致的不平衡
        if (diff > 1) {
            AVLNode<T> left = node.left;
            diff = getHeight(left.left) - getHeight(left.right);
            if (diff < 0) {
                node.left = rightRotation(node.right);
            }
            node = leftRotation(node);
        }
        // 右子树导致的不平衡
        else if (diff < -1) {
            AVLNode<T> right = node.right;
            diff = getHeight(right.left) - getHeight(right.right);
            if (diff > 0) {
                node.right = leftRotation(node.left);
            }
            node = rightRotation(node);
        }
        return node;
    }

    public List<T> inOrder() {
        List<T> list = new ArrayList<>();
        AVLNode<T> node = root;
        Stack<AVLNode<T>> stack = new Stack<>();
        while (node != null || !stack.isEmpty()) {
            if (node != null) {
                stack.push(node);
                node = node.left;
            } else {
                node = stack.pop();
                list.add(node.data);
                node = node.right;
            }
        }
        return list;
    }

    /**
     * 求节点的深度
     *
     * @return
     */
    private int getHeight(AVLNode<T> node) {
        return node == null ? -1 : node.height;
    }

    /**
     * 右旋调整，RR 型
     * node 节点的右子树导致的不平衡
     *
     * @param node
     * @return  调整后这棵树的根节点
     */
    private AVLNode<T> rightRotation(AVLNode<T> node) {
        AVLNode<T> right = node.right;
        node.right = right.left;
        right.left = node;
        // 更新节点高度
        node.height = Math.max(this.getHeight(node.left), this.getHeight(node.right)) + 1;
        right.height = Math.max(this.getHeight(right.left), this.getHeight(right.right)) + 1;
        return right;
    }

    /**
     * 左旋调整，LL 型
     * node 节点的左子树导致的不平衡
     *
     * @param node
     * @return  调整后这棵树的根节点
     */
    private AVLNode<T> leftRotation(AVLNode<T> node) {
        AVLNode<T> left = node.left;
        node.left = left.right;
        left.right = node;
        // 更新节点的高度
        node.height = Math.max(this.getHeight(node.left), this.getHeight(node.right)) + 1;
        left.height = Math.max(this.getHeight(left.left), this.getHeight(left.right)) + 1;
        return left;
    }

    public AVLNode<T> getRoot() {
        return root;
    }

    class AVLNode<T extends Comparable<? super T>> {
        private T data;
        private AVLNode<T> left;
        private AVLNode<T> right;
        // 节点的高度
        private int height;

        public AVLNode(T data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return String.format("AVLNode{data=%s, left=%s, right=%s}", data,
                    left != null ? left.data : null,
                    right != null ? right.data : null);
        }
    }

    public static void main(String[] args) {
        AVLTree<Integer> avlTree = new AVLTree<>();
        avlTree.insert(1);
        avlTree.insert(2);
        avlTree.insert(3);
        avlTree.insert(4);
        avlTree.insert(5);
        avlTree.insert(6);
        avlTree.insert(7);
        avlTree.insert(8);

        System.out.println(avlTree.getRoot());
        System.out.println(avlTree.inOrder());

        avlTree.delete(4);
        System.out.println(avlTree.getRoot());
        System.out.println(avlTree.inOrder());

        avlTree.insert(9);
        avlTree.insert(10);
        System.out.println(avlTree.inOrder());
    }

}
