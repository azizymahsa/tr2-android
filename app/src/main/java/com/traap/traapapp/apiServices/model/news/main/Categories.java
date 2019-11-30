package com.traap.traapapp.apiServices.model.news.main;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class Categories
{
    @Expose @Getter @Setter
    @SerializedName("news")
    private List<News> news;

    @Expose @Getter @Setter
    @SerializedName("title")
    private String title;

    @Expose @Getter @Setter
    @SerializedName("id")
    private int id;
}
