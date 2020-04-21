package com.leexm.demo.struct;

import java.util.*;

/**
 * 二叉树相关通用方法
 *
 * @author leexm
 * @date 2020-04-10 14:57
 */
public class BinTree<T> {

    /**
     * 前序遍历，递归
     *
     * @param root 树根节点
     * @param list
     */
    public void preOrder(Node<T> root, List<T> list) {
        if (root == null) {
            return;
        }
        list.add(root.data);
        preOrder(root.left, list);
        preOrder(root.right, list);
    }

    /**
     * 前序遍历，非递归
     *
     * @param root
     * @param list
     */
    public void preOrder2(Node<T> root, List<T> list) {
        Stack<Node> stack = new Stack<>();
        Node<T> node = root;
        while (node != null || !stack.isEmpty()) {
            if (node != null) {
                list.add(node.data);
                stack.push(node);
                node = node.left;
            } else {
                node = stack.pop();
                node = node.right;
            }
        }
    }

    /**
     * 中序遍历，递归
     *
     * @param root
     * @param list
     */
    public void inOrder(Node<T> root, List<T> list) {
        if (root == null) {
            return;
        }
        inOrder(root.left, list);
        list.add(root.data);
        inOrder(root.right, list);
    }

    /**
     * 中序遍历，非递归
     *
     * @param root
     * @param list
     */
    public void inOrder2(Node<T> root, List<T> list) {
        Stack<Node> stack = new Stack<>();
        Node<T> node = root;
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
    }

    /**
     * 后序遍历，递归
     *
     * @param root
     * @param list
     * @return
     */
    public void postOrder(Node<T> root, List<T> list) {
        if (root == null) {
            return;
        }
        postOrder(root.left, list);
        postOrder(root.right, list);
        list.add(root.data);
    }

    /**
     * 后序遍历，非递归
     *
     * @param root
     * @param list
     */
    public void postOrder2(Node<T> root, List<T> list) {
        if (root == null) {
            return;
        }
        Stack<Node> stack = new Stack<>();
        // 前一次访问的节点
        Node<T> pre = null;
        // 当前节点
        Node<T> cur = null;

        stack.push(root);
        while (!stack.isEmpty()) {
            cur = stack.peek();
            /**
             * 当前节点出栈条件
             * 1、不存在左右子节点
             * 2、存在左右孩子节点，但是左右子节点都被访问过
             */
            if ((cur.left == null && cur.right == null)
                    || (pre != null && (pre == cur.left || pre == cur.right))) {
                list.add(cur.data);
                stack.pop();
                pre = cur;
            } else {
                // 这里，先右再左
                if (cur.right != null) {
                    stack.push(cur.right);
                }
                if (cur.left != null) {
                    stack.push(cur.left);
                }
            }
        }
    }

