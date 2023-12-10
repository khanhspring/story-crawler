package com.storyscawler.infrastructure.model.entity;

import com.storyscawler.infrastructure.model.enumeration.GenreType;
import com.storyscawler.infrastructure.model.enumeration.StoryStatus;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "genre")
public class JpaGenre extends JpaBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String title;
    @Enumerated(EnumType.STRING)
    private GenreType type;
    private String category;
}
