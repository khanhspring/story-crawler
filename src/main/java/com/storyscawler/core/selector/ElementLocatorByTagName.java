package com.storyscawler.core.selector;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class ElementLocatorByTagName implements ElementLocator {
    private String tagName;

    @Override
    public String toString() {
        return String.format("by tag name [%s]", tagName);
    }
}
