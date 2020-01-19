package com.traap.traapapp.ui.fragments.turnover;

/**
 * Authors:
 * Reza Nejati <reza.n.j.t.i@gmail.com>
 * Copyright © 2017
 */
public class ClickTurnOverEvent {
    private Boolean isFilterClick;
    private Boolean isSearchClick;
    private String search;

    public Boolean getFilterClick() {
        return isFilterClick;
    }

    public void setFilterClick(Boolean filterClick) {
        isFilterClick = filterClick;
    }

    public Boolean getSearchClick() {
        return isSearchClick;
    }

    public void setSearchClick(Boolean searchClick) {
        isSearchClick = searchClick;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
