package com.traap.traapapp.apiServices.model.spectatorInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.traap.traapapp.apiServices.model.mainVideos.Category;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by MahtabAzizi on 2/17/2020.
 */
public class GetSpectatorListResponse
{
    @SerializedName("results")
    @Expose
    @Getter
    @Setter
    private ArrayList<SpectatorInfoResponse> results ;
}
