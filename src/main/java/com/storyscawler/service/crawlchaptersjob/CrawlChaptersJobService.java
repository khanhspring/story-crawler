package com.storyscawler.service.crawlchaptersjob;

public interface CrawlChaptersJobService {
    void start(Long id);
    void complete(Long id);
    void ready(Long id);
    void updateCurrentChapter(Long id, int currentChapter, String currentUrl);
    void onFailed(Long id, String errorMessage);
}
