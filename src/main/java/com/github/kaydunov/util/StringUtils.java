package com.github.kaydunov.util;

public class StringUtils {

    public static String centerString (String s, int width) {
        return String.format("%-" + width  + "s", String.format("%" + (s.length() + (width - s.length()) / 2) + "s", s));
    }

    public static String centerStringLn(String s, int width) {
        return centerString(s, width) + "\n";
    }
}
