package com.storyscawler.service.job.creator;

import com.storyscawler.infrastructure.model.entity.JpaStory;
import com.storyscawler.infrastructure.model.entity.crawl.JpaCrawlChaptersJob;
import com.storyscawler.infrastructure.repository.JpaChapterRepository;
import com.storyscawler.infrastructure.repository.JpaCrawlChaptersJobRepository;
import com.storyscawler.infrastructure.repository.JpaStoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

import static com.storyscawler.infrastructure.model.enumeration.CrawlChaptersJobStatus.Ready;

@Slf4j
@Component
@RequiredArgsConstructor
public class CrawlChaptersJobsCreator {

    private final JpaCrawlChaptersJobRepository jpaCrawlChaptersJobRepository;
    private final JpaStoryRepository jpaStoryRepository;
    private final JpaChapterRepository jpaChapterRepository;
    private static final Duration UPDATE_CHAPTERS_INTERVAL = Duration.ofHours(5);

    @Transactional
    public void create() {
        log.info("Start create jobs to crawl chapters");
        var page = PageRequest.of(0, 25);
        var hasNext = false;
        var lastModifiedDate = Instant.now().minus(UPDATE_CHAPTERS_INTERVAL);
        do {
            var results = jpaStoryRepository.findStoriesWithoutActiveCrawlChaptersJob(lastModifiedDate, page);
            log.info("Creating jobs to crawl chapters... current processing [{}]", results.getSize());

            var stories = results.getContent();
            createJobs(stories);
            hasNext = results.hasContent();
        } while (hasNext);
    }

    private void createJobs(List<JpaStory> stories) {
        stories.forEach(this::createJob);
    }

    private void createJob(JpaStory story) {
        var latestChapter = jpaChapterRepository.findFirstByStoryIdOrderByIndexDesc(story.getId())
                .orElse(null);

        int startIndex = 1;
        String startUrl = null;
        if (Objects.nonNull(latestChapter)) {
            startIndex = latestChapter.getIndex();
            startUrl = latestChapter.getExternalUrl();
        }

        var job = JpaCrawlChaptersJob.builder()
                .story(story)
                .status(Ready)
                .startUrl(startUrl)
                .startIndex(startIndex)
                .build();
        jpaCrawlChaptersJobRepository.save(job);
    }
}
