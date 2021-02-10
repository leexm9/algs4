package com.leexm.demo.util;

/**
 * @author leexm
 * @date 2021-02-10 18:05
 */
public class Format {

    public static void printMatrix(int[][] mat) {
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[i].length; j++) {
                System.out.print(mat[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println();
    }

}
