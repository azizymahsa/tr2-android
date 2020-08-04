package com.traap.traapapp.apiServices.model.buyChargeCard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Javad.Abadi on 12/10/2019.
 */
public class BuyChargeCardRequest
{
    @SerializedName("mobile")
    @Expose @Setter @Getter
    private String mobile;

    @SerializedName("OperatorType")
    @Expose @Setter @Getter
    private Integer operatorType;

    @SerializedName("sim_card_type")
    @Expose @Setter @Getter
    private Integer simCardType;

    @SerializedName("amount")
    @Expose @Setter @Getter
    private Integer amount;

    @SerializedName("pin2")
    @Expose @Setter @Getter
    private String pin2;

    @SerializedName("card_id")
    @Expose @Setter @Getter
    private int cardId;

    @SerializedName("cvv2")
    @Expose @Setter @Getter
    private String cvv2;

    @SerializedName("exp_year")
    @Expose @Setter @Getter
    private String expYear;

    @SerializedName("exp_month")
    @Expose @Setter @Getter
    private String expMonth;

    @SerializedName("type_charge")
    @Expose @Setter @Getter
    private Integer typeCharge;
}
