package ir.trap.tractor.android.ui.fragments.favoriteCard;

import android.view.View;

import ir.trap.tractor.android.apiServices.model.card.Result;
import ir.trap.tractor.android.ui.base.GoToActivity;

public interface FavoriteCardActionView
{
    void onSlideRight();

    void onSlideLeft();

    void onShowEditDialog(Result result, int Position);

    void onShowChangePasswordDialog(Result result, int Position);

    void onShowConfirmDeleteDialog(Result result, int Position);

    void onEditCard(Result cardDetail, int position);

    void onDeleteCard(Integer cardId, int position);

    void onChangePasswordCard(Integer cardId, String oldPin2, String newPin2);

    void onForgotPasswordCard(Integer cardId);

    void onShareCard(View view);

    void startActivity(GoToActivity activity);
}
