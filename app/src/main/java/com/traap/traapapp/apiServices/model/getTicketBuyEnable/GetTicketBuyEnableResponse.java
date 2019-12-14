package com.traap.traapapp.apiServices.model.getTicketBuyEnable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;
import retrofit2.http.GET;

public class GetTicketBuyEnableResponse
{
    @Getter @Setter @Expose
    @SerializedName("match_id")
    private int matchId;
}
