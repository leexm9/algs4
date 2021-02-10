package com.leexm.demo.leetcode;

import com.leexm.demo.util.Format;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 深度优先搜索
 *
 * @author leexm
 * @date 2021-02-06 18:09
 */
public class DFSearch {

    public static void main(String[] args) {
        DFSearch dfs = new DFSearch();

        int[][] grid = {{1,0,0,1},{0,1,1,0},{0,1,1,1},{1,0,1,1}};
        Format.printMatrix(grid);
        System.out.println(dfs.findCircleNum(grid));
    }

    /**
     * 695. 岛屿的最大面积
     * https://leetcode-cn.com/problems/max-area-of-island/
     *
     * @param grid
     * @return
     */
    public int maxAreaOfIsland(int[][] grid) {
        int rowNum = grid.length;
        int colNum = grid[0].length;
        // 上右下左 方向向量
        int[] direction = {-1, 0, 1, 0, -1};
        int max = 0;
        for (int i = 0; i < rowNum; i++) {
            for (int j = 0; j < colNum; j++) {
                if (grid[i][j] == 1) {
                    int num = this.findNextIsland(grid, i, j, direction, 1);
                    max = Math.max(max, num);
                }
            }
        }
        return max;
    }

    private int findNextIsland(int[][] grid, int rdx, int cdx, int[] direction, int num) {
        // 标记访问过
        grid[rdx][cdx] = 2;
        for (int i = 0; i < 4; i++) {
            int row = rdx + direction[i];
            int col = cdx + direction[i + 1];
            if (row >= 0 && row < grid.length && col >= 0 && col < grid[0].length && grid[row][col] == 1) {
                num = this.findNextIsland(grid, row, col, direction, num + 1);
            }
        }
        return num;
    }


    /**
     * 547. 省份数量
     * https://leetcode-cn.com/problems/number-of-provinces/
     *
     * @param isConnected
     * @return
     */
    public int findCircleNum(int[][] isConnected) {
        int circleNum = 0;
        for (int i = 0; i < isConnected.length; i++) {
            if (isConnected[i][i] == 1) {
                circleNum++;
                this.findNextConnected(isConnected, i);
            }
        }
        return circleNum;
    }

    private void findNextConnected(int[][] isConnected, int index) {
        isConnected[index][index] = 2;
        for (int i = 0; i < isConnected.length; i++) {
            if (isConnected[index][i] == 1 && isConnected[i][i] == 1) {
                this.findNextConnected(isConnected, i);
            }
        }
    }


    /**
     * 417. 太平洋大西洋水流问题
     * https://leetcode-cn.com/problems/pacific-atlantic-water-flow/
     *
     * @param matrix
     * @return
     */
    public List<List<Integer>> pacificAtlantic(int[][] matrix) {
        if (matrix.length == 0) {
            return new ArrayList<>();
        }
        int m = matrix.length, n = matrix[0].length;
        int[][] waterVisited = new int[m][n];
        int[] direction = {-1, 0, 1, 0, -1};
        // 太平洋
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 || j == 0) {
                    this.pacificNextScan(matrix, waterVisited, direction, i, j);
                }
            }
        }

        // 大西洋
        for (int i = m - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                if (i == m - 1 || j == n - 1) {
                    this.atlanticNextScan(matrix, waterVisited, direction, i, j);
                }
            }
        }

        List<List<Integer>> rs = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (waterVisited[i][j] == 3) {
                    rs.add(Arrays.asList(i, j));
                }
            }
        }

        return rs;
    }

    private void pacificNextScan(int[][] matrix, int[][] waterVisited, int[] dir, int i, int j) {
        if (waterVisited[i][j] == 1) {
            return;
        }
        waterVisited[i][j] = 1;
        for (int d = 0; d < 4; d++) {
            int r = i + dir[d];
            int c = j + dir[d + 1];
            if (r >= 0 && c >= 0 && r < matrix.length && c < matrix[0].length && matrix[r][c] >= matrix[i][j]) {
                this.pacificNextScan(matrix, waterVisited, dir, r, c);
            }
        }
    }

    private void atlanticNextScan(int[][] matrix, int[][] waterVisited, int[] dir, int i, int j) {
        if (waterVisited[i][j] >= 2) {
            return;
        }
        waterVisited[i][j] = waterVisited[i][j] == 1 ? 3 : 2;
        for (int d = 0; d < 4; d++) {
            int r = i + dir[d];
            int c = j + dir[d + 1];
            if (r >= 0 && c >= 0 && r < matrix.length && c < matrix[0].length && matrix[r][c] >= matrix[i][j]) {
                this.atlanticNextScan(matrix, waterVisited, dir, r, c);
            }
        }
    }

}
