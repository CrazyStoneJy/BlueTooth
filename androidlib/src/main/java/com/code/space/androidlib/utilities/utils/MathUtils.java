package com.code.space.androidlib.utilities.utils;

/**
 * Created by shangxuebin on 16-5-24.
 */
public class MathUtils {

    public static boolean isInRange(int num, int min, int max) {
        return num >= min && num <= max;
    }

    public static double sin(int degree) {
        return Math.sin(Math.toRadians(degree));
    }

    public static double sin(int degree, int hypotenuseLength) {
        return hypotenuseLength * sin(degree);
    }

    public static double cos(int degree) {
        return Math.cos(Math.toRadians(degree));
    }

    public static double cos(int degree, int hypotenuseLength) {
        return hypotenuseLength * cos(degree);
    }
}
