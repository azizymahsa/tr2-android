package com.traap.traapapp.apiServices.model.predict.predictSystem.getMainPredict;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class FormationItem
{
    @SerializedName("id")
    @Expose @Getter @Setter
    private int formationId;

    @SerializedName("title")
    @Expose @Getter @Setter
    private String title;
}
