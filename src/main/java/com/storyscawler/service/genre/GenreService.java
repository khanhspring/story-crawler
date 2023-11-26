package com.storyscawler.service.genre;

import com.storyscawler.infrastructure.model.entity.JpaGenre;

public interface GenreService {
    JpaGenre findByTitleOrElseCreate(String title);
}
