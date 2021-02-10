package com.leexm.demo.util;

/**
 * @author leexm
 * @date 2020-03-27 09:55
 */
public class InputUtils {

    /**
     * 将输入的字符串 "[1,2,3]" 转为数组
     * 
     * @param input
     * @return
     */
    public static int[] string2IntegerArray(String input) {
        input = input.trim();
        input = input.substring(1, input.length() - 1);
        if (input.length() == 0) {
            return new int[0];
        }

        String[] parts = input.split(",");
        int[] output = new int[parts.length];
        for (int index = 0; index < parts.length; index++) {
            String part = parts[index].trim();
            output[index] = Integer.parseInt(part);
        }
        return output;
    }

}
