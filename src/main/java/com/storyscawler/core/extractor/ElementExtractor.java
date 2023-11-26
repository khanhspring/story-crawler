package com.storyscawler.core.extractor;

import org.springframework.util.ObjectUtils;

import java.util.*;

public abstract class ElementExtractor<E> {

    public abstract String extract(E element, Extractor extractor);

    public List<Map<String, String>> extract(E element, List<Extractor> extractors) {
        if (Objects.isNull(element)) {
            return Collections.emptyList();
        }
        List<Map<String, String>> results = new ArrayList<>();
        Map<String, String> resultItem = extractItem(element, extractors);
        results.add(resultItem);
        return results;
    }

    public List<Map<String, String>> extract(List<E> elements, List<Extractor> extractors) {
        if (ObjectUtils.isEmpty(elements)) {
            return Collections.emptyList();
        }
        List<Map<String, String>> results = new ArrayList<>();
        for (var webElement : elements) {
            Map<String, String> resultItem = extractItem(webElement, extractors);
            results.add(resultItem);
        }
        return results;
    }
    private Map<String, String> extractItem(E element, List<Extractor> extractors) {
        Map<String, String> resultItem = new HashMap<>();
        for (var extractor : extractors) {
            var result = this.extract(element, extractor);
            resultItem.put(extractor.getKey(), result);
        }
        return resultItem;
    }
}
