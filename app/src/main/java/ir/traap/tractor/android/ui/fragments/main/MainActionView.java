package ir.traap.tractor.android.ui.fragments.main;

import ir.traap.tractor.android.apiServices.model.matchList.MatchItem;
import ir.traap.tractor.android.enums.BarcodeType;
import ir.traap.tractor.android.ui.base.BaseView;

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

    void onBuyTicketClick(MatchItem matchBuyable);
    void onLeageClick ();

    void onPredict(MatchItem matchPredict);

    void onCash();
}
