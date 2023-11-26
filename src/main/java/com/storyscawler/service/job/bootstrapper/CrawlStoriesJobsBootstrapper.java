package com.storyscawler.service.job.bootstrapper;

import com.storyscawler.infrastructure.model.enumeration.CrawlStoriesJobStatus;
import com.storyscawler.infrastructure.repository.JpaCrawlStoriesJobRepository;
import com.storyscawler.infrastructure.util.DelayUtils;
import com.storyscawler.service.job.executor.CrawlStoriesJobExecutor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.time.Duration;

@Slf4j
@Component
@RequiredArgsConstructor
public class CrawlStoriesJobsBootstrapper {

    private final CrawlStoriesJobExecutor jobExecutor;
    private final JpaCrawlStoriesJobRepository jpaCrawlStoriesJobRepository;

    private static final Duration DELAY_BETWEEN_JOBS = Duration.ofSeconds(1);

    @Transactional
    public void start() {
        log.info("Start executing crawl stories jobs");

        var page = PageRequest.of(0, 10);
        var jobs = jpaCrawlStoriesJobRepository.findAllByStatusOrderByCreatedDateAsc(CrawlStoriesJobStatus.Ready, page).getContent();

        if (ObjectUtils.isEmpty(jobs)) {
            log.info("Has no crawl stories job ready to run");
            return;
        }

        for (var job : jobs) {
            jobExecutor.execute(job);
            DelayUtils.delay(DELAY_BETWEEN_JOBS);
        }
    }
}
