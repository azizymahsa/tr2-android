package com.traap.traapapp.ui.fragments.payment;

public interface PaymentParentActionView
{
    void showPaymentParentLoading();

    void hidePaymentParentLoading();

    void onPaymentCancelAndBack();

    void startAddCardActivity();
}
