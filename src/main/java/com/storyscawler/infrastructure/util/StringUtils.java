package com.storyscawler.infrastructure.util;

public class StringUtils extends org.apache.commons.lang3.StringUtils {

    public static String toKebabCase(String input) {
        var normalized = org.apache.commons.lang3.StringUtils.stripAccents(input);
        return normalized
                .toLowerCase()
                .replace("đ", "d")
                .trim()
                .replaceAll("\\s+", "-");
    }
}
