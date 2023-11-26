package com.storyscawler.source.metruyencv.action;

import com.storyscawler.core.action.ActionInput;
import com.storyscawler.core.action.CrawlerAction;
import com.storyscawler.core.selenium.action.SeleniumActionContext;
import com.storyscawler.core.selenium.util.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

public class NormalizeStoryContentAction implements CrawlerAction<SeleniumActionContext, ActionInput, Void> {

    public Void execute(SeleniumActionContext context, ActionInput input) {
        var script = """
                CanvasRenderingContext2D.prototype.__oldFillText = CanvasRenderingContext2D.prototype.fillText;
                let currentPIndex = 1;
                const createP = (order, text) => {
                    if (text && text.trim().length > 0) {
                        const p = document.getElementById(`canvas-p-${currentPIndex}`);
                        if (!!p) {
                            p.textContent += ` ${text}`;
                        } else {
                            let p = document.createElement('p');
                            p.innerHTML = text;
                            p.style.order = order;
                            p.style.color = 'red';
                            p.className = `canvas-extracted-result`;
                            p.id = `canvas-p-${currentPIndex}`;
                            document.getElementById('article').append(p);
                        }
                    } else {
                        let p = document.createElement('p');
                        p.innerHTML = '<br/>';
                        p.style.order = order;
                        p.className = `canvas-extracted-result`;
                        document.getElementById('article').append(p);
                        currentPIndex++;
                    }
                }
                CanvasRenderingContext2D.prototype.fillText = function(text, x, y, z) {
                    createP(this.canvas.style.order, text);
                    this.__oldFillText(text, x, y, z);
                }
                """;
        var webDriver = context.getWebDriver();
        if (webDriver instanceof JavascriptExecutor) {
            ((JavascriptExecutor) webDriver).executeScript(script);
        } else {
            throw new IllegalStateException("This driver does not support JavaScript!");
        }
        WaitUtils.forInvisibility(webDriver, By.className("mx-auto"), 10);
        return null;
    }
}
