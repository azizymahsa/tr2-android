package com.traap.traapapp.apiServices.model.paymentMatchCard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.traap.traapapp.apiServices.model.paymentMatch.Viewers;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Javad.Abadi on 11/3/2019.
 */
public class PaymentMatchCardRequest
{
    @SerializedName("amount")
    @Expose @Getter @Setter
    private Integer amount;

    @SerializedName("pan")
    @Expose @Getter @Setter
    private String pan;

    @SerializedName("pin2")
    @Expose @Getter @Setter
    private String pin2;

    @SerializedName("viewers")
    @Expose @Getter @Setter
    private List<Viewers> viewers;

    @SerializedName("cvv2")
    @Expose @Getter @Setter
    private String cvv2;

    @SerializedName("exp_date")
    @Expose @Getter @Setter
    private String expDate;

//    @SerializedName("card_id")
//    @Expose @Getter @Setter
//    private Integer cardId;

}
