package com.storyscawler.core.action;

import com.storyscawler.core.ElementExtractor;

import java.util.List;
import java.util.Map;

public interface ActionExecutor {

    List<Map<String, String>> extractData(ElementExtractor elementExtractor);

    Map<String, String> extractSingleData(ElementExtractor elementExtractor);
}
