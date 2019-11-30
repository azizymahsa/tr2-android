package com.traap.traapapp.ui.activities.card.add;


import com.traap.traapapp.ui.base.BasePresenter;

/**
 * Created by Javad.Abadi on 7/16/2018.
 */
public interface AddCardPresenter extends BasePresenter
{
    void setCardDetail(String cardNumber, String etFullName, boolean isFavorite);
}
