package com.storyscawler.source.metruyencv.processor;

import com.storyscawler.core.ElementExtractor;
import com.storyscawler.core.model.StoryInfoResult;
import com.storyscawler.core.model.StoryInfoResult.StoryGenreResult;
import com.storyscawler.core.model.StoryInfoResult.StoryTagResult;
import com.storyscawler.core.processor.CrawlStoryInfoProcessor;
import com.storyscawler.core.selector.ElementLocator;
import com.storyscawler.core.selenium.action.SeleniumActionContext;
import com.storyscawler.core.selenium.action.SeleniumActionExecutor;
import com.storyscawler.infrastructure.exception.WebDriverPoolEmptyException;
import com.storyscawler.infrastructure.model.enumeration.SourceCode;
import com.storyscawler.infrastructure.pool.webdriverpool.StoryListingWebDriverPool;
import com.storyscawler.source.metruyencv.filter.MetruyencvContentFilters;
import com.storyscawler.source.metruyencv.util.MetruyencvUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

@Slf4j
@Component
@RequiredArgsConstructor
public class MetruyencvCrawlStoryInfoProcessor implements CrawlStoryInfoProcessor {

    private final StoryListingWebDriverPool webDriverPool;
    private final MetruyencvContentFilters filters;

    public void process(String url, Consumer<StoryInfoResult> callback) {
        var webDriver = webDriverPool.get().orElseThrow(WebDriverPoolEmptyException::new);
        try {
            var context = SeleniumActionContext.init(webDriver);
            var executor = new SeleniumActionExecutor(context);
            executor.access(url);

            var titleExtractor = ElementExtractor
                    .element(ElementLocator.tagName("h1"))
                    .text("title");
            var title = executor.extractSingleData(titleExtractor);

            var summaryExtractor = ElementExtractor
                    .element(ElementLocator.xpath("//*[@id=\"nav-intro\"]/div/div[1]/div[1]/div/p"))
                    .text("summary");
            var summary = executor.extractSingleData(summaryExtractor);

            var thumbnailExtractor = ElementExtractor
                    .element(ElementLocator.xpath("//div[contains(@class, \"nh-thumb\")]/img"))
                    .attr("thumbnail_url", "src");
            var thumbnail = executor.extractSingleData(thumbnailExtractor);

            var authorExtractor = ElementExtractor
                    .element(ElementLocator.xpath("//*[@id=\"app\"]/main/div[3]/div[1]/div/div/div/div[2]/ul[1]/li[1]/a"))
                    .text("author_name")
                    .attr("author_url", "href");
            var author = executor.extractSingleData(authorExtractor);

            var ratingExtractor = ElementExtractor
                    .element(ElementLocator.xpath("//*[@id=\"app\"]/main/div[3]/div[1]/div/div/div/div[2]/div[2]/span[2]/span"))
                    .ignoreException()
                    .timeout(Duration.ofSeconds(1))
                    .text("rating");
            var rating = executor.extractSingleData(ratingExtractor);

            var totalRatingExtractor = ElementExtractor
                    .element(ElementLocator.xpath("//*[@id=\"app\"]/main/div[3]/div[1]/div/div/div/div[2]/div[2]/span[3]"))
                    .ignoreException()
                    .timeout(Duration.ofSeconds(1))
                    .text("total_rating");
            var totalRating = executor.extractSingleData(totalRatingExtractor);

            var genreExtractor = ElementExtractor
                    .element(ElementLocator.xpath("//*[@id=\"app\"]/main/div[3]/div[1]/div/div/div/div[2]/ul[1]/li[3]/a"))
                    .text("genre_name")
                    .attr("genre_url", "href");
            var genre = executor.extractSingleData(genreExtractor);

            var statusExtractor = ElementExtractor
                    .element(ElementLocator.xpath("//*[@id=\"app\"]/main/div[3]/div[1]/div/div/div/div[2]/ul[1]/li[2]"))
                    .text("status_name");
            var status = executor.extractSingleData(statusExtractor);

            var genreResult = StoryGenreResult.builder()
                    .name(genre.get("genre_name"))
                    .url(genre.get("genre_url"))
                    .build();

            List<StoryTagResult> tagResults = new ArrayList<>();
            var infoCount = executor.count(ElementLocator.xpath("//*[@id=\"app\"]/main/div[3]/div[1]/div/div/div/div[2]/ul[1]/li"));
            if (infoCount > 3) {
                var tagsExtractor = ElementExtractor
                        .elements(ElementLocator.xpath("//*[@id=\"app\"]/main/div[3]/div[1]/div/div/div/div[2]/ul[1]/li[position()>3]/a"))
                        .text("tag_name")
                        .attr("tag_url", "href");
                var tags = executor.extractData(tagsExtractor);
                for (var tag : tags) {
                    var tagResult = StoryTagResult.builder()
                            .name(tag.get("tag_name"))
                            .url(tag.get("tag_url"))
                            .build();
                    tagResults.add(tagResult);
                }
            }

            var summaryCleaned = filters.filter(summary.get("summary"));

            Integer totalRatingNum = MetruyencvUtils.extractTotalRating(totalRating.get("total_rating"));
            BigDecimal ratingNum = MetruyencvUtils.convertRating(rating.get("rating"));

            var storyInfoResult = StoryInfoResult.builder()
                    .authorName(author.get("author_name"))
                    .authorUrl(author.get("author_url"))
                    .thumbnailUrl(thumbnail.get("thumbnail_url"))
                    .completed(MetruyencvUtils.isCompletedStatus(status.get("status_name")))
                    .summary(summaryCleaned)
                    .title(title.get("title"))
                    .genres(List.of(genreResult))
                    .tags(tagResults)
                    .rating(ratingNum)
                    .totalRating(totalRatingNum)
                    .build();
            doCallback(callback, storyInfoResult);
        } finally {
            webDriverPool.release(webDriver);
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
        return sourceCode == SourceCode.Metruyencv;
    }
}
