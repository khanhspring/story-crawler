package com.storyscawler.core.selenium.action;

import com.storyscawler.core.ElementExtractor;
import com.storyscawler.core.action.ActionExecutor;
import com.storyscawler.core.action.ActionInput;
import com.storyscawler.core.action.CrawlerAction;
import com.storyscawler.core.selector.ElementLocator;
import org.springframework.util.ObjectUtils;

import java.util.*;

public class SeleniumActionExecutor implements ActionExecutor {

    private final SeleniumAccessPageAction seleniumAccessPageAction = new SeleniumAccessPageAction();
    private final SeleniumExtractDataAction seleniumExtractDataAction = new SeleniumExtractDataAction();
    private final SeleniumExecuteScriptAction seleniumExecuteScriptAction = new SeleniumExecuteScriptAction();
    private final SeleniumElementCountAction seleniumElementCountAction = new SeleniumElementCountAction();
    private final Map<String, CrawlerAction<SeleniumActionContext, ActionInput, Void>> addonVoidActions = new HashMap<>();
    private final Map<String, CrawlerAction<SeleniumActionContext, ActionInput, Map<String, String>>> addonActions = new HashMap<>();

    private final SeleniumActionContext context;

    public SeleniumActionExecutor(SeleniumActionContext context) {
        this.context = context;
    }

    public void executeScript(String script) {
        seleniumExecuteScriptAction.execute(context, script);
    }

    public List<Map<String, String>> extractData(ElementExtractor elementExtractor) {
        return seleniumExtractDataAction.execute(context, elementExtractor);
    }

    public Map<String, String> extractSingleData(ElementExtractor elementExtractor) {
        var results = seleniumExtractDataAction.execute(context, elementExtractor);
        if (!ObjectUtils.isEmpty(results)) {
            return results.get(0);
        }
        return Collections.emptyMap();
    }

    public void access(String url) {
        seleniumAccessPageAction.execute(context, url);
    }

    public Integer count(ElementLocator elementsLocator) {
        return seleniumElementCountAction.execute(context, elementsLocator);
    }

    public void executeVoid(String name) {
        executeVoid(name, null);
    }

    public void executeVoid(String name, ActionInput input) {
        var action = addonVoidActions.get(name);
        if (Objects.isNull(action)) {
            throw new IllegalArgumentException("Action not found");
        }
        action.execute(context, input);
    }

    public Map<String, String> execute(String name, ActionInput input) {
        var action = addonActions.get(name);
        if (Objects.isNull(action)) {
            throw new IllegalArgumentException("Action not found");
        }
        return action.execute(context, input);
    }

    public void addVoidAction(String name, CrawlerAction<SeleniumActionContext, ActionInput, Void> action) {
        addonVoidActions.put(name, action);
    }

    public void addAction(String name, CrawlerAction<SeleniumActionContext, ActionInput, Map<String, String>> action) {
        addonActions.put(name, action);
    }
}
