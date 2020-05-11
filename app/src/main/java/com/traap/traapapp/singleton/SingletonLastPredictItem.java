package com.traap.traapapp.singleton;

import com.traap.traapapp.apiServices.model.matchList.MatchItem;
import com.traap.traapapp.enums.PredictPosition;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Javad.Abadi on 1/26/2019.
 */
public class SingletonLastPredictItem
{
    private static final SingletonLastPredictItem ourInstance = new SingletonLastPredictItem();

    public static SingletonLastPredictItem getInstance() {
        return ourInstance;
    }

    @Getter @Setter
    private PredictPosition predictPosition;

    @Getter @Setter
    private Integer matchId;

    @Getter @Setter
    private Boolean isPredictable;

}

