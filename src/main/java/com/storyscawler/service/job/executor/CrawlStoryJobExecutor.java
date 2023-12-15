package com.storyscawler.service.job.executor;

import com.storyscawler.infrastructure.model.entity.crawl.JpaCrawlStoryJob;

public interface CrawlStoryJobExecutor {

    void execute(JpaCrawlStoryJob job);
    void executeSync(JpaCrawlStoryJob job);
}
