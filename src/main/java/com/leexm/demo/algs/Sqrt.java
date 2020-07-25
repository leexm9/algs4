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
        double low, high, mid = 0;
        if (n == 1) {
            return n;
        } else if (n > 1) {
            low = 1;
            high = n;
        } else {
            low = n;
            high = 1;
        }
        while (low <= high && Math.abs(high - low) >= precision) {
            mid = low + (high - low) / 2;
            double tmp = Math.pow(mid, 2);
            if (tmp == n || Math.abs(tmp - n) < precision) {
                return mid;
            } else if (tmp < n) {
                low = mid;
            } else if (tmp > n) {
                high = mid;
            }
        }
        return mid;
    }

    /**
     * 牛顿迭代法求平方根
     *
     * @param n
     * @param precision
     * @return
     */
    public static double newSqrt(double n, double precision) {
        double rs = n;
        while ((rs - n / rs) > precision) {
            rs = (rs + n / rs) / 2;
        }
        return rs;
    }

    public static void main(String[] args) {
        System.out.println(square(5, 0.001));
        System.out.println(newSqrt(5, 0.001));
        System.out.println((5 - Math.pow(2.2353515625, 2)) * 1000);
        System.out.println((5 - Math.pow(2.2360688956433634, 2)) * 1000);
    }

}
