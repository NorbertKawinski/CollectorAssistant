package net.kawinski.utils.java;

public class StringUtils {
    public static boolean isNullOrWhitespace(String s) {
        if(s == null) {
            return true;
        }
        return s.trim().isEmpty();
    }
}
