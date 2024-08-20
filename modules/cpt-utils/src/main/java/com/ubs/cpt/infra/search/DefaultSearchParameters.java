package com.ubs.cpt.infra.search;

import com.ubs.cpt.domain.EntityId;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Base class for all specific SearchParameters,
 * supports filtering, paging and sorting information.
 *
 * @author Amar Pandav
 */
public class DefaultSearchParameters<E> implements Serializable, Cloneable, SearchParameters {

    private static final long serialVersionUID = 7357316773725421932L;

    protected EntityId<E> entityId;

    protected List<EntityId<E>> withoutEntityIds = new ArrayList<>();

    /**
     * A list of sorting entries
     */
    private List<SortBy> sortBy = new ArrayList<SortBy>();

    /**
     * A list of filtering entries
     */
    private List<FilterBy> filterBy = new ArrayList<FilterBy>();

    /**
     * Free text to search for almost any thing
     */
    private String fullTextSearch;

    /**
     * Start paging with the index from
     */
    private int from;

    /**
     * With a pagesize of
     */
    private Integer pageSize = 10;

    public List<SortBy> getSortBy() {
        return sortBy;
    }

    public void setSortBy(List<SortBy> sortBy) {
        this.sortBy = sortBy;
    }

    public void addSortBy(SortBy sortBy) {
        this.sortBy.add(sortBy);
    }

    public List<FilterBy> getFilterBy() {
        return filterBy;
    }

    public void setFilterBy(List<FilterBy> filterBy) {
        this.filterBy = filterBy;
    }

    public void addFilterBy(FilterBy filterBy) {
        this.filterBy.add(filterBy);
    }

    public Integer getFrom() {
        return from;
    }

    public void setFrom(Integer from) {
        this.from = from;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public boolean isFiltering() {
        return false;
    }

    @Override
    public <T extends SearchParameters> T noPaging() {
        this.pageSize = null;
        return (T) this;
    }

    /**
     * Reset filters and sort entries.
     */
    public void reset() {
        this.resetFilterAndSort();
    }

    public final void resetFilterAndSort() {
        this.filterBy = new ArrayList<FilterBy>();
        this.sortBy = new ArrayList<SortBy>();
    }

    @Override
    public Object clone() {
        try {
            DefaultSearchParameters clone = (DefaultSearchParameters) super.clone();

            // If we didn't copy these lists as well, filtering and sorting would affect
            // the original search parameters instance as well - which is something that
            // we want to avoid.
            clone.filterBy = new ArrayList<FilterBy>(clone.filterBy);
            clone.sortBy = new ArrayList<SortBy>(clone.sortBy);
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new InternalError(
                    "This should not happen, since we are Cloneable - yet we couldn't clone ourselves." + e.getMessage());
        }
    }

    public EntityId<E> getEntityId() {
        return entityId;
    }

    public void setEntityId(EntityId<E> entityId) {
        this.entityId = entityId;
    }

    public List<EntityId<E>> getWithoutEntityIds() {
        return withoutEntityIds;
    }

    public void setWithoutEntityIds(List<EntityId<E>> withoutEntityIds) {
        this.withoutEntityIds = withoutEntityIds;
    }

    public String getFullTextSearch() {
        return fullTextSearch;
    }

    public void setFullTextSearch(String fullTextSearch) {
        this.fullTextSearch = fullTextSearch;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + "{" + getAttributesString() + '}';
    }

    protected String getAttributesString() {
        return "sortBy=" + sortBy +
                ", filterBy=" + filterBy +
                ", fullTextSearch=" + fullTextSearch +
                ", from=" + from +
                ", pageSize=" + pageSize;
    }
}
