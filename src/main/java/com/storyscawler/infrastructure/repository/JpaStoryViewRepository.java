package com.storyscawler.infrastructure.repository;

import com.storyscawler.infrastructure.model.entity.JpaStoryView;
import com.storyscawler.infrastructure.model.projection.JpaTopStoryProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface JpaStoryViewRepository extends JpaRepository<JpaStoryView, Long> {

    @Modifying
    @Query(value = "insert into story_view (story_id, view_count, stats_date, created_date, version)" +
            " values (:storyId, 1, :statsDate, CURRENT_TIMESTAMP, 0)" +
            " on conflict (story_id, stats_date)" +
            " do update set" +
            " view_count = story_view.view_count + 1," +
            " last_modified_date = CURRENT_TIMESTAMP",
            nativeQuery = true)
    void increaseView(@Param("storyId") Long storyId, @Param("statsDate") LocalDate statsDate);

    Optional<JpaStoryView> findAllByStoryIdAndStatsDate(Long storyId, LocalDate statsDate);

    @Query("select  v.story as story, sum(v.viewCount) as totalView from JpaStoryView v" +
            " where" +
            " (cast(:statsDateFrom as date) is null or v.statsDate >= :statsDateFrom)" +
            " and" +
            " (cast(:statsDateTo as date) is null or v.statsDate <= :statsDateTo)" +
            " group by v.story" +
            " order by sum(v.viewCount) desc")
    Page<JpaTopStoryProjection> searchTopStories(@Param("statsDateFrom") LocalDate statsDateFrom, @Param("statsDateTo") LocalDate statsDateTo, Pageable pageable);
}