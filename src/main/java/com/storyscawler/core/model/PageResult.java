package com.storyscawler.core.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> {

    @Getter(AccessLevel.NONE)
    private boolean hasNext;
    private T content;

    public boolean hasNext() {
        return hasNext;
    }
}
