package com.storyscawler.source.metruyencv.processor.helper;

import com.storyscawler.core.ElementExtractor;
import com.storyscawler.core.selector.ElementLocator;
import com.storyscawler.core.selenium.action.SeleniumActionContext;
import com.storyscawler.core.selenium.action.SeleniumActionExecutor;
import com.storyscawler.source.metruyencv.action.WaitForStoriesListingReadyAction;
import com.storyscawler.core.model.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Component
public class MetruyencvCrawlStoryProcessor {

    public PageResult<List<Map<String, String>>> process(SeleniumActionContext context, String url) {
        var executor = new SeleniumActionExecutor(context);
        executor.addVoidAction("waitForStories", new WaitForStoriesListingReadyAction());

        executor.access(url);
        executor.executeVoid("waitForStories");

        var storiesExtractor = ElementExtractor
                .elements(ElementLocator.xpath("//*[@id=\"bookGrid\"]//div[contains(@class, \"media-body\")]//a"))
                .text("story_title")
                .attr("story_url", "href");
        var stories = executor.extractData(storiesExtractor);

        var btnNextExtractor = ElementExtractor
                .elements(ElementLocator.xpath("//*[@id=\"bookGrid\"]//ul[contains(@class, \"pagination\")]/li[last()]"))
                .attr("className", "class");
        var btnNextData = executor.extractSingleData(btnNextExtractor);
        boolean hasNext = false;
        if (Objects.nonNull(btnNextData.get("className"))
                && !btnNextData.get("className").contains("disabled")) {
            hasNext = true;
        }

        return PageResult.<List<Map<String, String>>>builder()
                .content(stories)
                .hasNext(hasNext)
                .build();
    }
}
