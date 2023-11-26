package com.storyscawler.core.selector;

public interface ElementLocator {

    static ElementLocator id(String id) {
        return ElementLocatorById.of(id);
    }

    static ElementLocator xpath(String xpath) {
        return ElementLocatorByXpath.of(xpath);
    }

    static ElementLocator cssSelector(String cssSelector) {
        return ElementLocatorByCssSelector.of(cssSelector);
    }

    static ElementLocator className(String className) {
        return ElementLocatorByClassName.of(className);
    }

    static ElementLocator tagName(String tagName) {
        return ElementLocatorByTagName.of(tagName);
    }
}
