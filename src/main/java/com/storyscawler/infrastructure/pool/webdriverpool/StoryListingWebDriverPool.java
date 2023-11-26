package com.storyscawler.infrastructure.pool.webdriverpool;

import com.storyscawler.infrastructure.property.WebDriverPoolProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StoryListingWebDriverPool extends WebDriverGenericPool {

    public StoryListingWebDriverPool(WebDriverPoolProperties properties) {
        super(
                properties.getStoryListing().getMaxSize(),
                properties.getStoryListing().isHeadless(),
                properties.getStoryListing().getRemoteUrl()
        );
    }

}
