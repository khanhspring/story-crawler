package com.storyscawler.source.metruyencv.filter;

import com.storyscawler.infrastructure.util.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
public class MetruyencvChapterTitlePrefixFilter implements MetruyencvChapterTitleFilter {

    @Override
    public String filter(String content) {
        if (ObjectUtils.isEmpty(content)) {
            return content;
        }
        return StringUtils.trim(content);
    }
}
