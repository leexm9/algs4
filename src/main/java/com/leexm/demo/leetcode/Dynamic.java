package com.leexm.demo.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 动态规划
 *
 * @author leexm
 * @date 2021-02-13 20:09
 */
public class Dynamic {

    public static void main(String[] args) {
        Dynamic dynamic = new Dynamic();
        char[][] matrix = {
                {'1','1',}, {'0','1'}};

        System.out.println(dynamic.maximalSquare2(matrix));
    }

    /**
     * 70. 爬楼梯
     * https://leetcode-cn.com/problems/climbing-stairs/
     *
     * @param n
     * @return
     */
    public int climbStairs1(int n) {
        if (n <= 2) {
            return n;
        }
        int[] rs = new int[n + 1];
        rs[1] = 1;
        rs[2] = 2;
        for (int i = 3; i <= n; i++) {
            rs[i] = rs[i - 1] + rs[i - 2];
        }
        return rs[n];
    }

    public int climbStairs2(int n) {
        if (n <= 2) {
            return n;
        }
        int pre1 = 1, pre2 = 2, cur = 0;
        for (int i = 3; i <= n; i++) {
            cur = pre2 + pre1;
            pre2 = cur;
            pre1 = pre2;
        }
        return cur;
    }

    /**
     * 198. 打家劫舍
     * https://leetcode-cn.com/problems/house-robber/
     *
     * @param nums
     * @return
     */
    public int rob(int[] nums) {
        int n = nums.length;
        if (n == 0) {
            return 0;
        } else if (n == 1) {
            return nums[0];
        }

        int pre1 = nums[0], pre2 = 0, cur = 0;
        for (int i = 1; i < nums.length; i++) {
            cur = Math.max(nums[i] + pre2, pre1);
            pre2 = pre1;
            pre1 = cur;
        }
        return cur;
    }

    /**
     * 413. 等差数列划分
     * https://leetcode-cn.com/problems/arithmetic-slices/
     *
     * @param nums
     * @return
     */
    public int numberOfArithmeticSlices1(int[] nums) {
        if (nums.length < 3) {
            return 0;
        }
        int diff = nums[1] - nums[0];
        int[] range = new int[] {0, 1};
        List<int[]> subs = new ArrayList<>();
        subs.add(range);
        for (int i = 2; i < nums.length; i++) {
            int d = nums[i] - nums[i - 1];
            if (d != diff) {
                range[1] = i - 1;
                range = new int[] {i - 1, i};
                subs.add(range);
                diff = d;
            } else {
                range[1] = i;
            }
        }
        List<int[]> list = subs.stream().filter(item -> item[1] - item[0] >= 2).collect(Collectors.toList());
        if (list.isEmpty()) {
            return 0;
        }
        int sum = 0;
        for (int[] item : list) {
            if (item[1] - item[0] == 2) {
                sum++;
            } else {
                for (int i = item[0]; i <= item[1] - 2; i++) {
                    for (int j = i + 2; j <= item[1]; j++) {
                        sum++;
                    }
                }
            }
        }
        return sum;
    }

    public int numberOfArithmeticSlices2(int[] nums) {
        if (nums.length < 3) {
            return 0;
        }
        int[] dp = new int[nums.length];
        int pre = nums[1] - nums[0];
        for (int i = 2; i < nums.length; i++) {
            int diff = nums[i] - nums[i - 1];
            if (diff == pre) {
                dp[i] = dp[i - 1] + 1;
            } else {
                pre = diff;
            }
        }
        return Arrays.stream(dp).sum();
    }

    /**
     * 64. 最小路径和
     * https://leetcode-cn.com/problems/minimum-path-sum/
     *
     * @param grid
     * @return
     */
    public int minPathSum1(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[][] path = new int[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int pathSum = grid[i][j] + this.minLastPath(path, i, j);
                path[i][j] = pathSum;
            }
        }

