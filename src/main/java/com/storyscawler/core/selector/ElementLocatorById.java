package com.storyscawler.core.selector;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class ElementLocatorById implements ElementLocator {
    private String id;

    @Override
    public String toString() {
        return String.format("by id [%s]", id);
    }
}
