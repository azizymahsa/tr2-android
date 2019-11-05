package ir.traap.tractor.android.ui.fragments.payment;

public interface PaymentParentActionView
{
    void showPaymentParentLoading();

    void hidePaymentParentLoading();

    void onPaymentCancelAndBack();

    void startAddCardActivity();
}
