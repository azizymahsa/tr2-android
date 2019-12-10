package com.traap.traapapp.models.otherModels.headerModel;

import androidx.annotation.Nullable;

import lombok.Getter;
import lombok.Setter;

public class HeaderModel
{
    @Getter @Setter
    private int popularNo;

    @Getter @Setter
    private String headerName;

    @Getter @Setter
    @Nullable
    private String profileUrl = null;
}
