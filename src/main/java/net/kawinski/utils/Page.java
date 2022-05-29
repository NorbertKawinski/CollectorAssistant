package net.kawinski.utils;

import lombok.Value;

@Value
public class Page {
    public static final Page EMPTY = new Page(1, 0);

    int number;
    int size;

    public int firstElementIndex() {
        return ((number - 1) * size);
    }

    public int firstElementNumber() {
        return firstElementIndex() + 1;
    }
}
