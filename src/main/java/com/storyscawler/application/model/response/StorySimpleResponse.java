package com.storyscawler.application.model.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StorySimpleResponse {
    private String slug;
    private String title;
}
