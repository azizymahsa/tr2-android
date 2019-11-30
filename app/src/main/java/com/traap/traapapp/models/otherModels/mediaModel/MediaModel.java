package com.traap.traapapp.models.otherModels.mediaModel;

import com.google.gson.annotations.Expose;

import lombok.Getter;
import lombok.Setter;

public class MediaModel
{
    @Getter @Setter @Expose
    private Integer id;

    @Getter @Setter @Expose
    private String title;

    @Getter @Setter @Expose
    private Integer iconDrawable;

    @Getter @Setter @Expose
    private Integer iconDrawableSelected;
}
