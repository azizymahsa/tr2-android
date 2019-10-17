package ir.trap.tractor.android.ui.fragments.payment;

public interface PaymentActionView
{
    void onPaymentGDSFlight();
    void onPaymentGDSHotel();
    void onPaymentGDSBus();

    void onPaymentChargeSimCard();
    void onPaymentPackSimCard();

    void onPaymentTransferMoney();

    void onPaymentWithoutCard();

    void onPaymentBillTicket();
}
