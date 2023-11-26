package com.storyscawler.core.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoryResult {
    private String title;
    private String url;
    private String slug;
}
