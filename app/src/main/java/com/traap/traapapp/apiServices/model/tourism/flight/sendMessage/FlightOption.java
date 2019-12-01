package com.traap.traapapp.apiServices.model.tourism.flight.sendMessage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class FlightOption
{
    @SerializedName("ArrivalDateTime")
    @Expose
    @Getter @Setter
    private String ArrivalDateTime;

    @SerializedName("DepartureDateTime")
    @Expose
    @Getter @Setter
    private String DepartureDateTime;

    @SerializedName("Reference")
    @Expose
    @Getter @Setter
    private String Reference;

    @SerializedName("FlightSegments")
    @Expose
    @Getter @Setter
    private List<FlightSegments> FlightSegments = null;

}
