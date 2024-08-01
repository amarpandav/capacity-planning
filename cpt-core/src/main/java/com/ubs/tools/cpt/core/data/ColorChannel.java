package com.ubs.tools.cpt.core.data;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public final class ColorChannel {
    private final int value;

    private ColorChannel(int value) {
        if (value < 0 || value > 255) {
            throw new IllegalArgumentException("value must be between 0 and 255. Actual value: " + value);
        }

        this.value = value;
    }

    public int value() {
        return value;
    }

    public static ColorChannel of(int value) {
        return new ColorChannel(value);
    }

    public String hex() {
        return Integer.toHexString(value);
    }
}
