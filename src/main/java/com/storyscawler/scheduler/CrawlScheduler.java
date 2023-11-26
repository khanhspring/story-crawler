package com.storyscawler.scheduler;

import com.storyscawler.infrastructure.property.ScheduleProperties;
import com.storyscawler.service.job.bootstrapper.CrawlChaptersJobsBootstrapper;
import com.storyscawler.service.job.bootstrapper.CrawlStoriesJobsBootstrapper;
import com.storyscawler.service.job.bootstrapper.CrawlStoryJobsBootstrapper;
import com.storyscawler.service.job.creator.CrawlChaptersJobsCreator;
import com.storyscawler.service.job.creator.CrawlStoriesJobsCreator;
import com.storyscawler.service.job.creator.CrawlStoryJobsCreator;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CrawlScheduler {

    private final CrawlStoriesJobsCreator crawlStoriesJobsCreator;
    private final CrawlStoriesJobsBootstrapper crawlStoriesJobsBootstrapper;
    private final CrawlStoryJobsBootstrapper crawlStoryJobsBootstrapper;
    private final CrawlStoryJobsCreator crawlStoryJobsCreator;
    private final CrawlChaptersJobsCreator crawlChaptersJobsCreator;
    private final CrawlChaptersJobsBootstrapper crawlChaptersJobsBootstrapper;
    private final ScheduleProperties properties;

    @Scheduled(cron = "${app.schedules.jobs-creation.stories-crawling.cron}")
    public void creatStoriesCrawlingJobs() {
        if (properties.getJobsCreation().getStoriesCrawling().isDisabled()) {
            return;
        }
        crawlStoriesJobsCreator.create();
    }

    @Scheduled(cron = "${app.schedules.jobs-bootstrap.stories-crawling.cron}")
    public void bootstrapStoriesCrawlingJobs() {
        if (properties.getJobsBootstrap().getStoriesCrawling().isDisabled()) {
            return;
        }
        crawlStoriesJobsBootstrapper.start();
    }

    @Scheduled(cron = "${app.schedules.jobs-creation.story-crawling.cron}")
    public void creatStoryCrawlingJobs() {
        if (properties.getJobsCreation().getStoryCrawling().isDisabled()) {
            return;
        }
        crawlStoryJobsCreator.create();
    }

    @Scheduled(cron = "${app.schedules.jobs-bootstrap.story-crawling.cron}")
    public void bootstrapStoryCrawlingJobs() {
        if (properties.getJobsBootstrap().getStoryCrawling().isDisabled()) {
            return;
        }
        crawlStoryJobsBootstrapper.start();
    }

    @Scheduled(cron = "${app.schedules.jobs-creation.chapters-crawling.cron}")
    public void creatChaptersCrawlingJobs() {
        if (properties.getJobsCreation().getChaptersCrawling().isDisabled()) {
            return;
        }
        crawlChaptersJobsCreator.create();
    }

    @Scheduled(cron = "${app.schedules.jobs-bootstrap.chapters-crawling.cron}")
    public void bootstrapChaptersCrawlingJobs() {
        if (properties.getJobsBootstrap().getChaptersCrawling().isDisabled()) {
            return;
        }
        crawlChaptersJobsBootstrapper.start();
    }
}
