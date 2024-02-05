package com.storyscawler.source.truyenfull.processor;

import com.storyscawler.core.jsoup.action.JsoupActionContext;
import com.storyscawler.core.model.ChapterResult;
import com.storyscawler.core.model.CrawlChaptersOption;
import com.storyscawler.core.model.PageResult;
import com.storyscawler.core.processor.CrawlChaptersProcessor;
import com.storyscawler.infrastructure.exception.ApplicationException;
import com.storyscawler.infrastructure.model.enumeration.SourceCode;
import com.storyscawler.infrastructure.pool.executorpool.StoryDetailJsoupExecutorPool;
import com.storyscawler.infrastructure.util.DelayUtils;
import com.storyscawler.source.truyenfull.processor.helper.TruyenfullCrawlChapterProcessor;
import com.storyscawler.source.truyenfull.processor.helper.TruyenfullCrawlFirstChapterUrlProcessor;
import com.storyscawler.source.truyenfull.util.TruyenfullUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

@Slf4j
@Component
@RequiredArgsConstructor
public class TruyenfullCrawlChaptersProcessor implements CrawlChaptersProcessor {

    private final StoryDetailJsoupExecutorPool executorPool;
    private final TruyenfullCrawlChapterProcessor truyenfullCrawlChapterProcessor;
    private final TruyenfullCrawlFirstChapterUrlProcessor truyenfullCrawlFirstChapterUrlProcessor;

    private static final Duration DELAY_BETWEEN_CHAPTERS = Duration.ofSeconds(1);

    public void process(CrawlChaptersOption option, Function<ChapterResult, Boolean> doNext) {
        var future = executorPool.execute(() -> {
            var context = JsoupActionContext.init()
                    .withFollowRedirects(false);

            var chapterNumber = new AtomicInteger(option.getStartIndex());

            String chapterSlug;
            if (Objects.nonNull(option.getStartUrl())) {
                chapterSlug = TruyenfullUtils.extractChapterSlug(option.getStartUrl());
            } else {
                var firstChapterUrl = truyenfullCrawlFirstChapterUrlProcessor.process(option.getStoryUrl());
                chapterSlug = TruyenfullUtils.extractChapterSlug(firstChapterUrl);
            }

            var baseUrl = TruyenfullUtils.buildChapterBaseFromStoryUrl(option.getStoryUrl());
            var hasNext = false;
            do {
                var url = baseUrl.replace("{chapterNumber}", chapterSlug);
                log.info("Crawling chapter [{}]", url);
                var pageResult = truyenfullCrawlChapterProcessor.process(context, url);
                chapterSlug = pageResult.getContent().get("next_chapter_slug");
                var canNext = doNext(doNext, pageResult, chapterNumber.get());
                hasNext = pageResult.hasNext() && canNext;
                chapterNumber.incrementAndGet();

                DelayUtils.delay(DELAY_BETWEEN_CHAPTERS);
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

    private boolean doNext(Function<ChapterResult, Boolean> doNext, PageResult<Map<String, String>> pageResult, int chapterNumber) {
        if (Objects.isNull(doNext)) {
            return true;
        }
        var result = pageResult.getContent();
        var chapter = ChapterResult.builder()
                .chapterNumber(chapterNumber)
                .content(result.get("chapter_content"))
                .title(result.get("chapter_title"))
                .url(result.get("chapter_url"))
                .build();
        return doNext.apply(chapter);
    }

    @Override
    public boolean isSupported(SourceCode sourceCode) {
        return sourceCode == SourceCode.Truyenfull && false; // TODO: disable
    }
}
