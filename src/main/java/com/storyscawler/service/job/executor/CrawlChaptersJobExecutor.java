package com.storyscawler.service.job.executor;

import com.storyscawler.infrastructure.model.entity.crawl.JpaCrawlChaptersJob;

public interface CrawlChaptersJobExecutor {

    void execute(JpaCrawlChaptersJob job);
}
