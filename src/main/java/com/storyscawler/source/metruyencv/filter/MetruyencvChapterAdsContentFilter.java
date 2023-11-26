package com.storyscawler.source.metruyencv.filter;

import com.storyscawler.infrastructure.util.StringUtils;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;


@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
public class MetruyencvChapterAdsContentFilter implements MetruyencvContentFilter {

    private static final String[] ADS_CONTENTS = {
            "— QUẢNG CÁO —",
            "MeTruyenChu\\.Com sẽ chuyển qua sử dụng tên miền mới MeTruyenCv\\.Com, vui lòng ghi nhớ tên miền mới metruyencv\\.com(\\n)+"
    };
    @Override
    public String filter(String content) {
        if (ObjectUtils.isEmpty(content)) {
            return content;
        }
        for (var ads : ADS_CONTENTS) {
            content = content.replaceAll(ads, "");
        }
        return StringUtils.trim(content);
    }
}
