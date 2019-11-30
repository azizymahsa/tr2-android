package com.traap.traapapp.apiServices.model.getAllMenuServices.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import com.traap.traapapp.apiServices.model.getMenu.response.GetMenuItemResponse;
import lombok.Getter;
import lombok.Setter;

public class GetAllMenuResponse
{
    @SerializedName("results")
    @Expose @Getter @Setter
    private ArrayList<GetMenuItemResponse> results;
}
