package com.storyscawler.core.processor;

import com.storyscawler.core.model.StoriesResult;
import com.storyscawler.infrastructure.model.enumeration.SourceCode;

import java.util.function.Function;

public interface CrawlStoriesProcessor {

    void process(String baseUrl, int fromPageNumber, Function<StoriesResult, Boolean> doNext);
    boolean isSupported(SourceCode sourceCode);
}
