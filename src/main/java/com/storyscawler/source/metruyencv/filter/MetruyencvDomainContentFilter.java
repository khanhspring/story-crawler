package com.storyscawler.source.metruyencv.filter;

import com.storyscawler.infrastructure.util.StringUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Order()
@Component
public class MetruyencvDomainContentFilter implements MetruyencvContentFilter {

    private static final String[] ADS_CONTENTS = {
            "MeTruyenChu.Com",
            "MeTruyenCv.Com",
            "metruyenchu.com",
            "metruyencv.com",
            "Truyencv.com",
            "truyencv.com"
    };
    @Override
    public String filter(String content) {
        if (ObjectUtils.isEmpty(content)) {
            return content;
        }
        for (var item : ADS_CONTENTS) {
            content = content.replace(item, "");
        }
        return StringUtils.trim(content);
    }
}
