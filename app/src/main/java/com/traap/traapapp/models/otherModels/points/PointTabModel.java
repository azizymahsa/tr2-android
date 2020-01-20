package com.traap.traapapp.models.otherModels.points;

import org.greenrobot.eventbus.Subscribe;

import lombok.Getter;
import lombok.Setter;

public class PointTabModel
{
    @Getter @Setter
    private int id;

    @Getter @Setter
    private String title;

    public PointTabModel()
    {
    }

    public PointTabModel(int id, String title)
    {
        this.id = id;
        this.title = title;
    }
}
