package com.storyscawler.core.action;

public interface CrawlerAction<Context extends ActionContext, Input, Result> {

    Result execute(Context context, Input input);
}
