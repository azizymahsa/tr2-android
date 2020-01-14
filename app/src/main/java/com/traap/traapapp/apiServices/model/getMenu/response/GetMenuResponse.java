package com.traap.traapapp.apiServices.model.getMenu.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

public class GetMenuResponse
{
    @SerializedName("drawer_menu")
    @Expose @Getter @Setter
    private ArrayList<GetMenuItemResponse> drawerMenu;

    @SerializedName("football_services")
    @Expose @Getter @Setter
    private ArrayList<GetMenuItemResponse> footballServiceList;

    @SerializedName("chosen_services")
    @Expose @Getter @Setter
    private ArrayList<GetMenuItemResponse> chosenServiceList;
}
