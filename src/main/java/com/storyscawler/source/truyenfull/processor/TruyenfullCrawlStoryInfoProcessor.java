package com.storyscawler.source.truyenfull.processor;

import com.storyscawler.core.ElementExtractor;
import com.storyscawler.core.jsoup.action.JsoupActionContext;
import com.storyscawler.core.jsoup.action.JsoupActionExecutor;
import com.storyscawler.core.model.StoryInfoResult;
import com.storyscawler.core.model.StoryInfoResult.StoryGenreResult;
import com.storyscawler.core.processor.CrawlStoryInfoProcessor;
import com.storyscawler.core.selector.ElementLocator;
import com.storyscawler.infrastructure.exception.ApplicationException;
import com.storyscawler.infrastructure.model.enumeration.SourceCode;
import com.storyscawler.infrastructure.pool.executorpool.StoryDetailJsoupExecutorPool;
import com.storyscawler.source.truyenfull.util.TruyenfullUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

@Slf4j
@Component
@RequiredArgsConstructor
public class TruyenfullCrawlStoryInfoProcessor implements CrawlStoryInfoProcessor {

    private final StoryDetailJsoupExecutorPool executorPool;

    public void process(String url, Consumer<StoryInfoResult> callback) {
        var future = executorPool.execute(() -> {
            log.info("Start crawling story info {}", url);
            var context = JsoupActionContext.init();
            var executor = new JsoupActionExecutor(context);
            executor.access(url);

            var titleExtractor = ElementExtractor
                    .element(ElementLocator.cssSelector(".col-info-desc .title"))
                    .text("title");
            var title = executor.extractSingleData(titleExtractor);

            var summaryExtractor = ElementExtractor
                    .element(ElementLocator.cssSelector(".desc-text"))
                    .html("summary");
            var summary = executor.extractSingleData(summaryExtractor);

            var thumbnailExtractor = ElementExtractor
                    .element(ElementLocator.cssSelector(".info-holder .books .book img"))
                    .attr("thumbnail_url", "src");
            var thumbnail = executor.extractSingleData(thumbnailExtractor);

            var authorExtractor = ElementExtractor
                    .element(ElementLocator.cssSelector(".info-holder .info div:nth-child(1) a"))
                    .text("author_name")
                    .attr("author_url", "href");
            var author = executor.extractSingleData(authorExtractor);

            var genresExtractor = ElementExtractor
                    .elements(ElementLocator.cssSelector(".info-holder .info div:nth-child(2) a"))
                    .text("genre_name")
                    .attr("genre_url", "href");
            var genres = executor.extractData(genresExtractor);

            var statusExtractor = ElementExtractor
                    .element(ElementLocator.cssSelector(".info-holder .info div:nth-child(4) span"))
                    .text("status_name");
            var status = executor.extractSingleData(statusExtractor);

            List<StoryGenreResult> genreResults = new ArrayList<>();
            for (var genre : genres) {
                var genreResult = StoryGenreResult.builder()
                        .name(genre.get("genre_name"))
                        .url(genre.get("genre_url"))
                        .build();
                genreResults.add(genreResult);
            }
            var storyInfoResult = StoryInfoResult.builder()
                    .authorName(author.get("author_name"))
                    .authorUrl(author.get("author_url"))
                    .thumbnailUrl(thumbnail.get("thumbnail_url"))
                    .completed(TruyenfullUtils.isCompletedStatus(status.get("status_name")))
                    .summary(summary.get("summary"))
                    .title(title.get("title"))
                    .genres(genreResults)
                    .build();
            doCallback(callback, storyInfoResult);
        });
        try {
            future.get();
        } catch (InterruptedException e) {
            throw new ApplicationException(e);
        } catch (ExecutionException e) {
            throw new ApplicationException(e.getCause());
        }
    }

    private void doCallback(Consumer<StoryInfoResult> callback, StoryInfoResult storyInfoResult) {
        if (Objects.isNull(callback)) {
            return;
        }
        callback.accept(storyInfoResult);
    }

    @Override
    public boolean isSupported(SourceCode sourceCode) {
        return sourceCode == SourceCode.Truyenfull && false; // TODO: disable
    }
}