    /**
     * 层序遍历
     *
     * @param root
     * @param list
     * @return
     */
    public void layerOrder(Node<T> root, List<T> list) {
        if (root == null) {
            return;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        do {
            Node<T> tmp = queue.poll();
            if (tmp != null) {
                list.add(tmp.data);
                if (tmp.left != null) {
                    queue.add(tmp.left);
                }
                if (tmp.right != null) {
                    queue.add(tmp.right);
                }
            }
        } while (!queue.isEmpty());
    }

    /**
     * 二叉树的层数(根节点为第1层)
     *
     * @param root
     * @return
     */
    public int layer(Node<T> root) {
        if (root == null) {
            return 0;
        }
        // 队列
        Queue<Node> queue = new LinkedList<>();
        // 层节点计数，count 表示当前层的节点数，preCount 表示上一层的节点
        int count = 0, preCount = 0;
        // 树的层数计数
        int level = 0;
        queue.add(root);
        preCount = 1;
        while (!queue.isEmpty()) {
            Node<T> tmp = queue.poll();
            preCount--;
            if (tmp.left != null) {
                queue.add(tmp.left);
                count++;
            }
            if (tmp.right != null) {
                queue.add(tmp.right);
                count++;
            }
            if (preCount == 0) {
                preCount = count;
                count = 0;
                level++;
            }
        }
        return level;
    }

    /**
     * 二叉树最大深度 —— 根节点到最远叶子节点最远路径上的节点数，递归
     * 节点深度：根节点到该节点的边数
     *
     * @param root
     * @return
     */
    public int maxDepth(Node<T> root) {
        if (root == null) {
            return -1;
        }

        // 如果两个子节点都非空，迭代左右子节点
        if (root.left != null && root.right != null) {
            return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
        } else if (root.left != null) {
            return 1 + maxDepth(root.left);
        } else if (root.right != null) {
            return 1 + maxDepth(root.right);
        } else {       // 左右子节点都是空，该节点深度就是 0
            return 0;
        }
    }

    /**
     * 二叉树最大深度，非递归
     * 最大深度问题转为树的层数问题
     *
     * @param root
     * @return
     */
    public int maxDepth2(Node<T> root) {
        if (root == null) {
            return -1;
        }
        // 节点深度从 0 开始计数
        return (this.layer(root) - 1);
    }

    /**
     * 二叉树最小深度——从根节点到最近叶子节点的最短路径上的节点数，递归
     *
     * @param root
     * @return
     */
    public int minDepth(Node<T> root) {
        if (root == null) {
            return -1;
        }

        // 如果两个子节点都非空，迭代左右子节点
        if (root.left != null && root.right != null) {
            return 1 + Math.min(minDepth(root.left), minDepth(root.right));
        } else if (root.left != null) {
            return 1 + minDepth(root.left);
        } else if (root.right != null) {
            return 1 + minDepth(root.right);
        } else {                // 左右子节点都是空，该节点深度就是 0
            return 0;
        }
    }

    /**
     * 二叉树最小深度，非递归
     *
     * @param root
     * @return
     */
    public int minDepth2(Node<T> root) {
        if (root == null) {
            return -1;
        }
        Queue<Node> queue = new LinkedList<>();
        // 层中节点计数，count 表示当前层的节点数，preCount 表示上一层的节点
        int count = 0, preCount = 0;
        // 层数
        int layer = 0;

        queue.add(root);
        preCount = 1;
        while (!queue.isEmpty()) {
            Node<T> tmp = queue.poll();
            preCount--;

            // 节点的左右子节点都是空，说明是叶子节点
            if (tmp.left == null && tmp.right == null) {
                // 标记空节点所在的层
                layer++;
                break;
            } else {
                if (tmp.left != null) {
                    queue.add(tmp.left);
                    count++;
                }
                if (tmp.right != null) {
                    queue.add(tmp.right);
                    count++;
                }
            }
            if (preCount == 0) {
                preCount = count;
                count = 0;
                layer++;
            }
        }
        return --layer;
    }

    /**
     * 二叉树节点的数量
     *
     * @param root
     * @return
     */
    public int size(Node<T> root) {
        if (root == null) {
            return 0;
        }
        Queue<Node> queue = new LinkedList<>();
        int count = 0;
        queue.add(root);
        while (!queue.isEmpty()) {
            Node<T> tmp = queue.poll();
            count++;
            if (tmp.left != null) {
                queue.add(tmp.left);
            }
            if (tmp.right != null) {
                queue.add(tmp.right);
            }
        }
        return count;
    }

    /**
     * 最大深度的路径
     *
     * @param root
     * @return
     */
    public List<T> maxDepthPath(Node<T> root) {
        if (root == null) {
            return null;
        }
        List<T> path = new LinkedList<>();
        // 根节点的最大深度
        int depth = this.maxDepth(root);
        Node<T> node = root;
        while (depth >= 0) {
            path.add(node.data);
            depth--;
            int ldepth = this.maxDepth(node.left);
            if (ldepth == depth) {
                node = node.left;
            } else {
                node = node.right;
            }
        }
        return path;
    }

    /**
     * 最小深度的路径
     *
     * @param root
     * @return
     */
    public List<T> minDepthPath(Node<T> root) {
        if (root == null) {
            return null;
        }
        List<T> path = new LinkedList<>();
        int depth = this.minDepth(root);
        Node<T> node = root;
        while (depth >= 0) {
            path.add(node.data);
            depth--;
            int ldepth = this.minDepth(node.left);
            if (ldepth == depth) {
                node = node.left;
            } else {
                node = node.right;
            }
        }
        return path;
    }

    static class Node<T> {
        T data;
        Node<T> left;
        Node<T> right;

        public Node() {
        }

        Node(T data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return String.format("Node{data=%s, left=%s, right=%s}", data,
                    left != null ? left.data : null,
                    right != null ? right.data : null);
        }
    }

    public static void main(String[] args) {
        Node<String> root = new Node<>("A");
        Node<String> b = root.left = new Node<>("B");
        Node<String> c = root.right = new Node<>("C");
        Node<String> d = b.left = new Node<>("D");
        Node<String> e = b.right = new Node<>("E");
        Node<String> f = c.left = new Node<>("F");
        Node<String> g = c.right = new Node<>("G");
        d.left = new Node<>("H");
        d.right = new Node<>("I");
        g.left = new Node<>("J");
        g.right = new Node<>("K");
        g.left.right = new Node<>("L");

        List<String> list = new ArrayList<>();
        System.out.println("------- 前序遍历 -------");
        BinTree<String> binTree = new BinTree<>();
        binTree.preOrder(root, list);
        System.out.println(String.format("递归：%s", list));

        list.clear();
        binTree.preOrder2(root, list);
        System.out.println(String.format("非递归：%s", list));

        System.out.println("\r\n------- 中序遍历 -------");
        list.clear();
        binTree.inOrder(root, list);
        System.out.println(String.format("递归：%s", list));

        list.clear();
        binTree.inOrder2(root, list);
        System.out.println(String.format("非递归：%s", list));

        System.out.println("\r\n------- 后序遍历 -------");
        list.clear();
        binTree.postOrder(root, list);
        System.out.println(String.format("递归：%s", list));

        list.clear();
        binTree.postOrder2(root, list);
        System.out.println(String.format("非递归：%s", list));

        System.out.println("\r\n------- 层序遍历 -------");
        list.clear();
        binTree.layerOrder(root, list);
        System.out.println(list);

        System.out.println("\r\n------- 树的层数 -------");
        System.out.println(String.format("层数：%d", binTree.layer(root)));

        System.out.println("\r\n------- 最大深度 -------");
        System.out.println(String.format("递归：%d", binTree.maxDepth(root)));
        System.out.println(String.format("非递归：%d", binTree.maxDepth2(root)));
        System.out.println(String.format("最大深度路径：%s", binTree.maxDepthPath(root)));

        System.out.println("\r\n------- 最小深度 -------");
        System.out.println(String.format("递归：%d", binTree.minDepth(root)));
        System.out.println(String.format("非递归：%d", binTree.minDepth2(root)));
        System.out.println(String.format("最小深度路径：%s", binTree.minDepthPath(root)));

        System.out.println("\r\n------- 树的节点数量 -------");
        System.out.println(String.format("二叉树节点个数：%d", binTree.size(root)));
    }

}
