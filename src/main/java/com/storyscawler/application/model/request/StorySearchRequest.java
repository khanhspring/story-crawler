package com.storyscawler.application.model.request;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StorySearchRequest {
    private String keyword;
    private Boolean completed;
    private List<Long> genreIds;
    private Long authorId;
}
