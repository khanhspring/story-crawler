package com.storyscawler.source.metruyencv.filter;

import com.storyscawler.infrastructure.util.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
public class MetruyencvChapterTitlePrefixFilter implements MetruyencvChapterTitleFilter {

    private static final String TITLE_PREFIX_REGEX = "Chương ([0-9]+)([:|.]?)";
    @Override
    public String filter(String content) {
        if (ObjectUtils.isEmpty(content)) {
            return content;
        }
        var nonPrefixContent = content.replaceFirst(TITLE_PREFIX_REGEX, "");
        return StringUtils.trim(nonPrefixContent);
    }
}
