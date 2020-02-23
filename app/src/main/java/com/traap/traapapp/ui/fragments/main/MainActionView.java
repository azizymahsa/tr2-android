package com.traap.traapapp.ui.fragments.main;

import java.util.ArrayList;

import com.traap.traapapp.apiServices.model.matchList.MatchItem;
import com.traap.traapapp.enums.BarcodeType;
import com.traap.traapapp.enums.LeagueTableParent;
import com.traap.traapapp.enums.MediaPosition;
import com.traap.traapapp.enums.SubMediaParent;
import com.traap.traapapp.models.otherModels.paymentInstance.SimChargePaymentInstance;
import com.traap.traapapp.models.otherModels.paymentInstance.SimPackPaymentInstance;
import com.traap.traapapp.ui.base.BaseView;
import com.traap.traapapp.ui.fragments.simcardCharge.OnClickContinueSelectPayment;

public interface MainActionView extends BaseView
{
    void onBill();

    void onChargeSimCard(Integer status);

    void onPackSimCard(Integer status);

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

    void onPredict(Integer matchId, Boolean isPredictable);

    void onPredictLeagueTable(Integer teamId, Integer matchId, Boolean isPredictable);

    void onCash();

    void onFootBallServiceOne();

    void onFootBallServiceTwo();

    void onFootBallServiceThree();

    void onFootBallServiceFour();

    void onFootBallServiceFive();

    void onFootBallServiceSix();

    void onNewsArchiveClick(SubMediaParent parent, MediaPosition mediaPosition);

    void onNewsFavoriteClick(SubMediaParent parent, MediaPosition mediaPosition);

    void onPhotosArchiveClick(SubMediaParent parent, MediaPosition mediaPosition);

    void onPhotosFavoriteClick(SubMediaParent parent, MediaPosition mediaPosition);

    void onVideosArchiveClick(SubMediaParent parent, MediaPosition mediaPosition);

    void onVideosFavoriteClick(SubMediaParent parent, MediaPosition mediaPosition);

    void onMainVideoClick();

    void openPastResultFragment(LeagueTableParent parent, String matchId, Boolean isPredictable, String teamId, String imageLogo, String logoTitle);

    void openChargePaymentFragment(OnClickContinueSelectPayment onClickContinueSelectPayment,String urlPayment, int imageDrawable, String title, String priceFormat, SimChargePaymentInstance paymentInstance, String mobile,int PAYMENT_STATUS );

    void openWebView(MainActionView mainView, String uRl, String gds_token);

    void openIncreaseWalletPaymentFragment(OnClickContinueSelectPayment onClickContinueSelectPayment,String urlPayment, int imageDrawable, String title, String amount, SimChargePaymentInstance paymentInstance, String mobile,int PAYMENT_STATUS );
    void openPackPaymentFragment(OnClickContinueSelectPayment onClickContinueSelectPayment,String urlPayment, int imageDrawable, String title, String amount, SimPackPaymentInstance paymentInstance, String mobile,int PAYMENT_STATUS );

    void getBuyEnable(BuyTicketAction buyTicketAction);

    void onSetPredictCompleted(Integer matchIdt, Boolean isPredictable, String message);
    void onBackToChargFragment(int PAYMENT_STATUS);

    void backToAllServicePackage(Integer backState);

    void onBackToHomeWallet(int i);

    void onBackToMatch();
}
