package com.ubs.cpt.shared.util;

public final class SqlUtil {
    private SqlUtil() {}

    public static String likeUppercaseArgument(String value) {
        if (value == null) {
            return null;
        }

        return "%" + value.trim().toUpperCase() + "%";
    }
}
