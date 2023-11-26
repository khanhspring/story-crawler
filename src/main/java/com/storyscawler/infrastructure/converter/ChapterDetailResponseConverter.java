package com.storyscawler.infrastructure.converter;

import com.storyscawler.application.model.response.ChapterDetailResponse;
import com.storyscawler.application.model.response.StorySimpleResponse;
import com.storyscawler.infrastructure.model.entity.JpaChapter;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ChapterDetailResponseConverter {

    private final ChapterSimpleResponseConverter chapterSimpleResponseConverter;

    @Nullable
    public ChapterDetailResponse convert(@Nullable JpaChapter source, @Nullable JpaChapter previous, @Nullable JpaChapter next) {
        if (Objects.isNull(source)) {
            return null;
        }
        var story = StorySimpleResponse.builder()
                .slug(source.getStory().getSlug())
                .title(source.getStory().getTitle())
                .build();

        return ChapterDetailResponse.builder()
                .title(source.getTitle())
                .index(source.getIndex())
                .content(source.getContent())
                .createdDate(source.getCreatedDate())
                .story(story)
                .previous(chapterSimpleResponseConverter.convert(previous))
                .next(chapterSimpleResponseConverter.convert(next))
                .build();
    }
}
