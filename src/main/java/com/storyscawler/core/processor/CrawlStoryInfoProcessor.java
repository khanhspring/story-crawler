package com.storyscawler.core.processor;

import com.storyscawler.core.model.StoryInfoResult;
import com.storyscawler.infrastructure.model.enumeration.SourceCode;

import java.util.function.Consumer;

public interface CrawlStoryInfoProcessor {

    void process(String url, Consumer<StoryInfoResult> callback);
    boolean isSupported(SourceCode sourceCode);
}
