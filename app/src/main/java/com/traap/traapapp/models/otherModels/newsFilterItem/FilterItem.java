package com.traap.traapapp.models.otherModels.newsFilterItem;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class FilterItem
{
    @Expose @Getter @Setter
    private String title;

    @Expose @Getter @Setter
    private int id;

    @Expose @Getter @Setter
    private boolean checked;

}
