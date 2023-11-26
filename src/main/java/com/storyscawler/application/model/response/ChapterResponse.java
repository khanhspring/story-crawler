package com.storyscawler.application.model.response;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChapterResponse {
    private int index;
    private String title;
    private String content;
    private Instant createdDate;
    private StorySimpleResponse story;
}
