
package com.traap.traapapp.apiServices.model.cup;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.traap.traapapp.apiServices.model.topPlayers.Result;

public class CupResponse {

    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("results")
    @Expose
    private List<Datum> results = null;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<Datum> getResults()
    {
        return results;
    }

    public void setResults(List<Datum> results)
    {
        this.results = results;
    }
}
