package com.traap.traapapp.apiServices.model.setting;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class AddValue
{
    @Expose @Getter @Setter
    @SerializedName("price")
    private int price;
}
