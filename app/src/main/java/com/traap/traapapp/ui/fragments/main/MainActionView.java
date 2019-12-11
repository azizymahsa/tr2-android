package com.traap.traapapp.ui.fragments.main;

import java.util.ArrayList;

import com.traap.traapapp.apiServices.model.matchList.MatchItem;
import com.traap.traapapp.enums.BarcodeType;
import com.traap.traapapp.enums.MediaPosition;
import com.traap.traapapp.enums.NewsParent;
import com.traap.traapapp.models.otherModels.paymentInstance.SimChargePaymentInstance;
import com.traap.traapapp.ui.base.BaseView;

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

    void onNewsFavoriteClick(NewsParent parent, MediaPosition mediaPosition);

    void onMainVideoClick();

    void openPastResultFragment(String teamId, String imageLogo, String logoTitle);

    void openChargePaymentFragment(String urlPayment, int icon_payment_ticket, String title, String priceFormat, SimChargePaymentInstance paymentInstance,String mobile);
}
