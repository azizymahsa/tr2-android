package com.traap.traapapp.apiServices.model.getTransaction;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by MahtabAzizi on 1/21/2020.
 */
public class Detail
{
    @SerializedName("mobile")
    @Expose
    @Getter
    @Setter
    private String mobile;

    @SerializedName("destination_card")
    @Expose @Getter @Setter
    private String destinationCard;

    @SerializedName("card_holder")
    @Expose @Getter @Setter
    private String cardHolder;
}
