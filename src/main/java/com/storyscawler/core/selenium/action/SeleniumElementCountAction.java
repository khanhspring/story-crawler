package com.storyscawler.core.selenium.action;

import com.storyscawler.core.action.CrawlerAction;
import com.storyscawler.core.selector.ElementLocator;
import com.storyscawler.core.selenium.SeleniumElementLocatorConverter;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class SeleniumElementCountAction implements CrawlerAction<SeleniumActionContext, ElementLocator, Integer> {

    private final SeleniumElementLocatorConverter seleniumElementLocatorConverter = new SeleniumElementLocatorConverter();

    public Integer execute(SeleniumActionContext context, ElementLocator elementsLocator) {
        var by = seleniumElementLocatorConverter.convert(elementsLocator);
        var waitForWebElements = new WebDriverWait(context.getWebDriver(), Duration.ofSeconds(10));
        waitForWebElements.ignoreAll(List.of(NoSuchElementException.class, TimeoutException.class));
        var webElements = waitForWebElements.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
        return webElements.size();
    }
}
