package com.storyscawler.service.job.restarter;

import com.storyscawler.infrastructure.repository.JpaCrawlChaptersJobRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class CrawlChaptersJobsRestarter {

    private final JpaCrawlChaptersJobRepository jpaCrawlChaptersJobRepository;

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void run() {
        log.info("Restart crawling chapters jobs from previous deployment");
        jpaCrawlChaptersJobRepository.updateInProgressToReady();
    }
}
