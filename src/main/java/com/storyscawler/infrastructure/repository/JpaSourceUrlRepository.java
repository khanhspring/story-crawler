package com.storyscawler.infrastructure.repository;

import com.storyscawler.infrastructure.model.entity.crawl.JpaSourceUrl;
import com.storyscawler.infrastructure.model.enumeration.SourceStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaSourceUrlRepository extends JpaRepository<JpaSourceUrl, Long> {

    List<JpaSourceUrl> findAllBySourceStatus(SourceStatus status);
}