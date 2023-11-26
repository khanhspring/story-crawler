package com.storyscawler.infrastructure.model.entity.crawl;

import com.storyscawler.infrastructure.model.entity.JpaBaseEntity;
import com.storyscawler.infrastructure.model.entity.JpaStory;
import com.storyscawler.infrastructure.model.enumeration.CrawlChaptersJobStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "crawl_chapters_job")
public class JpaCrawlChaptersJob extends JpaBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "story_id")
    private JpaStory story;

    @Enumerated(EnumType.STRING)
    private CrawlChaptersJobStatus status;

    private int startIndex;
    private String startUrl;

    private Integer currentIndex;
    private String currentUrl;

    private Integer endIndex;
    private String endUrl;

    private Instant completedTime;
    private String errorMessage;
    private int failedCount;
}
