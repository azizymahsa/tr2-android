package ir.traap.tractor.android.ui.activities.card.add;


import ir.traap.tractor.android.ui.base.BasePresenter;

/**
 * Created by Javad.Abadi on 7/16/2018.
 */
public interface AddCardPresenter extends BasePresenter
{
    void setCardDetail(String cardNumber, String etFullName, boolean isFavorite);
}
