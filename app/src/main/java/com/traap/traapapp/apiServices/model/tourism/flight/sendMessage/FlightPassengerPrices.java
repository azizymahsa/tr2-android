package com.traap.traapapp.apiServices.model.tourism.flight.sendMessage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class FlightPassengerPrices
{
    @SerializedName("BasePricePerPassenger")
    @Expose
    @Getter @Setter
    private Integer BasePricePerPassenger;

    @SerializedName("Code")
    @Expose
    @Getter @Setter
    private Integer Code;

    @SerializedName("FullPricePerPassenger")
    @Expose
    @Getter @Setter
    private Integer FullPricePerPassenger;

    @SerializedName("OperatorFeePerPassenger")
    @Expose
    @Getter @Setter
    private Integer OperatorFeePerPassenger;

}
