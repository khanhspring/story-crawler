package com.storyscawler.core.selenium;

import com.storyscawler.core.selector.*;
import org.openqa.selenium.By;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;

public class SeleniumElementLocatorConverter implements Converter<ElementLocator, By> {

    @NonNull
    @Override
    public By convert(@NonNull ElementLocator source) {
        if (source instanceof ElementLocatorById byId) {
            return By.id(byId.getId());
        }
        if (source instanceof ElementLocatorByClassName byClassName) {
            return By.className(byClassName.getClassName());
        }
        if (source instanceof ElementLocatorByXpath xpath) {
            return By.xpath(xpath.getXpathExpression());
        }
        if (source instanceof ElementLocatorByCssSelector cssSelector) {
            return By.cssSelector(cssSelector.getCssSelector());
        }
        if (source instanceof ElementLocatorByTagName tagSelector) {
            return By.tagName(tagSelector.getTagName());
        }
        throw new UnsupportedOperationException("This element locator is not supported");
    }
}
