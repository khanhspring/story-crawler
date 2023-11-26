package com.storyscawler.application.model.request;


import com.storyscawler.infrastructure.model.enumeration.TopStoryType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TopStorySearchRequest {
    private TopStoryType type = TopStoryType.AllTime;
}
