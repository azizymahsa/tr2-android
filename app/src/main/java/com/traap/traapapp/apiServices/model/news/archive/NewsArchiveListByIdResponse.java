package com.traap.traapapp.apiServices.model.news.archive;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import com.traap.traapapp.apiServices.model.news.main.News;
import lombok.Getter;
import lombok.Setter;

public class NewsArchiveListByIdResponse
{

    @Expose @Getter @Setter
    @SerializedName("results")
    private ArrayList<News> newsArchiveListById;

    @Expose @Getter @Setter
    @SerializedName("previous")
    private String previous;

    @Expose @Getter @Setter
    @SerializedName("next")
    private String next;

    @Expose @Getter @Setter
    @SerializedName("count")
    private int count;
}
