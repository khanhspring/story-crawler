package com.storyscawler.infrastructure.repository;

import com.storyscawler.infrastructure.model.entity.crawl.JpaCrawlStoryJob;
import com.storyscawler.infrastructure.model.enumeration.CrawlStoryJobStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

public interface JpaCrawlStoryJobRepository extends JpaRepository<JpaCrawlStoryJob, Long> {

    Slice<JpaCrawlStoryJob> findAllByStatusOrderByPriorityDescCreatedDateAsc(CrawlStoryJobStatus status, Pageable pageable);

    @Modifying
    @Transactional
    @Query("update JpaCrawlStoryJob o set o.status = com.storyscawler.infrastructure.model.enumeration.CrawlStoryJobStatus.Ready" +
            " where o.status = com.storyscawler.infrastructure.model.enumeration.CrawlStoryJobStatus.InProgress")
    void updateInProgressToReady();

    @Modifying
    @Transactional
    @Query("update JpaCrawlStoryJob o set o.status = :status where o.id = :id")
    void updateStatus(@Param("id") Long id, @Param("status") CrawlStoryJobStatus status);

    @Modifying
    @Transactional
    @Query("update JpaCrawlStoryJob o set o.url = :url where o.id = :id")
    void updateUrl(@Param("id") Long id, @Param("url") String url);

    @Modifying
    @Transactional
    @Query("update JpaCrawlStoryJob o set o.completedTime = :completedTime where o.id = :id")
    void updateCompletedTime(@Param("id") Long id, @Param("completedTime") Instant completedTime);
}