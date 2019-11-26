package ir.traap.tractor.android.apiServices.model.categoryByIdVideo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import ir.traap.tractor.android.apiServices.model.mainVideos.Category;

/**
 * Created by MahtabAzizi on 11/25/2019.
 */
public class CategoryByIdVideosResponse
{

    @SerializedName("results")
    @Expose
    private ArrayList<Category> results = null;

    public ArrayList<Category> getResults() {
        return results;
    }

    public void setResults(ArrayList<Category> results) {
        this.results = results;
    }

}
