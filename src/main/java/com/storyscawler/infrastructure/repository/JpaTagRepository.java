package com.storyscawler.infrastructure.repository;

import com.storyscawler.infrastructure.model.entity.JpaTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaTagRepository extends JpaRepository<JpaTag, Long> {

    Optional<JpaTag> findFirstByTitle(String title);
    boolean existsByCode(String title);
}