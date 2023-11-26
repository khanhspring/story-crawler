package com.storyscawler.service.job.executor;

import com.storyscawler.infrastructure.model.entity.crawl.JpaCrawlStoriesJob;

public interface CrawlStoriesJobExecutor {

    void execute(JpaCrawlStoriesJob job);
}
