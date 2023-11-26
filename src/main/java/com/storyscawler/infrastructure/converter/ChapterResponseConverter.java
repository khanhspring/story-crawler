package com.storyscawler.infrastructure.converter;

import com.storyscawler.application.model.response.ChapterResponse;
import com.storyscawler.application.model.response.StorySimpleResponse;
import com.storyscawler.infrastructure.model.entity.JpaChapter;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ChapterResponseConverter implements Converter<JpaChapter, ChapterResponse> {

    @Nullable
    @Override
    public ChapterResponse convert(@Nullable JpaChapter source) {
        if (Objects.isNull(source)) {
            return null;
        }
        var story = StorySimpleResponse.builder()
                .slug(source.getStory().getSlug())
                .title(source.getStory().getTitle())
                .build();
        return ChapterResponse.builder()
                .title(source.getTitle())
                .index(source.getIndex())
                .content(source.getContent())
                .createdDate(source.getCreatedDate())
                .story(story)
                .build();
    }
}
