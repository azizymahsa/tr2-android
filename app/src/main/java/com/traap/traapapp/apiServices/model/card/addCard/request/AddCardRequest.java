
package com.traap.traapapp.apiServices.model.card.addCard.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class AddCardRequest
{
    @SerializedName("card_number")
    @Expose @Getter @Setter
    private String cardNumber;

    @SerializedName("full_name")
    @Expose @Getter @Setter
    private String fullName;
}
