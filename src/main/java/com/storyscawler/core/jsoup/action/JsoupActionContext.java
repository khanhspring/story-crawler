package com.storyscawler.core.jsoup.action;

import com.storyscawler.core.action.ActionContext;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jsoup.nodes.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JsoupActionContext implements ActionContext {
    private Document document;
    private boolean followRedirects = true;

    public static JsoupActionContext init() {
        return new JsoupActionContext();
    }

    public JsoupActionContext withFollowRedirects(boolean followRedirect) {
        this.followRedirects = followRedirect;
        return this;
    }
}
