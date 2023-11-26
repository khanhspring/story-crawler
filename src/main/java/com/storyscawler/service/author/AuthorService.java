package com.storyscawler.service.author;

import com.storyscawler.infrastructure.model.entity.JpaAuthor;

public interface AuthorService {
    JpaAuthor findByNameOrElseCreate(String name);
}
