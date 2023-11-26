package com.storyscawler.service.crawlstoryjob;

import com.storyscawler.infrastructure.model.enumeration.CrawlStoryJobStatus;
import com.storyscawler.infrastructure.repository.JpaCrawlStoryJobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class CrawlStoryJobServiceImpl implements CrawlStoryJobService {
    private final JpaCrawlStoryJobRepository jpaCrawlStoryJobRepository;

    private static final int MAX_FAILED = 3;

    @Override
    @Transactional
    public void start(Long id) {
        jpaCrawlStoryJobRepository.updateStatus(id, CrawlStoryJobStatus.InProgress);
    }

    @Override
    @Transactional
    public void complete(Long id) {
        jpaCrawlStoryJobRepository.updateStatus(id, CrawlStoryJobStatus.Completed);
        jpaCrawlStoryJobRepository.updateCompletedTime(id, Instant.now());
    }

    @Override
    @Transactional
    public void ready(Long id) {
        jpaCrawlStoryJobRepository.updateStatus(id, CrawlStoryJobStatus.Ready);
    }

    @Override
    @Transactional
    public void onFailed(Long id, String errorMessage) {
        var job = jpaCrawlStoryJobRepository.findById(id)
                .orElseThrow();
        job.setErrorMessage(errorMessage);
        job.setFailedCount(job.getFailedCount() + 1);
        if (job.getFailedCount() > MAX_FAILED) {
            job.setStatus(CrawlStoryJobStatus.Failed);
        } else {
            job.setStatus(CrawlStoryJobStatus.Ready);
        }
        jpaCrawlStoryJobRepository.save(job);
    }
}
