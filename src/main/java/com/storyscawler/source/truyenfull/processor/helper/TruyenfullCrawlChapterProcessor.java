package com.storyscawler.source.truyenfull.processor.helper;

import com.storyscawler.core.ElementExtractor;
import com.storyscawler.core.jsoup.action.JsoupActionContext;
import com.storyscawler.core.jsoup.action.JsoupActionExecutor;
import com.storyscawler.core.model.PageResult;
import com.storyscawler.core.selector.ElementLocator;
import com.storyscawler.source.truyenfull.util.TruyenfullUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class TruyenfullCrawlChapterProcessor {

    public PageResult<Map<String, String>> process(JsoupActionContext context, String url) {
        log.info("Start crawling chapter {}", url);
        var executor = new JsoupActionExecutor(context);
        executor.access(url);

        try {
            return extractPageContent(executor, url);
        } catch (Exception e) {
            log.warn("Error when crawl chapter {}", url, e);
            throw e;
        }
    }

    private static PageResult<Map<String, String>> extractPageContent(JsoupActionExecutor executor, String url) {
        var titleExtractor = ElementExtractor
                .element(ElementLocator.cssSelector(".chapter-title"))
                .text("chapter_title");
        var title = executor.extractSingleData(titleExtractor);

        var contentExtractor = ElementExtractor
                .element(ElementLocator.cssSelector("#chapter-c"))
                .html("chapter_content");
        var content = executor.extractSingleData(contentExtractor);

        var btnNextExtractor = ElementExtractor
                .element(ElementLocator.cssSelector("#next_chap"))
                .attr("href", "href")
                .attr("className", "class");
        var btnNext = executor.extractSingleData(btnNextExtractor);

        boolean hasNext = true;
        if (StringUtils.trimToEmpty(btnNext.get("className")).contains("disabled")) {
            hasNext = false;
        }
        var nextChapterSlug = TruyenfullUtils.extractChapterSlug(btnNext.get("href"));

        var result = Map.of(
                "chapter_content", content.get("chapter_content"),
                "chapter_title", title.get("chapter_title"),
                "next_chapter_slug", StringUtils.trimToEmpty(nextChapterSlug),
                "chapter_url", url
        );

        return PageResult.<Map<String, String>>builder()
                .hasNext(hasNext)
                .content(result)
                .build();
    }
}
