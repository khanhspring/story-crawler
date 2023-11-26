package com.storyscawler.infrastructure.model.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TopStoryType {
    Day,
    Last7Days,
    Last30Days,
    AllTime
}
