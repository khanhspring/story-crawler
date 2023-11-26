package com.storyscawler.infrastructure.repository;

import com.storyscawler.application.model.request.AuthorSearchRequest;
import com.storyscawler.infrastructure.model.entity.JpaAuthor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface JpaAuthorRepository extends JpaRepository<JpaAuthor, Long> {
    Optional<JpaAuthor> findFirstByFullName(String fullName);
    boolean existsByCode(String code);

    Optional<JpaAuthor> findByCode(String code);
    @Query("select o from JpaAuthor o" +
            " where (:#{#request.keyword == null} = true or o.fullName like %:#{#request.keyword}%)")
    Page<JpaAuthor> search(@Param("request") AuthorSearchRequest request, Pageable pageable);
}