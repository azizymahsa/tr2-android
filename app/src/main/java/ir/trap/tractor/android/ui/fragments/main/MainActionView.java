package ir.trap.tractor.android.ui.fragments.main;

import androidx.fragment.app.Fragment;

import ir.trap.tractor.android.enums.BarcodeType;
import ir.trap.tractor.android.ui.base.BaseView;

public interface MainActionView extends BaseView
{
    void onBill();

    void onChargeSimCard();

    void onPackSimCard();
    void openBarcode(BarcodeType bill);
    void onBarcodReader();
    void onPaymentWithoutCard();

    void doTransferMoney();

    void onContact();

    void onInternetAlert();

    void showError(String message);

    void backToMainFragment();

    void openDrawer();

    void closeDrawer();

    void startAddCardActivity();

    void onBuyTicketClick();
}
