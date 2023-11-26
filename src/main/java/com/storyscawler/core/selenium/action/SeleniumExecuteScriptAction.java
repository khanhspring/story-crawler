package com.storyscawler.core.selenium.action;

import com.storyscawler.core.action.CrawlerAction;
import org.openqa.selenium.JavascriptExecutor;

public class SeleniumExecuteScriptAction implements CrawlerAction<SeleniumActionContext, String, Void> {

    public Void execute(SeleniumActionContext context, String script) {
        var webDriver = context.getWebDriver();
        if (webDriver instanceof JavascriptExecutor) {
            ((JavascriptExecutor) webDriver).executeScript(script);
        } else {
            throw new IllegalStateException("This driver does not support JavaScript!");
        }
        return null;
    }
}
