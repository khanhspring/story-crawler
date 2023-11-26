package com.storyscawler.service.job.bootstrapper;

import com.storyscawler.infrastructure.repository.JpaCrawlStoryJobRepository;
import com.storyscawler.infrastructure.util.DelayUtils;
import com.storyscawler.service.job.executor.CrawlStoryJobExecutor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.time.Duration;

import static com.storyscawler.infrastructure.model.enumeration.CrawlStoryJobStatus.Ready;

@Slf4j
@Component
@RequiredArgsConstructor
public class CrawlStoryJobsBootstrapper {

    private final CrawlStoryJobExecutor jobExecutor;
    private final JpaCrawlStoryJobRepository jpaCrawlStoryJobRepository;
    private static final Duration DELAY_BETWEEN_JOBS = Duration.ofSeconds(2);

    @Transactional
    public void start() {
        log.info("Start executing crawl story jobs");

        var page = PageRequest.of(0, 10);
        var jobs = jpaCrawlStoryJobRepository.findAllByStatusOrderByPriorityDescCreatedDateAsc(Ready, page).getContent();

        if (ObjectUtils.isEmpty(jobs)) {
            log.info("Has no crawl story job ready to run");
            return;
        }

        for (var job : jobs) {
            jobExecutor.execute(job);
            DelayUtils.delay(DELAY_BETWEEN_JOBS);
        }
    }
}
