package com.storyscawler.infrastructure.pool.executorpool;

import com.storyscawler.infrastructure.property.JsoupExecutorPoolProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StoryListingJsoupExecutorPool extends JsoupExecutorPool {

    public StoryListingJsoupExecutorPool(JsoupExecutorPoolProperties properties) {
        super(properties.getStoryListing().getMaxSize());
    }
}
