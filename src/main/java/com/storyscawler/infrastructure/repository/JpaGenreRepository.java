package com.storyscawler.infrastructure.repository;

import com.storyscawler.application.model.request.GenreSearchRequest;
import com.storyscawler.infrastructure.model.entity.JpaGenre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface JpaGenreRepository extends JpaRepository<JpaGenre, Long> {

    Optional<JpaGenre> findFirstByTitle(String title);
    boolean existsByCode(String code);

    Optional<JpaGenre> findByCode(String code);
    @Query("select o from JpaGenre o" +
            " where (:#{#request.keyword == null} = true or o.title like %:#{#request.keyword}%)")
    Page<JpaGenre> search(@Param("request") GenreSearchRequest request, Pageable pageable);
}