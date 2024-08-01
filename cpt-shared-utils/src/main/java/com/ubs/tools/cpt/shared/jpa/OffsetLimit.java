package com.ubs.tools.cpt.shared.jpa;

import jakarta.persistence.Query;

import java.util.OptionalInt;

public class OffsetLimit {
    private final Integer offset, limit;

    private static final OffsetLimit EMPTY = new OffsetLimit(null, null);

    private OffsetLimit(Integer offset, Integer limit) {
        this.offset = offset;
        this.limit = limit;
    }

    public OptionalInt offset() {
        return offset == null ? OptionalInt.empty() : OptionalInt.of(offset);
    }

    public OptionalInt limit() {
        return limit == null ? OptionalInt.empty() : OptionalInt.of(limit);
    }

    public static OffsetLimit of(Integer offset, Integer limit) {
        return new OffsetLimit(offset, limit);
    }

    public static OffsetLimit offset(Integer offset) {
        return of(offset, null);
    }

    public static OffsetLimit limit(Integer limit) {
        return of(null, limit);
    }

    public static OffsetLimit none() {
        return EMPTY;
    }

    public Query applyTo(Query query) {
        return query.setMaxResults(limit).setFirstResult(offset);
    }

    public static Query applyTo(Query query, OffsetLimit offsetLimit) {
        if (offsetLimit != null) {
            return offsetLimit.applyTo(query);
        }

        return query;
    }
}
