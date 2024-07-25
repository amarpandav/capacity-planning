package com.ubs.tools.cpt.shared.sql;

public sealed interface Orderable extends SqlFragment permits AliasClause, FieldSelector, SelectExpression, OrderableWrapper {
    default OrderableWrapper order(SortOrder sortOrder, NullsOrder nullsOrder) {
        if (this instanceof OrderableWrapper self) {
            if (self.sortOrder() == sortOrder && self.nullsOrder() == nullsOrder) {
                return self;
            }

            return new OrderableWrapper(self.orderable(), sortOrder, nullsOrder);
        }

        return new OrderableWrapper(this, sortOrder, nullsOrder);
    }

    default OrderableWrapper asc(NullsOrder nullsOrder) {
        return order(SortOrder.ASC, nullsOrder);
    }

    default OrderableWrapper desc(NullsOrder nullsOrder) {
        return order(SortOrder.DESC, nullsOrder);
    }

    default OrderableWrapper asc() {
        return asc(NullsOrder.NULLS_LAST);
    }
    default OrderableWrapper desc() {
        return desc(NullsOrder.NULLS_LAST);
    }
}
