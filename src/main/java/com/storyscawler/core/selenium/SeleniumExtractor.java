package com.storyscawler.core.selenium;

import com.storyscawler.core.extractor.*;
import org.openqa.selenium.WebElement;

public class SeleniumExtractor extends ElementExtractor<WebElement> {

    public String extract(WebElement webElement, Extractor extractor) {
        if (extractor instanceof TextExtractor) {
            return webElement.getText();
        }
        if (extractor instanceof HtmlExtractor) {
            return webElement.getAttribute("innerHTML");
        }
        if (extractor instanceof AttributeExtractor attributeExtractor) {
            return webElement.getAttribute(attributeExtractor.getAttributeName());
        }
        throw new UnsupportedOperationException("This extractor type is not supported");
    }
}
