package com.traap.traapapp.apiServices.model.getMenuHelp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by MahtabAzizi on 11/20/2019.
 */
public class GetMenuHelpResponse
{
    @SerializedName("results")
    @Expose
    private List<ResultHelpMenu> results = null;

    public List<ResultHelpMenu> getResults() {
        return results;
    }

    public void setResults(List<ResultHelpMenu> results) {
        this.results = results;
    }
}
