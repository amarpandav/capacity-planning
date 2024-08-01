package com.ubs.tools.cpt.core.data;

import java.util.stream.Stream;

public record RGB(ColorChannel r, ColorChannel g, ColorChannel b) {
    public static RGB of(int r, int g, int b) {
        return new RGB(ColorChannel.of(r), ColorChannel.of(g), ColorChannel.of(b));
    }

    public static RGB parseHexString(String hex) {
        hex = hex.trim().toUpperCase();

        if (hex.startsWith("#")) {
            hex = hex.substring(1);
        }

        if (hex.length() != 6) {
            throw new IllegalArgumentException("Hex RGB string must look like this: FF44AA. Actual: " + hex);
        }

        var parts = Stream.of(
                              hex.substring(0, 2),
                              hex.substring(2, 4),
                              hex.substring(4, 6)
                          )
                          .map(p -> Integer.parseInt(p, 16))
                          .toArray(Integer[]::new);

        return RGB.of(
            parts[0],
            parts[1],
            parts[2]
        );
    }

    public String hex() {
        return r.hex() + b.hex() + g.hex();
    }
}
