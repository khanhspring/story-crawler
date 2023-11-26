package com.storyscawler.infrastructure.model.entity.crawl;

import com.storyscawler.infrastructure.model.entity.JpaBaseEntity;
import com.storyscawler.infrastructure.model.entity.JpaSource;
import com.storyscawler.infrastructure.model.enumeration.CrawlStoriesJobStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "crawl_stories_job")
public class JpaCrawlStoriesJob extends JpaBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "source_id")
    private JpaSource source;

    private String baseUrl;

    @Enumerated(EnumType.STRING)
    private CrawlStoriesJobStatus status;

    private int currentPage;

    private Instant completedTime;
    private String errorMessage;
    private int failedCount;
}
