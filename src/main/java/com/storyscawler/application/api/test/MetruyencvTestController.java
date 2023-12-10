package com.storyscawler.application.api.test;

import com.storyscawler.core.model.ChapterResult;
import com.storyscawler.core.model.CrawlChaptersOption;
import com.storyscawler.core.model.StoryInfoResult;
import com.storyscawler.source.metruyencv.processor.MetruyencvCrawlChaptersProcessor;
import com.storyscawler.source.metruyencv.processor.MetruyencvCrawlStoriesProcessor;
import com.storyscawler.source.metruyencv.processor.MetruyencvCrawlStoryInfoProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
//@Profile("local")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "test/metruyencv")
public class MetruyencvTestController {

    private final MetruyencvCrawlChaptersProcessor metruyencvCrawlChaptersProcessor;
    private final MetruyencvCrawlStoryInfoProcessor metruyencvCrawlStoryInfoProcessor;
    private final MetruyencvCrawlStoriesProcessor metruyencvCrawlStoriesProcessor;

    @GetMapping("crawl-chapters")
    public void crawlChapters(@RequestParam String url, @RequestParam int startIndex) {
        var option = CrawlChaptersOption.builder()
                .startIndex(startIndex)
                .startUrl(null)
                .storyUrl(url)
                .build();
        metruyencvCrawlChaptersProcessor.process(option, (ChapterResult result) -> {
            log.info("Chapter number: {}", result.getChapterNumber());
            log.info("Content: {}", result.getContent());
            log.info("========================");
            return false;
        });
    }

    @GetMapping("crawl-story-info")
    public void crawlStoryInfo(@RequestParam String url) {
        metruyencvCrawlStoryInfoProcessor.process(url, (StoryInfoResult r) -> {
            log.info("AuthorName: {}", r.getAuthorName());
            log.info("Summary: {}", r.getSummary());
            log.info("Tag: {}", r.getSubGenres());
            log.info("========================");
        });
    }

    @GetMapping("crawl-stories")
    public void crawlStories(@RequestParam String url) {
        metruyencvCrawlStoriesProcessor.process(url, 1, null);
    }
}
