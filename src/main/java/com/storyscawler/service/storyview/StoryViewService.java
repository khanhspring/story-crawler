package com.storyscawler.service.storyview;

import java.time.LocalDate;

public interface StoryViewService {
    void increaseViews(Long storyId);
    void initViews(Long storyId, int value, LocalDate statsDate);
}
