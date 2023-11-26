package com.storyscawler.core.jsoup;

import com.storyscawler.core.selector.*;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.lang.NonNull;

public class JsoupElementLocatorSelector {

    @NonNull
    public Elements select(Document document, ElementLocator source) {
        if (source instanceof ElementLocatorById byId) {
            return document.select(String.format("#%s", byId.getId()));
        }
        if (source instanceof ElementLocatorByClassName byClassName) {
            return document.select(String.format(".%s", byClassName.getClassName()));
        }
        if (source instanceof ElementLocatorByXpath xpath) {
            return document.selectXpath(xpath.getXpathExpression());
        }
        if (source instanceof ElementLocatorByCssSelector cssSelector) {
            return document.select(cssSelector.getCssSelector());
        }
        if (source instanceof ElementLocatorByTagName tagSelector) {
            return document.select(tagSelector.getTagName());
        }
        throw new UnsupportedOperationException("This element locator is not supported");
    }
}
