package com.storyscawler.service.job.creator;

import com.storyscawler.infrastructure.model.entity.JpaStory;
import com.storyscawler.infrastructure.model.entity.crawl.JpaCrawlStoryJob;
import com.storyscawler.infrastructure.repository.JpaCrawlStoryJobRepository;
import com.storyscawler.infrastructure.repository.JpaStoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import static com.storyscawler.infrastructure.model.enumeration.CrawlStoryJobStatus.Ready;
import static com.storyscawler.infrastructure.model.enumeration.CrawlStoryJobType.New;
import static com.storyscawler.infrastructure.model.enumeration.CrawlStoryJobType.Update;
import static com.storyscawler.infrastructure.model.enumeration.StoryStatus.Draft;

@Slf4j
@Component
@RequiredArgsConstructor
public class CrawlStoryJobsCreator {

    private final JpaCrawlStoryJobRepository jpaCrawlStoryJobRepository;
    private final JpaStoryRepository jpaStoryRepository;

    private static final int DEFAULT_PRIORITY = 100;
    private static final int NEW_STORY_PRIORITY = 200;
    private static final Duration UPDATE_STORIES_INTERVAL = Duration.ofDays(1);

    @Transactional
    public void create() {
        log.info("Start create jobs to crawl story");
        var page = PageRequest.of(0, 25);
        var hasNext = false;
        var lastModifiedDate = Instant.now().minus(UPDATE_STORIES_INTERVAL);
        do {
            var results = jpaStoryRepository.findStoriesWithoutActiveCrawlInfoJob(lastModifiedDate, page);
            log.info("Creating jobs to crawl story... current processing [{}]", results.getSize());

            var stories = results.getContent();
            createJobs(stories);
            hasNext = results.hasContent();
        } while (hasNext);
    }

    private void createJobs(List<JpaStory> stories) {
        stories.forEach(this::createJob);
    }

    private void createJob(JpaStory story) {
        var type = story.getStatus() == Draft ? New : Update;
        var priority = type == New ? NEW_STORY_PRIORITY : DEFAULT_PRIORITY;

        var job = JpaCrawlStoryJob.builder()
                .story(story)
                .status(Ready)
                .priority(priority)
                .type(type)
                .build();
        jpaCrawlStoryJobRepository.save(job);
    }
}
