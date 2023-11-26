package com.storyscawler.infrastructure.model.entity;

import com.storyscawler.infrastructure.model.enumeration.StoryStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "story")
public class JpaStory extends JpaBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String slug;
    private String title;
    private String summary;
    private String thumbnailUrl;

    @Enumerated(EnumType.STRING)
    private StoryStatus status;

    private int totalView;
    private int totalRating;
    private int totalChapter;
    private BigDecimal rating;

    private boolean completed;
    private String externalUrl;

    private int position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private JpaAuthor author;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "source_id")
    private JpaSource source;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "story_genre", joinColumns = @JoinColumn(name = "story_id"), inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private Set<JpaGenre> genres;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "story_tag", joinColumns = @JoinColumn(name = "story_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<JpaTag> tags;
}
