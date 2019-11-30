package com.traap.traapapp.apiServices.model.tourism.flight.sendMessage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class FlightSegments
{
    @SerializedName("ArrivalDateTime")
    @Expose
    @Getter @Setter
    private String ArrivalDateTime;

    @SerializedName("DepartureDateTime")
    @Expose
    @Getter @Setter
    private String DepartureDateTime;

    @SerializedName("DestinationAirport")
    @Expose
    @Getter @Setter
    private String DestinationAirport;

    @SerializedName("FlightNumber")
    @Expose
    @Getter @Setter
    private String FlightNumber;

    @SerializedName("JourneyDuration")
    @Expose
    @Getter @Setter
    private String JourneyDuration;

    @SerializedName("MarketingAirline")
    @Expose
    @Getter @Setter
    private String MarketingAirline;

    @SerializedName("OperatingAirline")
    @Expose
    @Getter @Setter
    private String OperatingAirline;

    @SerializedName("OriginAirport")
    @Expose
    @Getter @Setter
    private String OriginAirport;

    @SerializedName("PlaneType")
    @Expose
    @Getter @Setter
    private String PlaneType;

}
