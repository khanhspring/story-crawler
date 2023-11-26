package com.storyscawler.core.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CrawlChaptersOption {
    private String storyUrl;
    private String startUrl;
    private int startIndex;
}
