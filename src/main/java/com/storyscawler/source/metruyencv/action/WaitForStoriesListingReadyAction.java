package com.storyscawler.source.metruyencv.action;

import com.storyscawler.core.action.ActionInput;
import com.storyscawler.core.action.CrawlerAction;
import com.storyscawler.core.selenium.action.SeleniumActionContext;
import com.storyscawler.core.selenium.util.WaitUtils;
import org.openqa.selenium.By;

public class WaitForStoriesListingReadyAction implements CrawlerAction<SeleniumActionContext, ActionInput, Void> {

    public Void execute(SeleniumActionContext context, ActionInput input) {
        var webDriver = context.getWebDriver();
        WaitUtils.forVisibility(webDriver, By.xpath("//*[@id=\"bookGrid\"]//div[contains(@class, \"media-body\")]//a"));
        return null;
    }
}
