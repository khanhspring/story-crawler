package com.storyscawler.infrastructure.repository;

import com.storyscawler.infrastructure.model.entity.JpaSource;
import com.storyscawler.infrastructure.model.enumeration.SourceStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaSourceRepository extends JpaRepository<JpaSource, Long> {
    Optional<JpaSource> findByCode(String code);
    List<JpaSource> findByStatus(SourceStatus status);
}