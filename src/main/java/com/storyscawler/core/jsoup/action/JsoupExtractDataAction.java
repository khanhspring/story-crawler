package com.storyscawler.core.jsoup.action;

import com.storyscawler.core.ElementExtractor;
import com.storyscawler.core.action.CrawlerAction;
import com.storyscawler.core.jsoup.JsoupElementLocatorSelector;
import com.storyscawler.core.jsoup.JsoupExtractor;
import com.storyscawler.infrastructure.exception.ApplicationException;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class JsoupExtractDataAction implements CrawlerAction<JsoupActionContext, ElementExtractor, List<Map<String, String>>> {

    private final JsoupElementLocatorSelector jsoupElementLocatorSelector = new JsoupElementLocatorSelector();
    private final JsoupExtractor jsoupExtractor = new JsoupExtractor();

    @Override
    public List<Map<String, String>> execute(JsoupActionContext context, ElementExtractor elementExtractor) {
        var document = context.getDocument();

        if (Objects.nonNull(elementExtractor.getElementLocator())) {
            var elementOpt = jsoupElementLocatorSelector.select(document, elementExtractor.getElementLocator())
                    .stream()
                    .findFirst();

            if (elementOpt.isEmpty() && elementExtractor.isIgnoreException()) {
                return Collections.emptyList();
            }

            var element = elementOpt.orElseThrow(() -> new ApplicationException("Element " + elementExtractor.getElementLocator().toString() + " not found"));

            return jsoupExtractor.extract(element, elementExtractor.getExtractors());
        }

        var elements = jsoupElementLocatorSelector.select(document, elementExtractor.getElementsLocator());
        return jsoupExtractor.extract(elements, elementExtractor.getExtractors());
    }
}
