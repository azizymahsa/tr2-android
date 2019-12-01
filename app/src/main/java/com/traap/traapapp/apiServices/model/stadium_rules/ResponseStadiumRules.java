package com.traap.traapapp.apiServices.model.stadium_rules;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class ResponseStadiumRules
{
    @SerializedName("rules")
    @Expose
    @Getter
    @Setter
    @Nullable
    private String rules ;



}
