package com.storyscawler.source.trumtruyen.filter;

import com.storyscawler.infrastructure.util.StringUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.List;


@Order(0)
@Component
public class TrumtruyenSummaryTagsFilter implements TrumtruyenContentFilter {

    private static final List<String> REMOVE_CONTENT_REGEXES = List.of(
            "(\\-)?(\\s)*Tags:(.*)",
            "Giới thiệu Truyện",
            "Giới thiệu",
            "(Cùng đọc truyện)(.*)(trải nghiệm tốt tại website)(\\.)?",
            "Có gì hot? Chọt thử tra\uD835\uDDFBg ［ TrùmTr\uD835\uDC2Ey ệ\uD835\uDDFB.\uD835\uDCE5\uD835\uDE49 "
    );

    @Override
    public String filter(String content) {
        if (ObjectUtils.isEmpty(content)) {
            return content;
        }
        for (var regex : REMOVE_CONTENT_REGEXES) {
            content = content.replaceFirst(regex, "");
        }
        return StringUtils.trim(content);
    }
}
