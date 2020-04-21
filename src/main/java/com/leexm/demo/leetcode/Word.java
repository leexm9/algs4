package com.leexm.demo.leetcode;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author leexm
 * @date 2020-03-31 16:12
 */
public class Word {

    public static void main(String[] args) {
        String[] dict = {"apple", "orange", "pie"};
        String word = "applepie";
        System.out.println(isInDict(word, dict));
        System.out.println(m2(word, dict));
    }

    private static boolean isInDict(String word, String[] dict) {
        Set<String> set = Stream.of(dict).collect(Collectors.toSet());
        // spilt[i] 表示 word 前 i 个字符是否可以拆分
        boolean[] spilt = new boolean[word.length() + 1];
        spilt[0] = true;
        for (int i = 1; i <= word.length(); i++) {
            for (int j = 0; j < i; j++) {
                if (spilt[j] && set.contains(word.substring(j, i))) {
                    spilt[i] = true;
                    break;
                }
            }
        }
        return spilt[word.length()];
    }

    private static boolean m2(String word, String[] dict) {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        Set<String> set = new HashSet<>();
        for (String wd : dict) {
            int length = wd.length();
            if (length < min) {
                min = length;
            }
            if (length > max) {
                max = length;
            }
            set.add(wd);
        }
        // 记录map，记录重复的计算的结果
        Map<Integer, Boolean> map = new HashMap<>();
        return isIn(word, 0, max, set, map);
    }

    private static boolean isIn(String word, int start, int max, Set<String> dict, Map<Integer, Boolean> map) {
        if (start == word.length()) {
            return true;
        }
        if (map.containsKey(start)) {
            return map.get(start);
        }
        for (int i = start; i < start + max && i < word.length(); i++) {
            if (dict.contains(word.substring(start, i + 1))) {
                if (isIn(word,i+1, max, dict, map)) {
                    map.put(start, true);
                    return true;
                }
            }
        }
        map.put(start,false);
        return false;
    }

}
