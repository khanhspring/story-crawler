package com.storyscawler.core.jsoup.action;

import com.storyscawler.core.action.CrawlerAction;
import com.storyscawler.infrastructure.exception.PageHasBeenRedirectedException;
import org.jsoup.Jsoup;

import java.io.IOException;

public class JsoupAccessPageAction implements CrawlerAction<JsoupActionContext, String, Void>  {

    @Override
    public Void execute(JsoupActionContext context, String url) {
        try {
            var document = Jsoup.connect(url)
                    .followRedirects(context.isFollowRedirects())
                    .get();
            if (document.connection().response().statusCode() == 301) {
                throw new PageHasBeenRedirectedException();
            }
            context.setDocument(document);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
