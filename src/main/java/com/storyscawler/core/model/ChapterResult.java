package com.storyscawler.core.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChapterResult {
    private int chapterNumber;
    private String title;
    private String content;
    private String url;
}
