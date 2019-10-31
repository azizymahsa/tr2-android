package ir.trap.tractor.android.apiServices.model.getAllMenuServices.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import ir.trap.tractor.android.apiServices.model.getMenu.response.GetMenuItemResponse;
import lombok.Getter;
import lombok.Setter;

public class GetAllMenuResponse
{
    @SerializedName("results")
    @Expose @Getter @Setter
    private ArrayList<GetMenuItemResponse> results;
}
