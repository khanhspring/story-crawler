package com.storyscawler.source.trumtruyen.util;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Safelist;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import java.util.regex.Pattern;

@Slf4j
public class TrumtruyenUtils {

    private static final String STORY_URL_REGEX = "^https:\\/\\/trumtruyen.vn\\/([^\\/^\\?^\\&]*)(.*)$";
    private static final String CHAPTER_URL_REGEX = "^https:\\/\\/trumtruyen\\.vn\\/([^\\/]+)\\/([^\\/^\\?]+)(.*)$";
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

    public static String br2nl(String html) {
        if(html==null) {
            return html;
        }
        Document document = Jsoup.parse(html);
        document.outputSettings(new Document.OutputSettings().prettyPrint(false));
        document.select("br").append("\\n");
        document.select("p").prepend("\\n");
        String s = document.html().replaceAll("\\\\n", "\n");
        return Jsoup.clean(s, "", Safelist.none(), new Document.OutputSettings().prettyPrint(false));
    }

    public static Integer convertTotalRating(String content) {
        if (Objects.isNull(content)) {
            return null;
        }
        try {
            return Integer.valueOf(content);
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
            return new BigDecimal(content);
        } catch (Exception e) {
            log.warn("Cannot convert rating {} to number", content);
        }
        return null;
    }
}
