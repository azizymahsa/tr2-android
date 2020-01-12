package com.traap.traapapp.apiServices.model.media.category;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import javax.annotation.Nullable;

import lombok.Getter;
import lombok.Setter;

public class MediaArchiveCategoryResponse
{
    @Expose @Getter @Setter
    @SerializedName("results")
    private ArrayList<TypeCategory> typeCategoryList;

    @Expose @Getter @Setter
    @Nullable
    @SerializedName("previous")
    private Object previous = null;

    @Expose @Getter @Setter
    @Nullable
    @SerializedName("next")
    private Object next = null;

    @Expose @Getter @Setter
    @SerializedName("count")
    private int count;
}
