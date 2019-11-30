package com.traap.traapapp.apiServices.model.tourism.flight.sendMessage;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class BookedReservation
{

    @SerializedName("ReservationId")
    @Expose
    @Getter @Setter
    private String ReservationId;

    @SerializedName("FlightPrice")
    @Expose
    @Getter @Setter
    @Nullable
    private FlightPrice FlightPrice;

    @SerializedName("Travelers")
    @Expose
    @Getter @Setter
    @Nullable
    private List<Travelers> travelers = null;

    @SerializedName("FlightPassengerPrices")
    @Expose
    @Getter @Setter
    @Nullable
    private List<FlightPassengerPrices> FlightPassengerPrices = null;

    @SerializedName("FlightItinerary")
    @Expose
    @Getter @Setter
    @Nullable
    private List<FlightItinerary> FlightItinerary = null;

}
