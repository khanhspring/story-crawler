package com.storyscawler.core.selector;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class ElementLocatorByCssSelector implements ElementLocator {
    private String cssSelector;

    @Override
    public String toString() {
        return String.format("by css selector [%s]", cssSelector);
    }
}
