package com.traap.traapapp.apiServices.model.buyPackageCard.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Javad.Abadi on 1/19/2020.
 */
public class BuyPackageCardRequest
{
    @SerializedName("amount")
    @Expose @Setter @Getter
    private Integer amount;

    @SerializedName("request_id")
    @Expose @Setter @Getter
    private String requestId;

    @SerializedName("bundle_id")
    @Expose @Setter @Getter
    private String bundleId;

    @SerializedName("pin2")
    @Expose @Setter @Getter
    private String pin2;

    @SerializedName("mobile")
    @Expose @Setter @Getter
    private String mobile;

    @SerializedName("title_package")
    @Expose @Setter @Getter
    private String titlePackage;

    @SerializedName("operator_type")
    @Expose @Setter @Getter
    private String operatorType;

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

}
