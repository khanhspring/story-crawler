package com.storyscawler.source.metruyencv.processor.helper;

import com.storyscawler.core.ElementExtractor;
import com.storyscawler.core.action.ActionInput;
import com.storyscawler.core.model.PageResult;
import com.storyscawler.core.selector.ElementLocator;
import com.storyscawler.core.selenium.action.SeleniumActionContext;
import com.storyscawler.core.selenium.action.SeleniumActionExecutor;
import com.storyscawler.infrastructure.exception.PageNotFoundException;
import com.storyscawler.infrastructure.property.MetruyencvProperties;
import com.storyscawler.source.metruyencv.action.LoginAction;
import com.storyscawler.source.metruyencv.action.NormalizeStoryContentAction;
import com.storyscawler.source.metruyencv.action.ReorderStoryContentAction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class MetruyencvCrawlChapterProcessor {

    private final MetruyencvProperties properties;

    public PageResult<Map<String, String>> process(SeleniumActionContext context, String url) {
        var executor = new SeleniumActionExecutor(context);
        executor.addVoidAction("login", new LoginAction());
        executor.addVoidAction("normalizeStoryContent", new NormalizeStoryContentAction());
        executor.addVoidAction("reorderStoryContent", new ReorderStoryContentAction());

        executor.access(url);

        executor.executeVoid("login", ActionInput.of("account", properties.pickRandomAccount()));
        executor.executeVoid("normalizeStoryContent");
        executor.executeVoid("reorderStoryContent");

        try {
            return extractPageContent(executor, url);
        } catch (Exception e) {
            log.warn("Error when crawl chapter", e);
            throwIfPageNotFound(executor);
            throw e;
        }
    }

    private static PageResult<Map<String, String>> extractPageContent(SeleniumActionExecutor executor, String url) {
        var titleExtractor = ElementExtractor
                .element(ElementLocator.cssSelector(".nh-read__title"))
                .text("chapter_title");
        var title = executor.extractSingleData(titleExtractor);

        var lockExtractor = ElementExtractor
                .elements(ElementLocator.cssSelector(".unlock-chapter__title"))
                .ignoreWaiting()
                .text("unlock_chapter_title");
        var lockTitle = executor.extractSingleData(lockExtractor);

        var contentExtractor = ElementExtractor
                .element(ElementLocator.cssSelector("#article"))
                .text("chapter_content");
        var content = executor.extractSingleData(contentExtractor);

        var btnNextExtractor = ElementExtractor
                .element(ElementLocator.id("nextChapter"))
                .attr("href", "href");
        var btnNextData = executor.extractSingleData(btnNextExtractor);

        boolean hasNext = true;
        var isLocked = !lockTitle.isEmpty();
        if (StringUtils.trimToEmpty(btnNextData.get("href")).endsWith("/chuong-cuoi") || isLocked) {
            hasNext = false;
        }

        var result = Map.of(
                "chapter_content", content.get("chapter_content"),
                "chapter_title", !isLocked ? title.get("chapter_title") : "", // to ignore this chapter
                "chapter_url", url

        );

        return PageResult.<Map<String, String>>builder()
                .hasNext(hasNext)
                .content(result)
                .build();
    }

    private void throwIfPageNotFound(SeleniumActionExecutor executor) {
        var errorMessageExtractor = ElementExtractor
                .element(ElementLocator.cssSelector(".wrap-error-page .db"))
                .ignoreException()
                .text("error_message");
        var error = executor.extractSingleData(errorMessageExtractor);

        var isPageNotFound = StringUtils.trimToEmpty(error.get("error_message"))
                .contains("Nội dung này không tồn tại hoặc đã bị xóa");

        if (isPageNotFound) {
            throw new PageNotFoundException();
        }
    }
}
