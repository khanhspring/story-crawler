package com.storyscawler.source.metruyencv.processor;

import com.storyscawler.core.model.PageResult;
import com.storyscawler.core.processor.CrawlStoriesProcessor;
import com.storyscawler.core.selenium.action.SeleniumActionContext;
import com.storyscawler.infrastructure.exception.WebDriverPoolEmptyException;
import com.storyscawler.core.model.StoriesResult;
import com.storyscawler.infrastructure.model.enumeration.SourceCode;
import com.storyscawler.infrastructure.pool.webdriverpool.StoryListingWebDriverPool;
import com.storyscawler.infrastructure.converter.StoriesResultConverter;
import com.storyscawler.source.metruyencv.processor.helper.MetruyencvCrawlStoryProcessor;
import com.storyscawler.source.metruyencv.util.MetruyencvUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Slf4j
@Component
@RequiredArgsConstructor
public class MetruyencvCrawlStoriesProcessor implements CrawlStoriesProcessor {

    private final StoryListingWebDriverPool webDriverPool;
    private final StoriesResultConverter storiesResultConverter;
    private final MetruyencvCrawlStoryProcessor metruyencvCrawlStoryProcessor;

    public void process(String baseUrl, int fromPageNumber, Function<StoriesResult, Boolean> doNext) {
        var webDriver = webDriverPool.get().orElseThrow(WebDriverPoolEmptyException::new);
        try {
            var context = SeleniumActionContext.init(webDriver);
            var pageNumber = fromPageNumber;
            var hasNext = false;
            do {
                var url = baseUrl.replace("{pageNumber}", pageNumber + "");
                log.info("[Metruyencv] Crawling stories url [{}]", url);

                var pageResult = metruyencvCrawlStoryProcessor.process(context, url);

                var canNext = doNext(doNext, pageResult, pageNumber);

                hasNext = pageResult.hasNext() && canNext;
                pageNumber++;
            } while (hasNext);
        } finally {
            webDriverPool.release(webDriver);
        }
    }

    private boolean doNext(Function<StoriesResult, Boolean> doNext, PageResult<List<Map<String, String>>> pageResult, int pageNumber) {
        if (Objects.isNull(doNext)) {
            return true;
        }
        var storiesResult = storiesResultConverter.convert(pageResult, pageNumber, MetruyencvUtils::extractStorySlug);
        return doNext.apply(storiesResult);
    }

    @Override
    public boolean isSupported(SourceCode sourceCode) {
        return sourceCode == SourceCode.Metruyencv;
    }
}
