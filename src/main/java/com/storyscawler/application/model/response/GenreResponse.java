package com.storyscawler.application.model.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GenreResponse {
    private Long id;
    private String code;
    private String title;
}
