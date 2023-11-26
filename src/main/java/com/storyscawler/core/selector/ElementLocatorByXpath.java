package com.storyscawler.core.selector;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class ElementLocatorByXpath implements ElementLocator {
    private String xpathExpression;

    @Override
    public String toString() {
        return String.format("by xpath [%s]", xpathExpression);
    }
}
