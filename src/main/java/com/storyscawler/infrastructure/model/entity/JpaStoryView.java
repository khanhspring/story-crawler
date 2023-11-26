package com.storyscawler.infrastructure.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "story_view")
public class JpaStoryView extends JpaBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int viewCount;
    private LocalDate statsDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "story_id")
    private JpaStory story;
}
