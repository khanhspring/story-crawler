package com.storyscawler.source.metruyencv.util;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import java.util.regex.Pattern;

@Slf4j
public class MetruyencvUtils {

    private static final String STORY_URL_REGEX = "^https:\\/\\/metruyencv.com\\/truyen\\/([^\\/^\\?^\\&]*)(.*)$";
    private static final String COMPLETED_STATUS = "Hoàn thành";

    public static String extractStorySlug(String url) {
        var p = Pattern.compile(STORY_URL_REGEX);
        var m = p.matcher(url);
        if (!m.matches()) {
            return null;
        }
        return m.group(1).trim();
    }

    public static String buildChapterBaseFromStoryUrl(String storyUrl) {
        if (storyUrl.endsWith("/")) {
            return storyUrl + "chuong-{chapterNumber}";
        }
        return storyUrl + "/chuong-{chapterNumber}";
    }

    public static boolean isCompletedStatus(String statusName) {
        if (Objects.isNull(statusName)) {
            return false;
        }
        return COMPLETED_STATUS.equalsIgnoreCase(statusName.trim());
    }

    public static Integer extractTotalRating(String content) {
        if (Objects.isNull(content)) {
            return null;
        }
        try {
            var p = Pattern.compile("^(.*)([0-9]+)(.*)$");
            var m = p.matcher(content);
            if (m.matches()) {
                var value = m.group(2);
                return Integer.valueOf(value);
            }
        } catch (Exception e) {
            log.warn("Cannot extract total rating {} from text", content);
        }
        return null;
    }

    public static BigDecimal convertRating(String content) {
        if (Objects.isNull(content)) {
            return null;
        }
        try {
            return new BigDecimal(content)
                    .multiply(BigDecimal.valueOf(2))
                    .setScale(1, RoundingMode.HALF_UP);
        } catch (Exception e) {
            log.warn("Cannot convert rating {} to number", content);
        }
        return null;
    }
}
