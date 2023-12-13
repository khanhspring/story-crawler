package com.storyscawler.infrastructure.model.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SourceCode {
    Metruyencv("metruyencv.com"),
    Truyenfull("truyenfull.vn"),
    Trumtruyen("trumtruyen.vn");

    private final String code;
}
