package com.traap.traapapp.apiServices.model.predict.predictResult.getPredict.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class Chart
{
    @SerializedName("bar")
    @Expose @Getter @Setter
    private List<BarChart> barChart = null;

    @SerializedName("pie")
    @Expose @Getter @Setter
    private List<PieChart> pieChart = null;

}
