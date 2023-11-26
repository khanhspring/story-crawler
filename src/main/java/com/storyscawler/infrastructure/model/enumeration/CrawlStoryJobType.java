package com.storyscawler.infrastructure.model.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CrawlStoryJobType {
    New,
    Update,
    Reset,
    Cancelled
}
