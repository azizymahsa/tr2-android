package com.traap.traapapp.apiServices.model.getLast5PastMatch.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class Last5PastMatchResponse
{
    @SerializedName("results")
    @Expose @Getter @Setter
    private List<Last5PastMatchItem> lastMatchList;
}
