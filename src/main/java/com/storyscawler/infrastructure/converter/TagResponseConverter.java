package com.storyscawler.infrastructure.converter;

import com.storyscawler.application.model.response.TagResponse;
import com.storyscawler.infrastructure.model.entity.JpaTag;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Component
public class TagResponseConverter implements Converter<JpaTag, TagResponse> {

    @Nullable
    @Override
    public TagResponse convert(@Nullable JpaTag source) {
        if (Objects.isNull(source)) {
            return null;
        }
        return TagResponse.builder()
                .code(source.getCode())
                .title(source.getTitle())
                .build();
    }

    @Nullable
    public List<TagResponse> convert(@Nullable Collection<JpaTag> sources) {
        return sources.stream()
                .map(this::convert)
                .toList();
    }
}
