package com.traap.traapapp.models.otherModels.points;

import lombok.Getter;
import lombok.Setter;

public class PointScoreModel
{
    @Getter @Setter
    private String score;

    @Getter @Setter
    private Integer maxHeight;

    public PointScoreModel()
    {
    }

    public PointScoreModel(String score, Integer maxHeight)
    {
        this.score = score;
        this.maxHeight = maxHeight;
    }
}
