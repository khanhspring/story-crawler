package com.storyscawler.service.job.creator;

import com.storyscawler.infrastructure.model.entity.crawl.JpaCrawlStoriesJob;
import com.storyscawler.infrastructure.model.enumeration.SourceStatus;
import com.storyscawler.infrastructure.repository.JpaCrawlStoriesJobRepository;
import com.storyscawler.infrastructure.repository.JpaSourceUrlRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.storyscawler.infrastructure.model.enumeration.CrawlStoriesJobStatus.InProgress;
import static com.storyscawler.infrastructure.model.enumeration.CrawlStoriesJobStatus.Ready;

@Slf4j
@Component
@RequiredArgsConstructor
public class CrawlStoriesJobsCreator {

    private final JpaSourceUrlRepository jpaSourceUrlRepository;
    private final JpaCrawlStoriesJobRepository jpaCrawlStoriesJobRepository;

    @Transactional
    public void create() {
        log.info("Start create jobs to crawl stories");

        if (isLocked()) {
            log.info("There is another process is in progress for crawling stories. Stopped!");
            return;
        }

        var sourceUrs = jpaSourceUrlRepository.findAllBySourceStatus(SourceStatus.Active);
        log.info("Found [{}] source(s) URLs", sourceUrs.size());

        for (var sourceUrl : sourceUrs) {
            var job = JpaCrawlStoriesJob.builder()
                    .source(sourceUrl.getSource())
                    .status(Ready)
                    .baseUrl(sourceUrl.getUrl())
                    .build();
            jpaCrawlStoriesJobRepository.save(job);
        }
    }

    private boolean isLocked() {
        return jpaCrawlStoriesJobRepository.existsByStatusIn(List.of(InProgress, Ready));
    }
}
