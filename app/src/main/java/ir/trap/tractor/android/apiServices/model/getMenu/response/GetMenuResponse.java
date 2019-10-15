package ir.trap.tractor.android.apiServices.model.getMenu.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

public class GetMenuResponse
{
    @SerializedName("football_services")
    @Expose @Getter @Setter
    private ArrayList<GetMenuItemResponse> footballServiceList;

    @SerializedName("chosen_services")
    @Expose @Getter @Setter
    private ArrayList<GetMenuItemResponse> chosenServiceList;

}
