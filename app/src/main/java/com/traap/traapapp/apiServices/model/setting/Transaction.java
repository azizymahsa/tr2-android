package com.traap.traapapp.apiServices.model.setting;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class Transaction
{
    @Expose @Getter @Setter
    @SerializedName("filters")
    private Filters filters;
}
