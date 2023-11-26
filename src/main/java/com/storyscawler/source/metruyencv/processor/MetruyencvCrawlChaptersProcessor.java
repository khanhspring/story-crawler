package com.storyscawler.source.metruyencv.processor;

import com.storyscawler.core.model.ChapterResult;
import com.storyscawler.core.model.CrawlChaptersOption;
import com.storyscawler.core.model.PageResult;
import com.storyscawler.core.processor.CrawlChaptersProcessor;
import com.storyscawler.core.selenium.action.SeleniumActionContext;
import com.storyscawler.infrastructure.exception.PageNotFoundException;
import com.storyscawler.infrastructure.exception.WebDriverPoolEmptyException;
import com.storyscawler.infrastructure.model.enumeration.SourceCode;
import com.storyscawler.infrastructure.pool.webdriverpool.StoryDetailWebDriverPool;
import com.storyscawler.source.metruyencv.filter.MetruyencvChapterTitleFilters;
import com.storyscawler.source.metruyencv.filter.MetruyencvContentFilters;
import com.storyscawler.source.metruyencv.processor.helper.MetruyencvCrawlChapterProcessor;
import com.storyscawler.source.metruyencv.util.MetruyencvUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

@Slf4j
@Component
@RequiredArgsConstructor
public class MetruyencvCrawlChaptersProcessor implements CrawlChaptersProcessor {

    private final MetruyencvContentFilters filters;
    private final MetruyencvChapterTitleFilters titleFilters;
    private final StoryDetailWebDriverPool webDriverPool;
    private final MetruyencvCrawlChapterProcessor metruyencvCrawlChapterProcessor;
    private static final int MAX_PAGE_NOTFOUND = 3;

    public void process(CrawlChaptersOption option, Function<ChapterResult, Boolean> doNext) {
        var webDriver = webDriverPool.get().orElseThrow(WebDriverPoolEmptyException::new);
        try {
            var context = SeleniumActionContext.init(webDriver);

            var baseUrl = MetruyencvUtils.buildChapterBaseFromStoryUrl(option.getStoryUrl());
            var chapterNumber = option.getStartIndex();
            var hasNext = false;
            var pageNotFoundCount = new AtomicInteger(0);
            do {
                var url = baseUrl.replace("{chapterNumber}", chapterNumber + "");
                log.info("Crawling chapter [{}]", url);
                try {
                    var pageResult = metruyencvCrawlChapterProcessor.process(context, url);
                    var canNext = doNext(doNext, pageResult, chapterNumber);
                    hasNext = pageResult.hasNext() && canNext;
                } catch (PageNotFoundException e) {
                    log.warn("Page not found url [{}]", url);
                    if (pageNotFoundCount.incrementAndGet() > MAX_PAGE_NOTFOUND) {
                        log.warn("Page not found url [{}] has reached the limit [{}]. Stopped!", url, MAX_PAGE_NOTFOUND);
                        // if next page but the page is not found => consider it is the last page
                        hasNext = false;
                    }
                }
                chapterNumber++;
            } while (hasNext);
        } finally {
            webDriverPool.destroy(webDriver);
        }
    }

    private boolean doNext(Function<ChapterResult, Boolean> doNext, PageResult<Map<String, String>> pageResult, int chapterNumber) {
        if (Objects.isNull(doNext)) {
            return true;
        }
        var result = pageResult.getContent();
        var contentCleaned = filters.filter(result.get("chapter_content"));
        var titleCleaned = titleFilters.filter(result.get("chapter_title"));
        var chapter = ChapterResult.builder()
                .chapterNumber(chapterNumber)
                .content(contentCleaned)
                .title(titleCleaned)
                .url(result.get("chapter_url"))
                .build();
        return doNext.apply(chapter);
    }

    @Override
    public boolean isSupported(SourceCode sourceCode) {
        return sourceCode == SourceCode.Metruyencv;
    }
}
