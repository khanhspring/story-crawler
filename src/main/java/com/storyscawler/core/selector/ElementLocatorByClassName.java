package com.storyscawler.core.selector;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class ElementLocatorByClassName implements ElementLocator {
    private String className;

    @Override
    public String toString() {
        return String.format("by class name [%s]", className);
    }
}
