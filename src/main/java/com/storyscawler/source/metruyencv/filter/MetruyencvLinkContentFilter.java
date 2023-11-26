package com.storyscawler.source.metruyencv.filter;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;


@Order(0)
@Component
public class MetruyencvLinkContentFilter implements MetruyencvContentFilter {

    @Override
    public String filter(String content) {
        if (ObjectUtils.isEmpty(content)) {
            return content;
        }
        return content.replaceAll("(\s)+(http(s):\\/\\/.)[-a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&\\/\\/=]*)(\s)", " ");
    }
}