        return path[m - 1][n - 1];
    }

    private int minLastPath(int[][] grid, int i, int j) {
        // 左
        int r1 = i;
        int c1 = j - 1;
        // 上
        int r2 = i - 1;
        int c2 = j;

        int path1 = 0, path2 = 0;
        if (r1 >= 0 && r1 < grid.length && c1 >= 0 && c1 < grid[0].length) {
            path1 = grid[r1][c1];
        }
        if (r2 >= 0 && r2 < grid.length && c2 >= 0 && c2 < grid[0].length) {
            path2 = grid[r2][c2];
        }

        if (c1 < 0) {
            return path2;
        } else if (r2 < 0) {
            return path1;
        } else {
            return Math.min(path1, path2);
        }
    }

    /**
     * 64. 最小路径和
     * https://leetcode-cn.com/problems/minimum-path-sum/
     *
     * @param grid
     * @return
     */
    public int minPathSum2(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[] path = new int[n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 && j == 0) {
                    path[0] = grid[0][0];
                } else if (i == 0) {
                    path[j] = grid[0][j] + path[j - 1];
                } else if (j == 0) {
                    path[0] = grid[i][0] + path[0];
                } else {
                    path[j] = grid[i][j] + Math.min(path[j - 1], path[j]);
                }
            }
        }

        return path[n - 1];
    }


    /**
     * 542. 01 矩阵
     * https://leetcode-cn.com/problems/01-matrix/
     *
     * @param matrix
     * @return
     */
    public int[][] updateMatrix1(int[][] matrix) {
        if (matrix.length == 0) {
            return new int[][]{};
        }
        int m = matrix.length, n = matrix[0].length;
        int[][] dp = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                dp[i][j] = Integer.MAX_VALUE - 1;
            }
        }

        // 所有 0 位置，广度优先搜索第一层
        LinkedList<int[]> queue = new LinkedList<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 0) {
                    queue.add(new int[]{i, j});
                    dp[i][j] = 0;
                }
            }
        }

        int[] dirs = {-1, 0, 1, 0, -1};
        while (!queue.isEmpty()) {
            int[] cell = queue.poll();
            int i = cell[0];
            int j = cell[1];
            if (matrix[i][j] == 2) {
                continue;
            }
            for (int d = 0; d < 4; d++) {
                int r = i + dirs[d];
                int c = j + dirs[d + 1];
                if (r >= 0 && r < m && c >= 0 && c < n) {
                    dp[r][c] = Math.min(dp[r][c], dp[i][j] + 1);
                    queue.add(new int[]{r, c});
                }
            }
            // 四周都遍历过标记
            matrix[i][j] = 2;
        }

        return dp;
    }

    /**
     * 542. 01 矩阵
     * https://leetcode-cn.com/problems/01-matrix/
     * 该解法类似贪心算法中的分糖果
     *
     * @param matrix
     * @return
     */
    public int[][] updateMatrix2(int[][] matrix) {
        if (matrix.length == 0) {
            return new int[][]{};
        }
        int m = matrix.length, n = matrix[0].length;
        int[][] dp = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                dp[i][j] = Integer.MAX_VALUE - 1;
            }
        }

        // 第一次遍历，正向遍历，根据相邻左元素和上元素得出当前元素的对应结果
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 0) {
                    dp[i][j] = 0;
                } else {
                    if (j > 0) {
                        dp[i][j] = Math.min(dp[i][j], dp[i][j - 1] + 1);
                    }
                    if (i > 0) {
                        dp[i][j] = Math.min(dp[i][j], dp[i - 1][j] + 1);
                    }
                }
            }
        }
        // 第二次遍历，反向遍历，根据相邻右元素和下元素及当前元素的结果得出最终结果
        for (int i = m - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                if (matrix[i][j] != 0) {
                    if (j < n - 1) {
                        dp[i][j] = Math.min(dp[i][j], dp[i][j + 1] + 1);
                    }
                    if (i < m - 1) {
                        dp[i][j] = Math.min(dp[i][j], dp[i + 1][j] + 1);
                    }
                }
            }
        }

        return dp;
    }


    /**
     * 221. 最大正方形
     * https://leetcode-cn.com/problems/maximal-square/
     *
     * @param matrix
     * @return
     */
    public int maximalSquare1(char[][] matrix) {
        int max = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == '0') {
                    continue;
                }
                int size = this.nextCell(matrix, i, j, 1);
                max = Math.max(max, size + 1);
            }
        }
        return max * max;
    }

    private int nextCell(char[][] matrix, int i, int j, int n) {
        int r = i + n;
        int c = j + n;
        if (r >= matrix.length || c >= matrix[0].length || matrix[r][c] != '1') {
            return n - 1;
        }

        int k = j;
        while (k <= c && matrix[r][k] == '1') {
            k++;
        }
        if (k != c + 1) {
            return n - 1;
        }
        k = i;
        while (k <= r && matrix[k][c] == '1') {
            k++;
        }
        if (k != r + 1) {
            return n - 1;
        }
        return this.nextCell(matrix, i, j, n + 1);
    }

    /**
     * 221. 最大正方形
     * https://leetcode-cn.com/problems/maximal-square/
     *
     * @param matrix
     * @return
     */
    public int maximalSquare2(char[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        int[][] dp = new int[m + 1][n + 1];

        int max = 0;
        for (int i = 1; i < matrix.length; i++) {
            for (int j = 1; j < matrix[0].length; j++) {
                if (matrix[i - 1][j - 1] == '1') {
                    dp[i][j] = Math.min(dp[i - 1][j - 1], Math.min(dp[i][j - 1], dp[i - 1][j])) + 1;
                }
                max = Math.max(max, dp[i][j]);
            }
        }
        return max * max;
    }

}
