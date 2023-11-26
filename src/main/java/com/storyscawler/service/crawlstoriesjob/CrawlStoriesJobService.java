package com.storyscawler.service.crawlstoriesjob;

public interface CrawlStoriesJobService {
    void start(Long id);
    void complete(Long id);
    void ready(Long id);
    void setCurrentPage(Long id, int currentPage);
    void onFailed(Long id, String errorMessage);
}
