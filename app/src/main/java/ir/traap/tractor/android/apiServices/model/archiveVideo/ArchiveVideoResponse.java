package ir.traap.tractor.android.apiServices.model.archiveVideo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import ir.traap.tractor.android.apiServices.model.mainVideos.Category;

/**
 * Created by MahtabAzizi on 11/27/2019.
 */
public class ArchiveVideoResponse
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
    private ArrayList<Category> results = null;

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

    public ArrayList<Category> getResults() {
        return results;
    }

    public void setResults(ArrayList<Category> results) {
        this.results = results;
    }

}