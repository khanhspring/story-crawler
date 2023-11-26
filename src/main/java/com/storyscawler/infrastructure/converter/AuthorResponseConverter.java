package com.storyscawler.infrastructure.converter;

import com.storyscawler.application.model.response.AuthorResponse;
import com.storyscawler.infrastructure.model.entity.JpaAuthor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class AuthorResponseConverter implements Converter<JpaAuthor, AuthorResponse> {

    @Nullable
    @Override
    public AuthorResponse convert(@Nullable JpaAuthor source) {
        if (Objects.isNull(source)) {
            return null;
        }
        return AuthorResponse.builder()
                .id(source.getId())
                .code(source.getCode())
                .fullName(source.getFullName())
                .build();
    }
}
