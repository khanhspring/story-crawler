package com.storyscawler.service.crawlstoriesjob;

import com.storyscawler.infrastructure.model.enumeration.CrawlStoriesJobStatus;
import com.storyscawler.infrastructure.repository.JpaCrawlStoriesJobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class CrawlStoriesJobServiceImpl implements CrawlStoriesJobService {
    private final JpaCrawlStoriesJobRepository jpaCrawlStoriesJobRepository;
    private static final int MAX_FAILED = 3;

    @Override
    @Transactional
    public void start(Long id) {
        jpaCrawlStoriesJobRepository.updateStatus(id, CrawlStoriesJobStatus.InProgress);
    }

    @Override
    @Transactional
    public void complete(Long id) {
        var job = jpaCrawlStoriesJobRepository.findById(id)
                .orElseThrow();
        job.setStatus(CrawlStoriesJobStatus.Completed);
        job.setCompletedTime(Instant.now());
        jpaCrawlStoriesJobRepository.save(job);
    }

    @Override
    @Transactional
    public void ready(Long id) {
        jpaCrawlStoriesJobRepository.updateStatus(id, CrawlStoriesJobStatus.Ready);
    }

    @Override
    @Transactional
    public void setCurrentPage(Long id, int currentPage) {
        jpaCrawlStoriesJobRepository.setCurrentPage(id, currentPage);
    }

    @Override
    @Transactional
    public void onFailed(Long id, String errorMessage) {
        var job = jpaCrawlStoriesJobRepository.findById(id)
                .orElseThrow();
        job.setErrorMessage(errorMessage);
        job.setFailedCount(job.getFailedCount() + 1);
        if (job.getFailedCount() > MAX_FAILED) {
            job.setStatus(CrawlStoriesJobStatus.Failed);
        } else {
            job.setStatus(CrawlStoriesJobStatus.Ready);
        }
    }
}
