package com.storyscawler.core.jsoup.action;

import com.storyscawler.core.ElementExtractor;
import com.storyscawler.core.action.ActionExecutor;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class JsoupActionExecutor implements ActionExecutor {

    private final JsoupAccessPageAction jsoupAccessPageAction = new JsoupAccessPageAction();
    private final JsoupExtractDataAction jsoupExtractDataAction = new JsoupExtractDataAction();

    private final JsoupActionContext context;

    public JsoupActionExecutor(JsoupActionContext context) {
        this.context = context;
    }

    public List<Map<String, String>> extractData(ElementExtractor elementExtractor) {
        return jsoupExtractDataAction.execute(context, elementExtractor);
    }

    public Map<String, String> extractSingleData(ElementExtractor elementExtractor) {
        var results = jsoupExtractDataAction.execute(context, elementExtractor);
        if (!ObjectUtils.isEmpty(results)) {
            return results.get(0);
        }
        return Collections.emptyMap();
    }

    public void access(String url) {
        jsoupAccessPageAction.execute(context, url);
    }
}
