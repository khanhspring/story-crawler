package com.storyscawler.service.crawlchaptersjob;

import com.storyscawler.infrastructure.model.enumeration.CrawlChaptersJobStatus;
import com.storyscawler.infrastructure.repository.JpaCrawlChaptersJobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class CrawlChaptersJobServiceImpl implements CrawlChaptersJobService {
    private final JpaCrawlChaptersJobRepository jpaCrawlChaptersJobRepository;
    private static final int MAX_FAILED = 3;

    @Override
    @Transactional
    public void start(Long id) {
        jpaCrawlChaptersJobRepository.updateStatus(id, CrawlChaptersJobStatus.InProgress);
    }

    @Override
    @Transactional
    public void complete(Long id) {
        var job = jpaCrawlChaptersJobRepository.findById(id)
                        .orElseThrow();
        job.setEndIndex(job.getCurrentIndex());
        job.setEndUrl(job.getCurrentUrl());
        job.setStatus(CrawlChaptersJobStatus.Completed);
        job.setCompletedTime(Instant.now());
        jpaCrawlChaptersJobRepository.save(job);
    }

    @Override
    @Transactional
    public void ready(Long id) {
        jpaCrawlChaptersJobRepository.updateStatus(id, CrawlChaptersJobStatus.Ready);
    }

    @Override
    @Transactional
    public void updateCurrentChapter(Long id, int currentChapter, String currentUrl) {
        var job = jpaCrawlChaptersJobRepository.findById(id)
                .orElseThrow();
        job.setCurrentIndex(currentChapter);
        job.setCurrentUrl(currentUrl);
        jpaCrawlChaptersJobRepository.save(job);
    }

    @Override
    @Transactional
    public void onFailed(Long id, String errorMessage) {
        var job = jpaCrawlChaptersJobRepository.findById(id)
                .orElseThrow();
        job.setErrorMessage(errorMessage);
        job.setFailedCount(job.getFailedCount() + 1);
        if (job.getFailedCount() > MAX_FAILED) {
            job.setStatus(CrawlChaptersJobStatus.Failed);
        } else {
            job.setStatus(CrawlChaptersJobStatus.Ready);
        }
    }
}
