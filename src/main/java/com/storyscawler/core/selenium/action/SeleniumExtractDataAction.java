package com.storyscawler.core.selenium.action;

import com.storyscawler.core.ElementExtractor;
import com.storyscawler.core.action.CrawlerAction;
import com.storyscawler.core.selenium.SeleniumElementLocatorConverter;
import com.storyscawler.core.selenium.SeleniumExtractor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SeleniumExtractDataAction implements CrawlerAction<SeleniumActionContext, ElementExtractor, List<Map<String, String>>> {

    private final SeleniumElementLocatorConverter seleniumElementLocatorConverter = new SeleniumElementLocatorConverter();
    private final SeleniumExtractor seleniumExtractor = new SeleniumExtractor();

    public List<Map<String, String>> execute(SeleniumActionContext context, ElementExtractor elementExtractor) {
        var webDriver = context.getWebDriver();
        if (Objects.nonNull(elementExtractor.getElementLocator())) {
            var by = seleniumElementLocatorConverter.convert(elementExtractor.getElementLocator());

            WebElement webElement;
            if (!elementExtractor.isIgnoreWaiting()) {
                var waitForWebElement = new WebDriverWait(webDriver, elementExtractor.getTimeout());
                if (elementExtractor.isIgnoreException()) {
                    waitForWebElement.ignoreAll(List.of(NoSuchElementException.class, TimeoutException.class));
                }
                webElement = waitForWebElement.until(ExpectedConditions.visibilityOfElementLocated(by));
            } else {
                webElement = webDriver.findElement(by);
            }
            return seleniumExtractor.extract(webElement, elementExtractor.getExtractors());
        }

        var by = seleniumElementLocatorConverter.convert(elementExtractor.getElementsLocator());

        List<WebElement> webElements;
        if (!elementExtractor.isIgnoreWaiting()) {
            var waitForWebElements = new WebDriverWait(webDriver, elementExtractor.getTimeout());
            if (elementExtractor.isIgnoreException()) {
                waitForWebElements.ignoreAll(List.of(NoSuchElementException.class, TimeoutException.class));
            }
            webElements = waitForWebElements.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
        } else {
            webElements = webDriver.findElements(by);
        }
        return seleniumExtractor.extract(webElements, elementExtractor.getExtractors());
    }
}
