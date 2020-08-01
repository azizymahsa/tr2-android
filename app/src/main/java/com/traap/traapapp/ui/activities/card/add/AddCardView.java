package com.traap.traapapp.ui.activities.card.add;


import com.traap.traapapp.ui.base.BaseView;

/**
 * Created by Javad.Abadi on 7/16/2018.
 */
public interface AddCardView extends BaseView
{
    void onSuccess(String message);
    void onError(String message);
}
