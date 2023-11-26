package com.storyscawler.source.metruyencv.filter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MetruyencvChapterTitleFilters {
    private final List<MetruyencvChapterTitleFilter> filters;

    public String filter(String content) {
        for (var filter : filters) {
            content = filter.filter(content);
        }
        return content;
    }
}
