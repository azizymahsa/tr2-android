package com.traap.traapapp.apiServices.model.lottery;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class GetLotteryWinnerListResponse
{
    @SerializedName("id")
    @Expose @Getter @Setter
    private Integer id;

    @SerializedName("image_name")
    @Expose @Getter @Setter
    private String imageLink;

    @SerializedName("is_final")
    @Expose @Getter @Setter
    private Boolean isFinal;

    @SerializedName("winners")
    @Expose @Getter @Setter
    private List<Winner> winnerList;
}
