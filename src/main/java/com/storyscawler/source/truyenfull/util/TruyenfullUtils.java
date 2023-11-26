package com.storyscawler.source.truyenfull.util;

import java.util.Objects;
import java.util.regex.Pattern;

public class TruyenfullUtils {

    private static final String STORY_URL_REGEX = "^https:\\/\\/truyenfull.vn\\/([^\\/^\\?^\\&]*)(.*)$";
    private static final String CHAPTER_URL_REGEX = "^https:\\/\\/truyenfull\\.vn\\/([^\\/]+)\\/([^\\/^\\?]+)(.*)$";
    private static final String COMPLETED_STATUS = "Full";

    public static String extractStorySlug(String url) {
        var p = Pattern.compile(STORY_URL_REGEX);
        var m = p.matcher(url);
        if (!m.matches()) {
            return null;
        }
        return m.group(1).trim();
    }

    public static String extractChapterSlug(String url) {
        var p = Pattern.compile(CHAPTER_URL_REGEX);
        var m = p.matcher(url);
        if (!m.matches()) {
            return "";
        }
        return m.group(2).trim();
    }

    public static String buildChapterBaseFromStoryUrl(String storyUrl) {
        if (storyUrl.endsWith("/")) {
            return storyUrl + "{chapterNumber}/";
        }
        return storyUrl + "/{chapterNumber}/";
    }

    public static boolean isCompletedStatus(String statusName) {
        if (Objects.isNull(statusName)) {
            return false;
        }
        return COMPLETED_STATUS.equalsIgnoreCase(statusName.trim());
    }
}
