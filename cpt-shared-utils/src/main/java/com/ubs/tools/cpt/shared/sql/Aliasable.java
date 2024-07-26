package com.ubs.tools.cpt.shared.sql;

public interface Aliasable extends Selectable {
    Selectable as(String alias);
}
