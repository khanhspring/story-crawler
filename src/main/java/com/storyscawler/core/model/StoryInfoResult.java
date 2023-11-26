package com.storyscawler.core.model;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoryInfoResult {
    private String title;
    private String summary;
    private String thumbnailUrl;
    private String authorName;
    private String authorUrl;
    private boolean completed;
    private List<StoryGenreResult> genres;
    private List<StoryTagResult> tags;
    private BigDecimal rating;
    private Integer totalRating;

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StoryGenreResult {
        private String name;
        private String url;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StoryTagResult {
        private String name;
        private String url;
    }
}
