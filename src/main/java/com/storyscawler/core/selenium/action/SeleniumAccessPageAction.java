package com.storyscawler.core.selenium.action;

import com.storyscawler.core.action.CrawlerAction;

public class SeleniumAccessPageAction implements CrawlerAction<SeleniumActionContext, String, Void> {

    public Void execute(SeleniumActionContext context, String pageUrl) {
        var webDriver = context.getWebDriver();
        webDriver.get(pageUrl);
        return null;
    }
}
