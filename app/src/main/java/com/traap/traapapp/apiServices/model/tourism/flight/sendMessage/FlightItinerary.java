package com.traap.traapapp.apiServices.model.tourism.flight.sendMessage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class FlightItinerary
{
    @SerializedName("FlightOption")
    @Expose
    @Getter @Setter
    private FlightOption FlightOption = null;

}
