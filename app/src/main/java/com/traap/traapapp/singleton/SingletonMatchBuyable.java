package com.traap.traapapp.singleton;

import com.traap.traapapp.apiServices.model.matchList.MatchItem;
import com.traap.traapapp.models.otherModels.newsModel.NewsArchiveClickModel;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Javad.Abadi on 1/26/2019.
 */
public class SingletonMatchBuyable
{
    private static final SingletonMatchBuyable ourInstance = new SingletonMatchBuyable();

    public static SingletonMatchBuyable getInstance() {
        return ourInstance;
    }

    @Getter @Setter
    private ArrayList<MatchItem> matchBuyable;

}

