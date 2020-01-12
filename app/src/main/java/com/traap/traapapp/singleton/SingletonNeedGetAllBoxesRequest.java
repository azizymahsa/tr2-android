package com.traap.traapapp.singleton;

import com.traap.traapapp.models.otherModels.newsModel.NewsArchiveClickModel;

import lombok.Getter;
import lombok.Setter;

/**
 * Authors:
 * Reza Nejati <reza.n.j.t.i@gmail.com>
 * Copyright © 2017
 */
public class SingletonNeedGetAllBoxesRequest {
    private static final SingletonNeedGetAllBoxesRequest ourInstance = new SingletonNeedGetAllBoxesRequest();

    public static SingletonNeedGetAllBoxesRequest getInstance() {
        return ourInstance;
    }

    @Getter
    @Setter
    public Boolean needRequest=true;
}
