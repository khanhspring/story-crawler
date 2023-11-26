package com.storyscawler.infrastructure.converter;

import com.storyscawler.application.model.response.ChapterSimpleResponse;
import com.storyscawler.infrastructure.model.entity.JpaChapter;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ChapterSimpleResponseConverter implements Converter<JpaChapter, ChapterSimpleResponse> {

    @Nullable
    @Override
    public ChapterSimpleResponse convert(@Nullable JpaChapter source) {
        if (Objects.isNull(source)) {
            return null;
        }
        return ChapterSimpleResponse.builder()
                .title(source.getTitle())
                .index(source.getIndex())
                .createdDate(source.getCreatedDate())
                .build();
    }
}
