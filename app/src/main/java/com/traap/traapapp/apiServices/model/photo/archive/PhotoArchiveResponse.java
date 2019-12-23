package com.traap.traapapp.apiServices.model.photo.archive;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.traap.traapapp.apiServices.model.mainVideos.Category;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Javad.Abadi on 11/27/2019.
 */
public class PhotoArchiveResponse
{
    @SerializedName("count")
    @Expose @Getter @Setter
    private Integer count;

    @SerializedName("next")
    @Expose @Getter @Setter
    private Object next;

    @SerializedName("previous")
    @Expose @Getter @Setter
    private Object previous;

    @SerializedName("results")
    @Expose @Getter @Setter
    private ArrayList<Category> results = null;
}