package com.storyscawler.source.trumtruyen.filter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TrumtruyenChapterTitleFilters {
    private final List<TrumtruyenChapterTitleFilter> filters;

    public String filter(String content) {
        for (var filter : filters) {
            content = filter.filter(content);
        }
        return content;
    }
}
