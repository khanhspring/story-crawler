package com.storyscawler.infrastructure.model.projection;

import com.storyscawler.infrastructure.model.entity.JpaStory;

public interface JpaTopStoryProjection {
    JpaStory getStory();
    int getTotalView();
}
