package com.storyscawler.core.processor;

import com.storyscawler.core.model.ChapterResult;
import com.storyscawler.core.model.CrawlChaptersOption;
import com.storyscawler.infrastructure.model.enumeration.SourceCode;

import java.util.function.Function;

public interface CrawlChaptersProcessor {

    void process(CrawlChaptersOption option, Function<ChapterResult, Boolean> doNext);
    boolean isSupported(SourceCode sourceCode);
}
