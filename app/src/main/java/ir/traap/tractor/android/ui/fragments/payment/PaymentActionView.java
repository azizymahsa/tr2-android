package ir.traap.tractor.android.ui.fragments.payment;

import ir.traap.tractor.android.apiServices.model.buyPackage.response.PackageBuyResponse;
import ir.traap.tractor.android.apiServices.model.mobileCharge.response.MobileChargeResponse;

public interface PaymentActionView
{
    void onPaymentGDSFlight();
    void onPaymentGDSHotel();
    void onPaymentGDSBus();

    void onPaymentChargeSimCard(MobileChargeResponse data, String mobile);
    void onErrorCharge(String message);

    void onPaymentPackSimCard(PackageBuyResponse response, String mobile);
    void onErrorPackSimcard(String message);

    void onPaymentTransferMoney();

    void onPaymentWithoutCard();

    void onPaymentBill();

    void onPaymentTicket();
}
