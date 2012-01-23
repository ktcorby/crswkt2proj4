package com.gtproj4.crswkt2proj4.etc;

import java.text.DecimalFormat;

/**
 * Created by IntelliJ IDEA.
 * User: kevin
 * Date: 1/11/12
 * Time: 12:47 PM
 */
public class DoubleVal {
    private static final double TOLERANCE = 0.000001;
    private double d;

    public DoubleVal(double d) {
        this.d = d;
    }

    public static String format(double d) {
        return new DoubleVal(d).toString();
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#.#############");
        return df.format(d);
        //return ("" + d).replaceAll(".0$", "");
    }

    public static boolean eq(double a, double b) {
        return Math.abs(a - b) < TOLERANCE;
    }
}
