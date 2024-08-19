package com.ubs.cpt.shared.sql;

public interface Aliasable extends Selectable {
    Selectable as(String alias);
}
