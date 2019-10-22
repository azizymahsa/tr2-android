package ir.trap.tractor.android.ui.fragments.favoriteCard;

import ir.trap.tractor.android.apiServices.model.card.getCardList.Result;
import ir.trap.tractor.android.ui.base.GoToActivity;

public interface FavoriteCardActionView
{
    void onSlideRight();

    void onSlideLeft();

    void onShowEditDialog(Result result, int Position);

    void onShowPasswordChangeDialog(Result result, int Position);

    void showConfirmDeleteCard(Result result, int Position);

    void startActivity(GoToActivity activity);
}
