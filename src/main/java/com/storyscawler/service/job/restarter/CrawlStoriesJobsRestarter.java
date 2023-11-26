package com.storyscawler.service.job.restarter;

import com.storyscawler.infrastructure.repository.JpaCrawlStoriesJobRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class CrawlStoriesJobsRestarter {

    private final JpaCrawlStoriesJobRepository jpaCrawlStoriesJobRepository;

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void run() {
        log.info("Restart crawling stories jobs from previous deployment");
        jpaCrawlStoriesJobRepository.updateInProgressToReady();
    }
}
