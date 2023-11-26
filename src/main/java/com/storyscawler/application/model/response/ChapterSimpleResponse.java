package com.storyscawler.application.model.response;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChapterSimpleResponse {
    private int index;
    private String title;
    private Instant createdDate;
}
