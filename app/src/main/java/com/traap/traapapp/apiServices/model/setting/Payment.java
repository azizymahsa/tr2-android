package com.traap.traapapp.apiServices.model.setting;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class Payment
{
    @Expose @Getter @Setter
    @SerializedName("wallet")
    private boolean wallet;

    @Expose @Getter @Setter
    @SerializedName("ipg")
    private boolean ipg;

    @Expose @Getter @Setter
    @SerializedName("shetab")
    private boolean shetab;
}
