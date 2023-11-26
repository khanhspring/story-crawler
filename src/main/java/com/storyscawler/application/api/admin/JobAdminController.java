package com.storyscawler.application.api.admin;

import com.storyscawler.service.job.creator.CrawlChaptersJobsCreator;
import com.storyscawler.service.job.creator.CrawlStoriesJobsCreator;
import com.storyscawler.service.job.creator.CrawlStoryJobsCreator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("admin/jobs")
public class JobAdminController {

    private final CrawlStoriesJobsCreator crawlStoriesJobsCreator;
    private final CrawlStoryJobsCreator crawlStoryJobsCreator;
    private final CrawlChaptersJobsCreator crawlChaptersJobsCreator;

    @PostMapping("stories-crawling")
    public void createStoriesCrawlingJobs() {
        crawlStoriesJobsCreator.create();
    }

    @PostMapping("story-crawling")
    public void creatStoryCrawlingJobs() {
        crawlStoryJobsCreator.create();
    }

    @PostMapping("chapters-crawling")
    public void creatChaptersCrawlingJobs() {
        crawlChaptersJobsCreator.create();
    }
}
