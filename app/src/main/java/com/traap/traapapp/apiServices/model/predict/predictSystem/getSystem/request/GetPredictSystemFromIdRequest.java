package com.traap.traapapp.apiServices.model.predict.predictSystem.getSystem.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class GetPredictSystemFromIdRequest
{
    @SerializedName("formation_id")
    @Expose @Getter @Setter
    private int formationId;

    public GetPredictSystemFromIdRequest(int formationId)
    {
        this.formationId = formationId;
    }
}
