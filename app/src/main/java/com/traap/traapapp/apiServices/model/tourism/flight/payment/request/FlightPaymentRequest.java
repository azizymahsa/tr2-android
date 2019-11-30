
package com.traap.traapapp.apiServices.model.tourism.flight.payment.request;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import com.traap.traapapp.apiServices.model.tourism.flight.sendMessage.BookedReservation;
import lombok.Getter;
import lombok.Setter;
//import ir.traap.tractor.android.apiServices.model.tourism.flight.sendMessage.BookedReservation;

public class  FlightPaymentRequest
{
    @SerializedName("Pnr")
    @Expose
    @Getter @Setter
    private String pnr;

    @SerializedName("IsSuccess")
    @Expose
    @Getter @Setter
    private Boolean isSuccess;

    @SerializedName("Pin2")
    @Expose
    @Getter @Setter
    private String password;

    @SerializedName("Cvv2")
    @Expose
    @Nullable
    @Getter @Setter
    private String cvv2;

    @SerializedName("ExpDate")
    @Expose
    @Getter @Setter
    private String expDate;

    @SerializedName("Pan")
    @Expose
    @Getter @Setter
    private String pan;

    @SerializedName("UserId")
    @Expose
    @Getter @Setter
    private String userId;

    @SerializedName("OfferId")
    @Expose
    @Getter @Setter
    private String offerId;

    @SerializedName("BookedReservation")
    @Expose
    @Getter @Setter
    private BookedReservation bookedReservation ;
}
