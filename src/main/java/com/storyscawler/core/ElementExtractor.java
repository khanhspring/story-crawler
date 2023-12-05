package com.storyscawler.core;

import com.storyscawler.core.extractor.AttributeExtractor;
import com.storyscawler.core.extractor.Extractor;
import com.storyscawler.core.extractor.HtmlExtractor;
import com.storyscawler.core.extractor.TextExtractor;
import com.storyscawler.core.selector.ElementLocator;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ElementExtractor {

    private final List<Extractor> extractors = new ArrayList<>();
    private ElementLocator elementLocator;
    private ElementLocator elementsLocator;
    private boolean ignoreException;

    private boolean ignoreWaiting;
    private Duration timeout = Duration.ofSeconds(30);

    public static ElementExtractor element(ElementLocator elementLocator) {
        var builder = new ElementExtractor();
        builder.setElementLocator(elementLocator);
        return builder;
    }

    public static ElementExtractor elements(ElementLocator elementsLocator) {
        var builder = new ElementExtractor();
        builder.setElementsLocator(elementsLocator);
        return builder;
    }

    public ElementExtractor ignoreException() {
        this.ignoreException = true;
        return this;
    }

    public ElementExtractor ignoreWaiting() {
        this.ignoreWaiting = true;
        return this;
    }

    public ElementExtractor text(String key) {
        var textExtractor = new TextExtractor(key);
        this.extractors.add(textExtractor);
        return this;
    }

    public ElementExtractor html(String key) {
        var htmlExtractor = new HtmlExtractor(key);
        this.extractors.add(htmlExtractor);
        return this;
    }

    public ElementExtractor attr(String key, String attributeName) {
        var textExtractor = new AttributeExtractor(key, attributeName);
        this.extractors.add(textExtractor);
        return this;
    }

    public ElementExtractor timeout(Duration duration) {
        this.timeout = duration;
        return this;
    }
}
