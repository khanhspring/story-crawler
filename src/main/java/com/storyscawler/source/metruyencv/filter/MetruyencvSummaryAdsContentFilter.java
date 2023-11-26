package com.storyscawler.source.metruyencv.filter;

import com.storyscawler.infrastructure.util.StringUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;


@Order(0)
@Component
public class MetruyencvSummaryAdsContentFilter implements MetruyencvContentFilter {

    private static final String ADS_CONTENT_PREFIX = "CẦU HOA TƯƠI, CẦU BUFF KẸO";
    @Override
    public String filter(String content) {
        if (ObjectUtils.isEmpty(content)) {
            return content;
        }
        if (content.contains(ADS_CONTENT_PREFIX)) {
            var adsIndex = content.indexOf(ADS_CONTENT_PREFIX);
            var mainContent = content.substring(0, adsIndex);
            return StringUtils.trim(mainContent);
        }
        return StringUtils.trim(content);
    }
}
