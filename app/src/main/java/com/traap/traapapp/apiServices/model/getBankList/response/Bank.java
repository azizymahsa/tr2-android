
package com.traap.traapapp.apiServices.model.getBankList.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class Bank
{

    @SerializedName("id")
    @Expose @Getter @Setter
    private int id;

    @SerializedName("card_image_back")
    @Expose @Getter @Setter
    private String imageCardBack;

    @SerializedName("card_image")
    @Expose @Getter @Setter
    private String imageCard;

    @SerializedName("bin")
    @Expose @Getter @Setter
    private String bankBin;

    @SerializedName("name")
    @Expose @Getter @Setter
    private String bankName;

    @SerializedName("color_text")
    @Expose @Getter @Setter
    private String colorText;

    @SerializedName("order_item")
    @Expose @Getter @Setter
    private int orderItem;

    @SerializedName("color_number")
    @Expose @Getter @Setter
    private String colorNumber;
}
