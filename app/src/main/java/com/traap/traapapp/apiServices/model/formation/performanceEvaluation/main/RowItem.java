package com.traap.traapapp.apiServices.model.formation.performanceEvaluation.main;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class RowItem
{
    @SerializedName("columns")
    @Expose @Getter @Setter
    private List<Column> columnList;
}
