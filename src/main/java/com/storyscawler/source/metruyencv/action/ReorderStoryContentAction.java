package com.storyscawler.source.metruyencv.action;

import com.storyscawler.core.action.ActionInput;
import com.storyscawler.core.action.CrawlerAction;
import com.storyscawler.core.selenium.action.SeleniumActionContext;
import org.openqa.selenium.JavascriptExecutor;

public class ReorderStoryContentAction implements CrawlerAction<SeleniumActionContext, ActionInput, Void> {

    public Void execute(SeleniumActionContext context, ActionInput input) {
        var script = """
                const getOrderNumber = ($e) => (Number($e.css('order')));
                const orderByOrderCss = (arr) => arr.sort((a, b) => getOrderNumber($(a)) - getOrderNumber($(b)));
                const reverseString = (str) => ((str === '') ? '' : reverseString(str.substr(1)) + str.charAt(0));
                const canvases = $('#article').find('canvas');
                
                if (canvases.length > 0) {
                    const paragraphs = $('#article').find('p');
                    let orderedParagraphs = orderByOrderCss(paragraphs);
                    for (let i = 0; i < orderedParagraphs.length; i++) {
                        const paragraph = orderedParagraphs[i];
                        const texts = $(paragraph).find('*');
                        if (!$(paragraph).hasClass('canvas-extracted-result') && texts.length > 0) {
                            const orderedTexts = orderByOrderCss(texts);
                            
                            const normalizedText = orderedTexts.map((_, text) => {
                                const $text = $(text);
                                let textValue = $text.text();
                                if ($text.css('font-size') == '0px') return '';
                                if ($text.css('direction') == 'rtl') return reverseString(textValue);
                                const after = window.getComputedStyle(text, ':after').getPropertyValue("content");
                                if (after && after != 'none' && after.length > 1) {
                                    textValue += after.substring(1, after.length - 1)
                                }
                                return textValue;
                            }).get().join('').replace(/ | /gm, ' ');
                            $(paragraph).html(normalizedText + '<br><br>');
                        }
                    }
                    $('#article').empty();
                    $('#article').removeClass();
                    $('#article').append(orderedParagraphs);
                }
                """;
        var webDriver = context.getWebDriver();
        if (webDriver instanceof JavascriptExecutor) {
            ((JavascriptExecutor) webDriver).executeScript(script);
        } else {
            throw new IllegalStateException("This driver does not support JavaScript!");
        }
        return null;
    }
}
