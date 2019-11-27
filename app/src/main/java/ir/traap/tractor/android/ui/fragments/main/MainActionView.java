package ir.traap.tractor.android.ui.fragments.main;

import java.util.ArrayList;

import ir.traap.tractor.android.apiServices.model.matchList.MatchItem;
import ir.traap.tractor.android.enums.BarcodeType;
import ir.traap.tractor.android.enums.MediaPosition;
import ir.traap.tractor.android.enums.NewsParent;
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

    void onLeageClick (ArrayList<MatchItem> matchBuyable);

    void onPredict(MatchItem matchPredict, Boolean isPredictable);

    void onCash();

    void onFootBallServiceOne();

    void onFootBallServiceTwo();

    void onFootBallServiceThree();

    void onFootBallServiceFour();

    void onFootBallServiceFive();

    void onFootBallServiceSix();

    void onNewsArchiveClick(NewsParent parent, MediaPosition mediaPosition);
}
