package com.storyscawler.application.model.response;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoryResponse {
    private String slug;
    private String title;
    private String summary;
    private String thumbnailUrl;
    private boolean completed;
    private int totalView;
    private BigDecimal rating;
    private int totalRating;
    private int totalChapter;

    private AuthorResponse author;

    private List<GenreResponse> genres;

    private List<TagResponse> tags;
}
