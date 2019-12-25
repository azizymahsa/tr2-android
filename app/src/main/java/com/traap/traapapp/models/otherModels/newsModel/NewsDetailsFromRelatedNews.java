package com.traap.traapapp.models.otherModels.newsModel;

import com.traap.traapapp.models.otherModels.mediaModel.MediaDetailsPositionIdsModel;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class NewsDetailsFromRelatedNews
{
    @Getter @Setter
    private Integer currentId;

    @Getter @Setter
    private Integer currentPosition;

    @Getter @Setter
    private List<MediaDetailsPositionIdsModel> positionIdsList;

}
