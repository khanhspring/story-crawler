package com.storyscawler.application.model.response;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChapterDetailResponse {
    private int index;
    private String title;
    private String content;
    private Instant createdDate;
    private StorySimpleResponse story;
    private ChapterSimpleResponse previous;
    private ChapterSimpleResponse next;
}
