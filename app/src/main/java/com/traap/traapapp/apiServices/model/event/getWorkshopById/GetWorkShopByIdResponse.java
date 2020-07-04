
package com.traap.traapapp.apiServices.model.event.getWorkshopById;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetWorkShopByIdResponse implements Serializable
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
    @SerializedName("setting")
    @Expose
    private Object setting;
    @SerializedName("bundle")
    @Expose
    private Object bundle;
    @SerializedName("results")
    @Expose
    private List<Result> results = null;
    private final static long serialVersionUID = 2827831220637468072L;

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

    public Object getSetting() {
        return setting;
    }

    public void setSetting(Object setting) {
        this.setting = setting;
    }

    public Object getBundle() {
        return bundle;
    }

    public void setBundle(Object bundle) {
        this.bundle = bundle;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

}
