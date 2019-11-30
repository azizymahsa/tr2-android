package com.traap.traapapp.apiServices.model.getMyBill;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by MahtabAzizi on 10/21/2019.
 */
public class GetMyBillResponse
{
    @SerializedName("count")
    @Expose
    private Integer count;

    @SerializedName("next")
    @Expose
    private Object next;

    @SerializedName("previous")
    @Expose
    private Object previous;

    @SerializedName("results")
    @Expose
    private List<Bills> results = null;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Object getNext() {
        return next;
    }

    public void setNext(Object next) {
        this.next = next;
    }

    public Object getPrevious() {
        return previous;
    }

    public void setPrevious(Object previous) {
        this.previous = previous;
    }

    public List<Bills> getResults() {
        return results;
    }

    public void setResults(List<Bills> results) {
        this.results = results;
    }
}
