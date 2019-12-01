package com.traap.traapapp.singleton;

import com.traap.traapapp.models.otherModels.newsModel.NewsArchiveClickModel;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Javad.Abadi on 1/26/2019.
 */
public class SingletonNewsArchiveClick
{
    private static final SingletonNewsArchiveClick ourInstance = new SingletonNewsArchiveClick();

    public static SingletonNewsArchiveClick getInstance() {
        return ourInstance;
    }

    @Getter @Setter
    private NewsArchiveClickModel newsArchiveClickModel;

}

