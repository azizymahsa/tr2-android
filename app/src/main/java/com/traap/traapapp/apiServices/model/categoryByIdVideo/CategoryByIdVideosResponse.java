package com.traap.traapapp.apiServices.model.categoryByIdVideo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import com.traap.traapapp.apiServices.model.mainVideos.Category;

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
