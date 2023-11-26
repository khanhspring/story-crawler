package com.storyscawler.infrastructure.model.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CrawlStoryJobStatus {
    Ready,
    InProgress,
    Completed,
    Failed,
    Cancelled
}
