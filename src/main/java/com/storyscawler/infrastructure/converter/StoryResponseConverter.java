package com.storyscawler.infrastructure.converter;

import com.storyscawler.application.model.response.StoryResponse;
import com.storyscawler.infrastructure.model.entity.JpaStory;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class StoryResponseConverter implements Converter<JpaStory, StoryResponse> {

    private final AuthorResponseConverter authorResponseConverter;
    private final GenreResponseConverter genreResponseConverter;
    private final TagResponseConverter tagResponseConverter;

    @Nullable
    @Override
    public StoryResponse convert(@Nullable JpaStory source) {
        if (Objects.isNull(source)) {
            return null;
        }
        return StoryResponse.builder()
                .title(source.getTitle())
                .slug(source.getSlug())
                .summary(source.getSummary())
                .thumbnailUrl(source.getThumbnailUrl())
                .totalView(source.getTotalView())
                .rating(source.getRating())
                .totalRating(source.getTotalRating())
                .totalChapter(source.getTotalChapter())
                .completed(source.isCompleted())
                .author(authorResponseConverter.convert(source.getAuthor()))
                .genres(genreResponseConverter.convert(source.getGenres()))
                .tags(tagResponseConverter.convert(source.getTags()))
                .build();
    }
}
