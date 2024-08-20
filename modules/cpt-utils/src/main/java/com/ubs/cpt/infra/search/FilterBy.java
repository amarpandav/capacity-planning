package com.ubs.cpt.infra.search;

import java.io.Serializable;

/**
 * Wrapper object for filtering criteria.
 *
 * @author Amar Pandav
 */
public class FilterBy implements Serializable {

    private static final long serialVersionUID = -375979053509613021L;
    /**
     * Fieldname (=key)
     */
    private String fieldName;

    /**
     * Filter value for the associated key
     */
    private Object value;

    /**
     * Default constructor, assigns
     *
     * @param fieldName with a key and
     * @param value     with a value for this key
     */
    public FilterBy(String fieldName, Object value) {
        this.fieldName = fieldName;
        this.value = value;
    }

    /**
     * Field name
     *
     * @return field-name
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * Value
     *
     * @return value
     */
    public Object getValue() {
        return value;
    }
}
