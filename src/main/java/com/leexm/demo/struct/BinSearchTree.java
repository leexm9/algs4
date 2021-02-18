package com.leexm.demo.struct;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 二叉查找树
 *
 * @author leexm
 * @date 2020-04-10 14:57
 */
public class BinSearchTree<T extends Comparable<? super T>> {

    private BinTree.Node<T> root;

    public BinTree.Node<T> find(T data) {
        if (root == null) {
            return null;
        }
        BinTree.Node<T> node = root;
        while (node != null) {
            int i = data.compareTo(node.data);
            if (i == 0) {
                break;
            } else if (i < 0) {
                node = node.left;
            } else {
                node = node.right;
            }
        }
        return node;
    }

    public void insert(T data) {
        if (root == null) {
            root = new BinTree.Node<>(data);
        }
        BinTree.Node<T> node = root;
        while (node != null) {
            int i = data.compareTo(node.data);
            if (i == 0) {
                break;
            } else if (i < 0) {
                if (node.left == null) {
                    node.left = new BinTree.Node<>(data);
                } else {
                    node = node.left;
                }
            } else {
                if (node.right == null) {
                    node.right = new BinTree.Node<>(data);
                } else {
                    node = node.right;
                }
            }
        }
    }

    public void delete(T data) {
        if (root == null) {
            return;
        }
        // data 对应的节点
        BinTree.Node<T> node = root;
        BinTree.Node<T> parent = null;
        // 表示查找元素的节点是左子节点还是右子节点
        boolean isLeft = false;
        while (node != null && !node.data.equals(data)) {
            int i = data.compareTo(node.data);
            parent = node;
            if (i < 0) {
                node = node.left;
                isLeft = true;
            } else {
                node = node.right;
                isLeft = false;
            }
        }
        // 没有相应的节点，直接返回
        if (node == null) {
            return;
        }

        // 如果节点左右子节点都不为空
        if (node.left != null && node.right != null) {
            // 查找右子树中最小的节点
            BinTree.Node<T> min = node.right;
            BinTree.Node<T> per = node;
            while (min.left != null) {
                per = min;
                min = min.left;
            }
            node.data = min.data;
            // 将右子树最小节点赋值给 node，下面从删除 node 变成删除右子树的最小节点了
            node = min;
            parent = per;
            isLeft = true;
        }

        BinTree.Node<T> child = null;
        if (node.left != null) {
            child = node.left;
            node.left = null;
        } else if (node.right != null) {
            child = node.right;
            node.right = null;
        }

        if (parent == null) {
            root = child;
        } else if (isLeft) {
            parent.left = child;
        } else {
            parent.right = child;
        }
    }

    public BinTree.Node<T> findMin() {
        BinTree.Node<T> node = root;
        while (node != null && node.left != null) {
            node = node.left;
        }
        return node.left != null ? node.left : node;
    }

    public BinTree.Node<T> findMax() {
        BinTree.Node<T> node = root;
        while (node != null && node.right != null) {
            node = node.right;
        }
        return node.right != null ? node.right : node;
    }

    public BinTree.Node<T> getRoot() {
        return root;
    }

    public static void main(String[] args) {
        BinSearchTree searchTree = new BinSearchTree();
        searchTree.insert(33);
        searchTree.insert(17);
        searchTree.insert(13);
        searchTree.insert(50);
        searchTree.insert(34);
        searchTree.insert(18);
        searchTree.insert(16);
        searchTree.insert(58);
        searchTree.insert(51);
        searchTree.insert(25);
        searchTree.insert(19);
        searchTree.insert(66);
        searchTree.insert(27);

        System.out.println("----------中序遍历----------");
        List<Integer> list = new ArrayList<>();
        BinTree binTree = new BinTree();
        binTree.inOrder(searchTree.getRoot(), list);
        System.out.println(list);

        System.out.println("---------insert-----------");
        searchTree.insert(55);
        list.clear();
        binTree.inOrder(searchTree.getRoot(), list);
        System.out.println(list);

        System.out.println("---------find-----------");
        System.out.println(searchTree.find(25));
        System.out.println(searchTree.find(36));

        System.out.println("---------delete-----------");
        searchTree.delete(13);
        searchTree.delete(18);
        searchTree.delete(55);
        list.clear();
        binTree.inOrder(searchTree.getRoot(), list);
        System.out.println(list);

        // 删除根节点
        searchTree.delete(33);
        list.clear();
        binTree.inOrder(searchTree.getRoot(), list);
        System.out.println(list);
        System.out.println(searchTree.getRoot().data);

        System.out.println("---------top/top-----------");
        System.out.println(searchTree.findMin());
        System.out.println(searchTree.findMax());
    }

}
