package com.storyscawler.core.jsoup;

import com.storyscawler.core.extractor.*;
import org.jsoup.nodes.Element;

public class JsoupExtractor extends ElementExtractor<Element> {

    public String extract(Element element, Extractor extractor) {
        if (extractor instanceof TextExtractor) {
            return element.text();
        }
        if (extractor instanceof HtmlExtractor) {
            return element.html();
        }
        if (extractor instanceof AttributeExtractor attributeExtractor) {
            return element.attr(attributeExtractor.getAttributeName());
        }
        throw new UnsupportedOperationException("This extractor type is not supported");
    }

}
