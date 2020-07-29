package com.traap.traapapp.ui.fragments.favoriteCard;

import android.view.View;

import com.traap.traapapp.apiServices.model.card.CardBankItem;
import com.traap.traapapp.ui.base.GoToActivity;

public interface FavoriteCardActionView
{
    void onSlideRight();

    void onSlideLeft();

    void onShowEditDialog(CardBankItem cardBankItem, int Position);

    void onShowChangePasswordDialog(CardBankItem cardBankItem, int Position);

    void onShowConfirmDeleteDialog(CardBankItem cardBankItem, int Position);

    void onEditCard(CardBankItem cardDetail, int position);

    void onDeleteCard(Integer cardId, int position);

    void onChangePasswordCard(Integer cardId, String oldPin2, String newPin2);

    void onForgotPasswordCard(Integer cardId);

    void onShareCard(View view);

    void startActivityForResult(GoToActivity activity);
}
