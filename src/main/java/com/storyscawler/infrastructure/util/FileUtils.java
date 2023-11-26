package com.storyscawler.infrastructure.util;

import java.util.Objects;
import java.util.regex.Pattern;

public class FileUtils {

    public static String getFileExtension(String filePath) {
        if (Objects.isNull(filePath)) {
            return null;
        }
        var p = Pattern.compile("(([^\\/]+)\\.([a-zA-Z]+))([^\\/]*)$");
        var m = p.matcher(filePath);
        if (m.find()) {
            return m.group(3);
        }
        return null;
    }
}
