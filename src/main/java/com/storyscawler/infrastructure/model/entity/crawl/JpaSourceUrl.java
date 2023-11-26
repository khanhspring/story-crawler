package com.storyscawler.infrastructure.model.entity.crawl;

import com.storyscawler.infrastructure.model.entity.JpaBaseEntity;
import com.storyscawler.infrastructure.model.entity.JpaSource;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "source_url")
public class JpaSourceUrl extends JpaBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "source_id")
    private JpaSource source;

    private String url;
}
