package com.storyscawler.source.trumtruyen.filter;

import com.storyscawler.infrastructure.util.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
public class TrumtruyenChapterTitlePrefixFilter implements TrumtruyenChapterTitleFilter {

    private static final String TITLE_PREFIX_REGEX = "Chương ([0-9]+)(\s)*([:|.]?)";
    @Override
    public String filter(String content) {
        if (ObjectUtils.isEmpty(content)) {
            return content;
        }
        var nonPrefixContent = content.replaceFirst(TITLE_PREFIX_REGEX, "");
        return StringUtils.trim(nonPrefixContent);
    }
}
