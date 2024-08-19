package com.ubs.cpt.infra.search;

import java.io.Serializable;
import java.util.List;

/**
 * @author Amar Pandav
 */
public interface SearchParameters extends Serializable, Cloneable {

    public List<SortBy> getSortBy();

    public void setSortBy(List<SortBy> sortEntries);

    public List<FilterBy> getFilterBy();

    public void setFilterBy(List<FilterBy> filterEntries);

    public Integer getFrom();

    public void setFrom(Integer from);

    public Integer getPageSize();

    public void setPageSize(Integer pageSize);

    public boolean isFiltering();

    public <T extends SearchParameters> T noPaging();

    public Object clone();

}
