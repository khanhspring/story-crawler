package com.storyscawler.service.job.bootstrapper;

import com.storyscawler.infrastructure.repository.JpaCrawlChaptersJobRepository;
import com.storyscawler.infrastructure.util.DelayUtils;
import com.storyscawler.service.job.executor.CrawlChaptersJobExecutor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.time.Duration;

import static com.storyscawler.infrastructure.model.enumeration.CrawlChaptersJobStatus.Ready;

@Slf4j
@Component
@RequiredArgsConstructor
public class CrawlChaptersJobsBootstrapper {

    private final CrawlChaptersJobExecutor jobExecutor;
    private final JpaCrawlChaptersJobRepository jpaCrawlChaptersJobRepository;
    private static final Duration DELAY_BETWEEN_JOBS = Duration.ofMillis(500);

    @Transactional
    public void start() {
        log.info("Start executing crawl chapters jobs");

        var page = PageRequest.of(0, 10);
        var jobs = jpaCrawlChaptersJobRepository.findAllByStatusOrderByCreatedDateAsc(Ready, page).getContent();

        if (ObjectUtils.isEmpty(jobs)) {
            log.info("Has no crawl chapters job ready to run");
            return;
        }

        for (var job : jobs) {
            jobExecutor.execute(job);
            DelayUtils.delay(DELAY_BETWEEN_JOBS);
        }
    }
}
