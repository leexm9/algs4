package com.leexm.demo.leetcode;

import com.leexm.demo.util.Format;

import java.util.*;

/**
 * 广度优先搜索
 *
 * @author leexm
 * @date 2021-02-10 23:30
 */
public class BFSearch {

    public static void main(String[] args) {
        BFSearch bfSearch = new BFSearch();
        String beginWord = "red";
        String endWord = "tax";
        List<String> wordList = Arrays.asList("ted","tex","red","tax","tad","den","rex","pee");

        System.out.println(bfSearch.findLadders2(beginWord, endWord, wordList));
    }

    /**
     * 934. 最短的桥 https://leetcode-cn.com/problems/shortest-bridge/
     *
     * @param A
     * @return
     */
    public int shortestBridge(int[][] A) {
        int[] dirs = {-1, 0, 1, 0, -1};
        LinkedList<int[]> firstIsland = new LinkedList<>();
        // 查找第一个岛屿
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[0].length; j++) {
                if (A[i][j] != 1) {
                    continue;
                }
                this.findFirstIsland(A, dirs, i, j, firstIsland);
                break;
            }
            if (!firstIsland.isEmpty()) {
                break;
            }
        }

        int n = 0;
        int size = firstIsland.size();
        while (!firstIsland.isEmpty()) {
            while (size > 0) {
                boolean flag = findNextIsland(firstIsland, dirs, A);
                if (flag) {
                    break;
                }
                size--;
            }
            if (size != 0) {
                break;
            }
            n++;
            size = firstIsland.size();
        }
        return n;
    }

    /**
     * 查找第一个岛
     *
     * @param A
     * @return
     */
    private void findFirstIsland(int[][] A, int[] dirs, int i, int j, List<int[]> island) {
        if (A[i][j] != 1) {
            return;
        }
        island.add(new int[] {i, j});
        A[i][j] = 2;
        for (int d = 0; d < 4; d++) {
            int r = i + dirs[d];
            int c = j + dirs[d + 1];
            if (r >= 0 && c >= 0 && r < A.length && c < A[0].length && A[r][c] == 1) {
                this.findFirstIsland(A, dirs, r, c, island);
            }
        }
    }

    private boolean findNextIsland(LinkedList<int[]> island, int[] dirs, int[][] A) {
        int[] a = island.pop();
        for (int i = 0; i < 4; i++) {
            int r = a[0] + dirs[i];
            int c = a[1] + dirs[i + 1];
            if (r >= 0 && c >= 0 && r < A.length && c < A[0].length) {
                if (A[r][c] == 0) {
                    A[r][c] = 2;
                    island.add(new int[] {r, c});
                }
                if (A[r][c] == 1) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 126. 单词接龙 II https://leetcode-cn.com/problems/word-ladder-ii/
     *
     * @param beginWord
     * @param endWord
     * @param wordList
     * @return
     */
    public List<List<String>> findLadders1(String beginWord, String endWord, List<String> wordList) {
        List<List<String>> result = new ArrayList<>();

        // 单个字符场景
        if (beginWord.length() == 1) {
            result.add(Arrays.asList(beginWord, endWord));
            return result;
        }

        Set<String> dict = new HashSet<>(wordList);
        // 不包含目标单词，直接返回
        if (!dict.contains(endWord)) {
            return result;
        }

        dict.remove(beginWord);
        Map<String, List<String>> nextMap = new HashMap<>();

        Set<String> words = new HashSet<>();
        words.add(beginWord);

        boolean found = false;
        while (!words.isEmpty() && !found) {
            Set<String> nextLevelWords = new HashSet<>();
            for (String str : words) {
                for (int i = 0; i < str.length(); i++) {
                    for (int j = 0; j < 26; j++) {
                        String temp = this.replaceChar(str, i, (char)(j + 'a'));
                        if (!dict.contains(temp)) {
                            continue;
                        }
                        nextMap.computeIfAbsent(str, k -> new ArrayList<>()).add(temp);
                        if (endWord.equals(temp)) {
                            found = true;
                        }
                        nextLevelWords.add(temp);
                    }
                }
            }
            words = nextLevelWords;
            dict.removeAll(nextLevelWords);
        }
        if (!found) {
            return result;
        }
        List<String> path = new ArrayList<>();
        path.add(beginWord);
        this.backtracking(beginWord, endWord, nextMap, path, result);

        return result;
    }

    /**
     * 126. 单词接龙 II https://leetcode-cn.com/problems/word-ladder-ii/
     *
     * @param beginWord
     * @param endWord
     * @param wordList
     * @return
     */
    public List<List<String>> findLadders2(String beginWord, String endWord, List<String> wordList) {
        List<List<String>> result = new ArrayList<>();

        // 单个字符场景
        if (beginWord.length() == 1) {
            result.add(Arrays.asList(beginWord, endWord));
            return result;
        }

        Set<String> dict = new HashSet<>(wordList);
        // 不包含目标单词，直接返回
        if (!dict.contains(endWord)) {
            return result;
        }

        dict.remove(beginWord);
        dict.remove(endWord);
        Map<String, List<String>> nextMap = new HashMap<>();

        Set<String> left = new HashSet<>();
        left.add(beginWord);
        Set<String> right = new HashSet<>();
        right.add(endWord);

        boolean reversed = false, found = false;
        while (!left.isEmpty() && !found) {
            Set<String> nextLevelWords = new HashSet<>();
            for (String str : left) {
                for (int i = 0; i < str.length(); i++) {
                    for (int j = 0; j < 26; j++) {
                        String ns = this.replaceChar(str, i, (char)(j + 'a'));
                        if (right.contains(ns)) {
                            if (reversed) {
                                nextMap.computeIfAbsent(ns, k -> new ArrayList<>()).add(str);
                            } else {
                                nextMap.computeIfAbsent(str, k -> new ArrayList<>()).add(ns);
                            }
                            found = true;
                        } else if (dict.contains(ns)) {
                            if (reversed) {
                                nextMap.computeIfAbsent(ns, k -> new ArrayList<>()).add(str);
                            } else {
                                nextMap.computeIfAbsent(str, k -> new ArrayList<>()).add(ns);
                            }
                            nextLevelWords.add(ns);
                        }
                    }
                }
            }
            if (nextLevelWords.size() <= right.size()) {
                left = nextLevelWords;
            } else {
                reversed = !reversed;
                left = right;
                right = nextLevelWords;
            }
            dict.removeAll(nextLevelWords);
        }

        if (found) {
            List<String> path = new ArrayList<>();
            path.add(beginWord);
            this.backtracking(beginWord, endWord, nextMap, path, result);
        }
        return result;
    }

    private void backtracking(String beginWord, String endWord, Map<String, List<String>> nextMap, List<String> path,
        List<List<String>> result) {
        if (beginWord.equals(endWord)) {
            result.add(new ArrayList<>(path));
            return;
        }
        List<String> nextStrs = nextMap.get(beginWord);
        if (nextStrs == null || nextStrs.isEmpty()) {
            return;
        }
        for (String str : nextStrs) {
            path.add(str);
            this.backtracking(str, endWord, nextMap, path, result);
            path.remove(path.size() - 1);
        }
    }

    /**
     * 替换字符串指定位置的字符
     *
     * @param s
     * @param index
     * @param newChar
     * @return
     */
    private String replaceChar(String s, int index, char newChar) {
        StringBuilder builder = new StringBuilder(s);
        builder.replace(index++, index, String.valueOf(newChar));
        return builder.toString();
    }

}
