package com.storyscawler.service.job.executor.impl;

import com.storyscawler.core.model.StoriesResult;
import com.storyscawler.core.model.StoryResult;
import com.storyscawler.infrastructure.exception.PoolEmptyException;
import com.storyscawler.infrastructure.model.entity.JpaSource;
import com.storyscawler.infrastructure.model.entity.JpaStory;
import com.storyscawler.infrastructure.model.entity.crawl.JpaCrawlStoriesJob;
import com.storyscawler.infrastructure.model.enumeration.StoryStatus;
import com.storyscawler.infrastructure.property.AppConfigProperties;
import com.storyscawler.infrastructure.repository.JpaStoryRepository;
import com.storyscawler.service.crawlstoriesjob.CrawlStoriesJobService;
import com.storyscawler.service.job.executor.CrawlStoriesJobExecutor;
import com.storyscawler.service.processor.CrawlStoriesProcessorFactory;
import com.storyscawler.service.storyview.StoryViewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
@RequiredArgsConstructor
public class CrawlStoriesJobExecutorImpl implements CrawlStoriesJobExecutor {

    private static final int MAX_DUPLICATED = 40;
    private static final int POSITION_DELTA = 1000;

    private final CrawlStoriesProcessorFactory processorFactory;
    private final JpaStoryRepository jpaStoryRepository;
    private final StoryViewService storyViewService;
    private final CrawlStoriesJobService crawlStoriesJobService;
    private final AppConfigProperties properties;

    @Async
    public void execute(JpaCrawlStoriesJob job) {
        try {
            log.info("Start crawling stories job id [{}] for [{}]", job.getId(), job.getSource().getCode());
            AtomicInteger duplicatedCount = new AtomicInteger(0);
            crawlStoriesJobService.start(job.getId());

            processorFactory.get(job.getSource().getCode())
                    .process(job.getBaseUrl(), job.getCurrentPage() + 1, (r) -> doOnNext(job, r, duplicatedCount));

            crawlStoriesJobService.complete(job.getId());
            log.info("Completed crawling stories job id [{}] for [{}]", job.getId(), job.getSource().getCode());
        } catch (PoolEmptyException e) {
            log.warn("{}. Stopped!", e.getMessage());
            crawlStoriesJobService.ready(job.getId());
        } catch (Exception e) {
            log.warn("Cannot start crawling stories job id [{}] for [{}]", job.getId(), job.getSource().getCode(), e);
            crawlStoriesJobService.onFailed(job.getId(), e.getMessage());
        }
    }

    private boolean doOnNext(JpaCrawlStoriesJob job, StoriesResult result, AtomicInteger duplicatedCount) {
        var stories = result.getStories();
        if (ObjectUtils.isEmpty(stories)) {
            log.info("Has no stories found on the page number [{}]", result.getPageNumber());
            return false;
        }
        int i = 0;
        for (var storyResult : stories) {
            var slug = storyResult.getSlug();
            var existed = jpaStoryRepository.existsBySlug(slug);
            if (existed) {
                log.info("The story [{}] is already existed in the DB", slug);
                var totalDuplicated = duplicatedCount.incrementAndGet();
                if (totalDuplicated > MAX_DUPLICATED) {
                    log.info("Total duplicated stories [{}] has exceeded the limit [{}]. Stopped!", totalDuplicated, MAX_DUPLICATED);
                    return false;
                }
            } else {
                addStory(storyResult, job.getSource(), slug, result.getPageNumber(), i++);
            }
        }
        crawlStoriesJobService.setCurrentPage(job.getId(), result.getPageNumber());

        return result.getPageNumber() < properties.getStoriesPageLimit();
    }

    private void addStory(StoryResult storyResult, JpaSource source, String slug, int pageNumber, int index) {
        var position = (POSITION_DELTA * pageNumber) + index;

        var initTotalView = RandomUtils.nextInt(200, 400);
        var story = JpaStory.builder()
                .title(storyResult.getTitle())
                .slug(slug)
                .status(StoryStatus.Draft)
                .source(source)
                .externalUrl(storyResult.getUrl())
                .totalView(initTotalView)
                .position(position)
                .build();
        story = jpaStoryRepository.save(story);
        storyViewService.initViews(story.getId(), initTotalView, LocalDate.now());
    }
}
