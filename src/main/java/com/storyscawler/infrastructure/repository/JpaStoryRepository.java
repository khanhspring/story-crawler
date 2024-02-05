package com.storyscawler.infrastructure.repository;

import com.storyscawler.application.model.request.StorySearchRequest;
import com.storyscawler.infrastructure.model.entity.JpaStory;
import com.storyscawler.infrastructure.model.enumeration.StoryStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.Optional;

public interface JpaStoryRepository extends JpaRepository<JpaStory, Long> {

    @Query("select o from JpaStory o" +
            " left join JpaCrawlStoryJob j on j.story.id = o.id and j.status in (com.storyscawler.infrastructure.model.enumeration.CrawlStoryJobStatus.Ready, com.storyscawler.infrastructure.model.enumeration.CrawlStoryJobStatus.InProgress)" +
            " where" +
            " j.id is null" +
            " and (" +
            "   o.status in (com.storyscawler.infrastructure.model.enumeration.StoryStatus.Draft) " +
            "   or (o.completed = false and o.lastModifiedDate < :lastModifiedDate)" +
            " )" +
            " and o.source.status = com.storyscawler.infrastructure.model.enumeration.SourceStatus.Active" +
            " order by o.createdDate asc")
    Slice<JpaStory> findStoriesWithoutActiveCrawlInfoJob(@Param("lastModifiedDate") Instant lastModifiedDate, Pageable pageable);

    @Query("select o from JpaStory o" +
            " left join JpaCrawlChaptersJob j on j.story.id = o.id and j.status in (com.storyscawler.infrastructure.model.enumeration.CrawlChaptersJobStatus.Ready, com.storyscawler.infrastructure.model.enumeration.CrawlChaptersJobStatus.InProgress)" +
            " where" +
            " j.id is null" +
            " and (" +
            "   o.status in (com.storyscawler.infrastructure.model.enumeration.StoryStatus.Draft) " +
            "   or (o.completed = false and o.lastModifiedDate < :lastModifiedDate)" +
            " )" +
            " and o.source.status = com.storyscawler.infrastructure.model.enumeration.SourceStatus.Active" +
            " order by o.createdDate asc")
    Slice<JpaStory> findStoriesWithoutActiveCrawlChaptersJob(@Param("lastModifiedDate") Instant lastModifiedDate, Pageable pageable);

    boolean existsBySlug(String slug);
    Optional<JpaStory> findBySlugAndStatus(String slug, StoryStatus status);

    @Query("select o from JpaStory o" +
            " join fetch o.genres g" +
            " where (:#{#request.keyword == null} = true or o.title like %:#{#request.keyword}%)" +
            " and (:#{#request.completed == null} = true or o.completed = :#{#request.completed})" +
            " and (:#{#request.authorId == null} = true or o.author.id = :#{#request.authorId})" +
            " and (:#{#request.genreIds == null} = true or g.id in (:#{#request.genreIds}))" +
            " and o.status = com.storyscawler.infrastructure.model.enumeration.StoryStatus.Active")
    Page<JpaStory> search(@Param("request") StorySearchRequest request, Pageable pageable);

    Page<JpaStory> findAllByGenresCode(String genreCode, Pageable pageable);
    Page<JpaStory> findAllByAuthorCode(String authorCode, Pageable pageable);

    @Modifying
    @Query("update JpaStory o set o.totalView = o.totalView + 1 where o.id = :id")
    void increaseView(@Param("id") Long id);
}