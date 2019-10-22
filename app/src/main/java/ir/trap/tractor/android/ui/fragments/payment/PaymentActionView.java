package ir.trap.tractor.android.ui.fragments.payment;

import ir.trap.tractor.android.apiServices.model.mobileCharge.response.MobileChargeResponse;

public interface PaymentActionView
{
    void onPaymentGDSFlight();
    void onPaymentGDSHotel();
    void onPaymentGDSBus();

    void onPaymentChargeSimCard(MobileChargeResponse data, String mobile);
    void onPaymentPackSimCard();

    void onPaymentTransferMoney();

    void onPaymentWithoutCard();

    void onPaymentBillTicket();
    void onErrorCharge(String message);
}
