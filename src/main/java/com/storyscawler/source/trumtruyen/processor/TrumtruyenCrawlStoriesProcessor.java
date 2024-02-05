package com.storyscawler.source.trumtruyen.processor;

import com.storyscawler.core.jsoup.action.JsoupActionContext;
import com.storyscawler.core.model.PageResult;
import com.storyscawler.core.model.StoriesResult;
import com.storyscawler.core.processor.CrawlStoriesProcessor;
import com.storyscawler.infrastructure.converter.StoriesResultConverter;
import com.storyscawler.infrastructure.exception.ApplicationException;
import com.storyscawler.infrastructure.model.enumeration.SourceCode;
import com.storyscawler.infrastructure.pool.executorpool.StoryListingJsoupExecutorPool;
import com.storyscawler.infrastructure.util.DelayUtils;
import com.storyscawler.source.trumtruyen.processor.helper.TrumtruyenCrawlStoryProcessor;
import com.storyscawler.source.trumtruyen.util.TrumtruyenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;

@Slf4j
@Component
@RequiredArgsConstructor
public class TrumtruyenCrawlStoriesProcessor implements CrawlStoriesProcessor {

    private final StoryListingJsoupExecutorPool executorPool;
    private final StoriesResultConverter storiesResultConverter;
    private final TrumtruyenCrawlStoryProcessor trumtruyenCrawlStoryProcessor;
    private static final Duration DELAY_BETWEEN_PAGES = Duration.ofSeconds(1);

    public void process(String baseUrl, int fromPageNumber, Function<StoriesResult, Boolean> doNext) {
        var future = executorPool.execute(() -> {
            var context = JsoupActionContext.init();
            var pageNumber = fromPageNumber;
            var hasNext = false;
            do {
                String pageNumberPath = "";
                if (pageNumber > 1) {
                    pageNumberPath = "trang-" + pageNumber + "/";
                }
                var url = baseUrl.replace("{pageNumber}", pageNumberPath);
                log.info("[Trumtruyen] Crawling stories url [{}]", url);
                var pageResult = trumtruyenCrawlStoryProcessor.process(context, url);
                var canNext = doNext(doNext, pageResult, pageNumber);
                hasNext = pageResult.hasNext() && canNext;
                pageNumber++;

                DelayUtils.delay(DELAY_BETWEEN_PAGES);
            } while (hasNext);
        });
        try {
            future.get();
        } catch (InterruptedException e) {
            throw new ApplicationException(e);
        } catch (ExecutionException e) {
            throw new ApplicationException(e.getCause());
        }
    }

    private boolean doNext(Function<StoriesResult, Boolean> doNext, PageResult<List<Map<String, String>>> pageResult, int pageNumber) {
        if (Objects.isNull(doNext)) {
            return true;
        }
        var storiesResult = storiesResultConverter.convert(pageResult, pageNumber, TrumtruyenUtils::extractStorySlug);
        return doNext.apply(storiesResult);
    }

    @Override
    public boolean isSupported(SourceCode sourceCode) {
        return sourceCode == SourceCode.Trumtruyen && false; // TODO: disable
    }
}
