package com.storyscawler.core.extractor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AttributeExtractor implements Extractor {
    private String key;
    private String attributeName;
}
