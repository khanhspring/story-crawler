package com.storyscawler.source.trumtruyen.filter;

import com.storyscawler.infrastructure.util.StringUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Order(99)
@Component
public class TrumtruyenDomainContentFilter implements TrumtruyenContentFilter {

    private static final String[] ADS_CONTENTS = {
            "Trùm Truyện",
            "trumtruyen.vn",
            "Trùm truyện",
            "trumtruyen",
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
