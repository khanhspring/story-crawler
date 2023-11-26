package com.storyscawler.infrastructure.repository;

import com.storyscawler.infrastructure.model.entity.JpaChapter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaChapterRepository extends JpaRepository<JpaChapter, Long> {

    Optional<JpaChapter> findByStoryIdAndIndex(Long storyId, int index);
    Optional<JpaChapter> findFirstByStoryIdOrderByIndexDesc(Long storyId);

    Page<JpaChapter> findAllByStorySlug(String storySlug, Pageable pageable);
    Optional<JpaChapter> findByStorySlugAndIndex(String storySlug, int index);

    int countAllByStoryId(Long storyId);
}