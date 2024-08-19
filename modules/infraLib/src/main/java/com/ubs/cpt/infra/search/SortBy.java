package com.ubs.cpt.infra.search;

import java.io.Serializable;

/**
 * Wrapper object for a sorting criteria.
 *
 * @author Amar Pandav
 */
public class SortBy implements Serializable {

    private static final long serialVersionUID = 2793582853883865180L;

    /**
     * Field-name of the attribute
     * on which sorting should happen
     */
    private String fieldName;

    /**
     * Sort ascending or descending
     */
    private SortDirection direction;

    /**
     * Default constructor
     *
     * @param fieldName of attribute on which sorting should happen
     * @param direction sorting up/down
     */
    public SortBy(String fieldName, SortDirection direction) {
        this.fieldName = fieldName;
        this.direction = direction;
    }

    /**
     * Field-name of the attribute
     * on which sorting should happen
     *
     * @return the field-name
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * Sort direction
     *
     * @return the direction
     */
    public SortDirection getDirection() {
        return direction;
    }

    /**
     * In which direction does the sort work?
     */
    public static enum SortDirection {
        /**
         * sorting up
         */
        ASCENDING,
        /**
         * sorting down
         */
        DESCENDING
    }
}
