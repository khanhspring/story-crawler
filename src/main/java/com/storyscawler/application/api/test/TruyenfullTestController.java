package com.storyscawler.application.api.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.storyscawler.core.jsoup.action.JsoupActionContext;
import com.storyscawler.core.model.CrawlChaptersOption;
import com.storyscawler.source.truyenfull.processor.TruyenfullCrawlChaptersProcessor;
import com.storyscawler.source.truyenfull.processor.helper.TruyenfullCrawlChapterProcessor;
import com.storyscawler.source.truyenfull.processor.TruyenfullCrawlStoriesProcessor;
import com.storyscawler.source.truyenfull.processor.TruyenfullCrawlStoryInfoProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Profile("local")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "test/truyenfull")
public class TruyenfullTestController {

    private final TruyenfullCrawlStoriesProcessor truyenfullCrawlStoriesProcessor;
    private final TruyenfullCrawlStoryInfoProcessor truyenfullCrawlStoryInfoProcessor;
    private final TruyenfullCrawlChapterProcessor truyenfullCrawlChapterProcessor;
    private final TruyenfullCrawlChaptersProcessor truyenfullCrawlChaptersProcessor;

    @GetMapping("crawl-stories")
    public void crawlStories(@RequestParam String url) {
        truyenfullCrawlStoriesProcessor.process(url, 1, (r) -> {
            if (r.getPageNumber() >= 5) {
                return false;
            }
            log.info("Page {}", r.getPageNumber());
            log.info("Stories {}", r.getStories());
            return true;
        });
    }

    @GetMapping("crawl-story-info")
    public void crawlStoryInfo(@RequestParam String url) {
        truyenfullCrawlStoryInfoProcessor.process(url, (r) -> {
            try {
                log.info("Story {}", new ObjectMapper().writeValueAsString(r));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @GetMapping("crawl-chapter")
    public void crawlChapter(@RequestParam String url) throws JsonProcessingException {
        var context = JsoupActionContext.init();
        var result = truyenfullCrawlChapterProcessor.process(context, url);
        log.info("Chapter {}", new ObjectMapper().writeValueAsString(result));
    }

    @GetMapping("crawl-chapters")
    public void crawlChapters(@RequestParam String url) {
        var option = CrawlChaptersOption.builder()
                .startIndex(0)
                .startUrl(null)
                .storyUrl(url)
                .build();
        truyenfullCrawlChaptersProcessor.process(option, (r) -> {
            try {
                log.info("Chapter {}", new ObjectMapper().writeValueAsString(r));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            if (r.getChapterNumber() > 3) {
                return false;
            }
            return true;
        });
    }
}
