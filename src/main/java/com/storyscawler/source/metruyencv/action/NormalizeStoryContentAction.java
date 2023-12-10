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
                const createP = (order, text) => {
                    let div = document.getElementById(`canvas-div-${order}`);
                    if (!div) {
                        div = document.createElement('div');
                        div.style.order = order;
                        div.id = `canvas-div-${order}`;
                        document.getElementById('article').appendChild(div);
                                
                        let p = document.createElement('p');
                        p.style.order = order;
                        p.style.color = 'red';
                        p.className = `canvas-extracted-result`;
                        p.id = `canvas-p-${order}`;
                        div.appendChild(p);
                    }
                                
                    if (text && text.trim().length > 0) {
                        let lastP = div.lastElementChild;
                        lastP.textContent += ` ${text}`;
                    } else {
                        let pNewLine = document.createElement('p');
                        pNewLine.innerHTML = '<br/>';
                        pNewLine.style.order = order;
                        pNewLine.className = `canvas-extracted-result`;
                        div.appendChild(pNewLine);
                                
                        let p = document.createElement('p');
                        p.style.order = order;
                        p.style.color = 'red';
                        p.className = `canvas-extracted-result`;
                        p.id = `canvas-p-${order}`;
                        div.appendChild(p);
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
