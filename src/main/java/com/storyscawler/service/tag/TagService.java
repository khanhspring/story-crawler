package com.storyscawler.service.tag;

import com.storyscawler.infrastructure.model.entity.JpaTag;

public interface TagService {
    JpaTag findByTitleOrElseCreate(String title);
}
