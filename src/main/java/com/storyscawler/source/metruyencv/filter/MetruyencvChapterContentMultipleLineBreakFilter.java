package com.storyscawler.source.metruyencv.filter;

import com.storyscawler.infrastructure.util.StringUtils;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;


@Order(100)
@Component
public class MetruyencvChapterContentMultipleLineBreakFilter implements MetruyencvContentFilter {

    @Override
    public String filter(String content) {
        if (ObjectUtils.isEmpty(content)) {
            return content;
        }

        content = content.replaceAll("(\n{2,})|(\r{2,})|((\r\n){2,})","\n");
        return StringUtils.trim(content);
    }
}
