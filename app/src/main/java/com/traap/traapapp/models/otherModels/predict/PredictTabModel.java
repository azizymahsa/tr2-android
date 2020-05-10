package com.traap.traapapp.models.otherModels.predict;

import lombok.Getter;
import lombok.Setter;

public class PredictTabModel
{
    @Getter @Setter
    private int id;

    @Getter @Setter
    private String title;

    public PredictTabModel()
    {
    }

    public PredictTabModel(int id, String title)
    {
        this.id = id;
        this.title = title;
    }
}
