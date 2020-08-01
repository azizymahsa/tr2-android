package com.traap.traapapp.apiServices.model.card.editCard.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class EditCardRequest
{

    @SerializedName("card_number")
    @Expose @Getter @Setter
    private String cardNumber;

    @SerializedName("full_name")
    @Expose @Getter @Setter
    private String fullName;

    @SerializedName("is_favorite")
    @Expose @Getter @Setter
    private Boolean isFavorite;
}
