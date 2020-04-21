package com.leexm.demo.algs;

/**
 * 求一个数的平方根，精确到小数点后 6 位
 *
 * @author leexm
 * @date 2020-04-07 15:38
 */
public class Sqrt {

    /**
     * 求平方根
     *
     * @param n             待求数据
     * @param precision     精度
     * @return
     */
    public static double square(double n, double precision) {
        double low, high, mid, tmp = n;
        if (n == 1) {
            return n;
        } else if (n > 1) {
            low = 1;
            high = n;
        } else {
            low = n;
            high = 1;
        }
        mid = low + (high - low) / 2;
        while (low <= high && Math.abs(high - low) >= precision) {
            tmp = Math.pow(mid, 2);
            if (tmp < n) {
                low = mid;
            } else if (tmp > n) {
                high = mid;
            } else if (Math.abs(tmp - n) < precision) {
                return mid;
            }
            mid = low + (high - low) / 2;
        }
        return mid;
    }

    public static void main(String[] args) {
        System.out.println(square(5, 0.00001));
        System.out.println(Math.pow(2.236064910888672, 2));
        System.out.println(Math.pow(2.236064910888673, 2));
    }

}
