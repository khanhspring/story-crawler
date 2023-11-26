package com.storyscawler.infrastructure.pool.executorpool;

import com.storyscawler.infrastructure.property.JsoupExecutorPoolProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StoryDetailJsoupExecutorPool extends JsoupExecutorPool {

    public StoryDetailJsoupExecutorPool(JsoupExecutorPoolProperties properties) {
        super(properties.getStoryDetail().getMaxSize());
    }
}
