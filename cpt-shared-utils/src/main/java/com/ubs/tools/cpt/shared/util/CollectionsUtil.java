package com.ubs.tools.cpt.shared.util;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public final class CollectionsUtil {
    public static <T> List<T> listWithoutNulls(T[] args) {
        if (arrayEmpty(args)) {
            return Collections.emptyList();
        }

        return Stream.of(args).filter(Objects::nonNull).toList();
    }

    public static <T> boolean arrayEmpty(T[] args) {
        return args == null || args.length == 0;
    }
}
