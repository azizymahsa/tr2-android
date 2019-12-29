package com.traap.traapapp.apiServices.model.getBoughtFor;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.traap.traapapp.apiServices.model.getTicketInfo.ResultTicketInfo;

import java.util.List;

/**
 * Created by MahtabAzizi on 12/29/2019.
 */
public class GetBoughtForResponse
{
    @SerializedName("results")
    @Expose
    private List<String> results = null;

    public List<String> getResults()
    {
        return results;
    }

    public void setResults(List<String> results)
    {
        this.results = results;
    }
}
