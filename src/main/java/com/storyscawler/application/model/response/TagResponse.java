package com.storyscawler.application.model.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagResponse {
    private String code;
    private String title;
}
