package com.traap.traapapp.apiServices.model.news.details.putBookmark.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class NewsBookmarkResponse
{
    @SerializedName("is_bookmarked")
    @Expose @Setter @Getter
    private Boolean isBookmarked;
}
