package com.leexm.demo.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author leexm
 * @date 2021-02-08 00:04
 */
public class Backtrace {

    public static void main(String[] args) {
        Backtrace backtrace = new Backtrace();

        System.out.println(backtrace.permute2(new int[]{1, 2, 3, 4}));

        List<List<String>> rs = backtrace.solveNQueens2(5);
        System.out.println(rs.size());
        System.out.println(rs);
    }


    /**
     * 46. 全排列
     * https://leetcode-cn.com/problems/permutations/
     *
     * @param nums
     * @return
     */
    public List<List<Integer>> permute1(int[] nums) {
        if (nums.length == 0) {
            return new ArrayList<>();
        }
        List<List<Integer>> rs = new ArrayList<>();
        rs.add(Arrays.stream(nums).boxed().collect(Collectors.toList()));
        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                this.nextPermute1(nums, i, j, rs);
            }
        }
        return rs;
    }

    private void nextPermute1(int[] nums, int idx1, int idx2, List<List<Integer>> list) {
        this.swap(nums, idx1, idx2);
        list.add(Arrays.stream(nums).boxed().collect(Collectors.toList()));
        for (int i = idx1 + 1; i < nums.length - 1; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                this.nextPermute1(nums, i, j, list);
            }
        }
        this.swap(nums, idx1, idx2);
    }

    /**
     * 46. 全排列
     * https://leetcode-cn.com/problems/permutations/
     *
     * @param nums
     * @return
     */
    public List<List<Integer>> permute2(int[] nums) {
        if (nums.length == 0) {
            return new ArrayList<>();
        }
        List<List<Integer>> rs = new ArrayList<>();
        this.permuteNext2(nums, 0, rs);
        return rs;
    }

    private void permuteNext2(int[] nums, int index, List<List<Integer>> list) {
        if (index == nums.length - 1) {
            list.add(Arrays.stream(nums).boxed().collect(Collectors.toList()));
            return;
        }
        for (int i = index; i < nums.length; i++) {
            this.swap(nums, index, i);
            this.permuteNext2(nums, index + 1, list);
            this.swap(nums, index, i);
        }
    }


    /**
     * 77. 组合
     * https://leetcode-cn.com/problems/combinations/
     *
     * @param n
     * @param k
     * @return
     */
    public List<List<Integer>> combine(int n, int k) {
        if (k <= 0 || n < k) {
            return new ArrayList<>();
        }
        List<List<Integer>> rs = new ArrayList<>();
        int[] item = new int[k];
        int[] nums = IntStream.rangeClosed(1, n).toArray();
        this.pickN(nums, 0, k, rs, item);
        return rs;
    }

    private void pickN(int[] nums, int index, int k, List<List<Integer>> result, int[] item) {
        if (k == 0) {
            result.add(Arrays.stream(item).boxed().collect(Collectors.toList()));
            return;
        }
        for (int i = index; i < nums.length; i++) {
            item[item.length - k] = nums[i];
            this.pickN(nums, i + 1, k - 1, result, item);
        }
    }


    /**
     * 79. 单词搜索
     * https://leetcode-cn.com/problems/word-search/
     *
     * @param board
     * @param word
     * @return
     */
    public boolean exist(char[][] board, String word) {
        int[][] visited = new int[board.length][board[0].length];
        int[] dirs = {-1, 0, 1, 0, -1};
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == word.charAt(0)) {
                    visited[i][j] = 1;
                    boolean flag = this.findNextChar(board, visited, dirs, i, j, word, 1);
                    if (flag) {
                        return true;
                    }
                    visited[i][j] = 0;
                }
            }
        }
        return false;
    }

    public boolean findNextChar(char[][] board, int[][] visited, int[] dirs, int i, int j, String word, int index) {
        if (index >= word.length()) {
            return true;
        }
        boolean flag = false;
        for (int k = 0; k < 4; k++) {
            int r = i + dirs[k];
            int c = j + dirs[k + 1];
            if (r >= 0 && c >= 0 && r < board.length && c < board[0].length
                    && visited[r][c] == 0 && board[r][c] == word.charAt(index)) {
                visited[r][c] = 1;
                flag = this.findNextChar(board, visited, dirs, r, c, word, index + 1);
                if (flag) {
                    break;
                }
                visited[r][c] = 0;
            }
        }
        return flag;
    }


    /**
     * 51. N 皇后
     * https://leetcode-cn.com/problems/n-queens/
     *
     * @param n
     * @return
     */
    public List<List<String>> solveNQueens1(int n) {
        int[][] dirs = {{-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}, {-1, -1}};
        int[][] visited = new int[n][n];
        List<List<String>> result = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            this.placeN(visited, dirs, 0, i, result);
            visited = new int[n][n];
        }
        return result;
    }

    private void placeN(int[][] visited, int[][] dirs, int n, int i, List<List<String>> solutions) {
        this.setVisited(visited, dirs, n++, i);
        if (n == visited.length) {
            this.queenResult(visited, solutions);
            return;
        }
        for (int j = 0; j < visited.length; j++) {
            if (visited[n][j] == 0) {
                this.placeN(visited, dirs, n, j, solutions);
                this.releaseVisited(visited, dirs, n, j);
            }
        }
    }

    private void setVisited(int[][] visited, int[][] dirs, int i, int j) {
        int r = i, c = j;
        visited[i][j] = 1;
        int k = 0;
        do {
            r += dirs[k][0];
            c += dirs[k][1];
            if (r >= 0 && c >= 0 && r < visited.length && c < visited.length) {
                visited[r][c] += 1;
            } else {
                k++;
                r = i;
                c = j;
            }
        } while (k < dirs.length);
    }

    private void releaseVisited(int[][] visited, int[][] dirs, int i, int j) {
        int r = i, c = j;
        visited[i][j] = 0;
        int k = 0;
        do {
            r += dirs[k][0];
            c += dirs[k][1];
            if (r >= 0 && c >= 0 && r < visited.length && c < visited.length) {
                visited[r][c] -= 1;
            } else {
                k++;
                r = i;
                c = j;
            }
        } while (k < dirs.length);
    }

    private void queenResult(int[][] visited, List<List<String>> result) {
        List<String> temp = new ArrayList<>();
        for (int r = 0; r < visited.length; r++) {
            StringBuilder builder = new StringBuilder();
            for (int c = 0; c < visited.length; c++) {
                if (visited[r][c] == 1) {
                    builder.append("Q");
                } else {
                    builder.append(".");
                }
            }
            temp.add(builder.toString());
        }
        result.add(temp);
    }

    /**
     * 51. N 皇后
     * https://leetcode-cn.com/problems/n-queens/
     *
     * @param n
     * @return
     */
    public List<List<String>> solveNQueens2(int n) {
        List<List<String>> result = new ArrayList<>();
        int[][] visited = new int[n][n];
        // 列上的皇后
        boolean[] col = new boolean[n];
        // 右斜列上的皇后
        boolean[] rdiag = new boolean[2 * n - 1];
        // 左斜列上的皇后
        boolean[] ldiag = new boolean[2 * n - 1];
        for (int i = 0; i < n; i++) {
            this.placeN(visited, col, rdiag, ldiag, 0, i, result);
        }
        return result;
    }

    private void placeN(int[][] visited, boolean[] col, boolean[] rdiag, boolean[] ldiag,
                        int r, int c, List<List<String>> solutions) {
        if (r >= col.length || c >= col.length) {
            return;
        }
        // (n, i) 对应的右、左斜列下标
        int ridx = r + c;
        int lidx = r + col.length - 1 - c;

        if (!col[c] && !rdiag[ridx] && !ldiag[lidx]) {
            visited[r][c] = 1;
            if (r == col.length - 1) {
                this.queenResult(visited, solutions);
            }

            col[c] = rdiag[ridx] = ldiag[lidx] = true;
            for (int i = 0; i < col.length; i++) {
                this.placeN(visited, col, rdiag, ldiag, r + 1, i, solutions);
            }
            visited[r][c] = 0;
            col[c] = rdiag[ridx] = ldiag[lidx] = false;
        }
    }

    private void swap(int[]nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

}
