package com.storyscawler.source.truyenfull.processor.helper;

import com.storyscawler.core.ElementExtractor;
import com.storyscawler.core.jsoup.action.JsoupActionContext;
import com.storyscawler.core.jsoup.action.JsoupActionExecutor;
import com.storyscawler.core.model.PageResult;
import com.storyscawler.core.selector.ElementLocator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class TruyenfullCrawlStoryProcessor {

    public PageResult<List<Map<String, String>>> process(JsoupActionContext context, String url) {
        var executor = new JsoupActionExecutor(context);
        executor.access(url);

        var storiesExtractor = ElementExtractor
                .elements(ElementLocator.cssSelector("h3.truyen-title a"))
                .text("story_title")
                .attr("story_url", "href");
        var stories = executor.extractData(storiesExtractor);

        var btnNextExtractor = ElementExtractor
                .elements(ElementLocator.cssSelector(".glyphicon-menu-right"))
                .ignoreException()
                .attr("className", "class");
        var btnNextData = executor.extractSingleData(btnNextExtractor);
        boolean hasNext = false;
        if (!btnNextData.isEmpty() && btnNextData.containsKey("className")) {
            hasNext = true;
        }

        return PageResult.<List<Map<String, String>>>builder()
                .content(stories)
                .hasNext(hasNext)
                .build();
    }
}
