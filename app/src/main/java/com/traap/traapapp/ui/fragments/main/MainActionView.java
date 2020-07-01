package com.traap.traapapp.ui.fragments.main;

import androidx.annotation.Nullable;

import com.traap.traapapp.apiServices.model.lottery.Winner;
import com.traap.traapapp.apiServices.model.matchList.MatchItem;
import com.traap.traapapp.enums.BarcodeType;
import com.traap.traapapp.enums.LeagueTableParent;
import com.traap.traapapp.enums.MediaPosition;
import com.traap.traapapp.enums.PredictPosition;
import com.traap.traapapp.enums.SubMediaParent;
import com.traap.traapapp.models.otherModels.paymentInstance.SimChargePaymentInstance;
import com.traap.traapapp.models.otherModels.paymentInstance.SimPackPaymentInstance;
import com.traap.traapapp.models.otherModels.qrModel.QrModel;
import com.traap.traapapp.ui.base.BaseView;
import com.traap.traapapp.ui.fragments.simcardCharge.OnClickContinueSelectPayment;

import java.util.ArrayList;
import java.util.List;

public interface MainActionView extends BaseView
{
    void onBill(String title,Integer idBillType,String qrCode);

    void onChargeSimCard(Integer status);
    void onBillMotor(Integer status);
    void onBillCar(Integer status);
    void onBillToll(Integer status);
    void onBillTrafic(Integer status);

    void onPackSimCard(Integer status);

    void openBarcode(BarcodeType bill);

    void onBarcodReader(BarcodeType barcodeType);

    void onShowPaymentWithoutCardFragment(@Nullable QrModel model);

    void onPaymentWithoutCard(OnClickContinueSelectPayment onClickContinueSelectPayment,
                              String urlPayment,
                              int imageDrawable,
                              String title,
                              String amount,
                              SimChargePaymentInstance paymentInstance,
                              String mobile,
                              int PAYMENT_STATUS
    );

    void doTransferMoney();

    void onContact();

    void onInternetAlert();

    void showError(String message);

    void backToMainFragment();

    void openDrawer();

    void closeDrawer();

    void startAddCardActivity();

    void onBuyTicketClick(MatchItem matchBuyable);

    void onLeageClick(ArrayList<MatchItem> matchBuyable);

    void onPredict(PredictPosition position, Integer matchId, Boolean isPredictable, Boolean isFormationPredict);

    void onBackToPredict(PredictPosition position, Integer matchId, Boolean isPredictable, Boolean isFormationPredict);

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

    void onMainQuestionClick(Integer idQuest);

    void openPastResultFragment(LeagueTableParent parent, String matchId, Boolean isPredictable, String teamId, String imageLogo, String logoTitle);

    void openChargePaymentFragment(OnClickContinueSelectPayment onClickContinueSelectPayment, String urlPayment, int imageDrawable, String title, String priceFormat, SimChargePaymentInstance paymentInstance, String mobile, int PAYMENT_STATUS);

    void openWebView(MainActionView mainView, String uRl, String gds_token, String title);

    void openIncreaseWalletPaymentFragment(OnClickContinueSelectPayment onClickContinueSelectPayment, String urlPayment, int imageDrawable, String title, String amount, SimChargePaymentInstance paymentInstance, String mobile, int PAYMENT_STATUS);

    void openPackPaymentFragment(OnClickContinueSelectPayment onClickContinueSelectPayment, String urlPayment, int imageDrawable, String title, String amount, SimPackPaymentInstance paymentInstance, String mobile, int PAYMENT_STATUS);

    void getBuyEnable(BuyTicketAction buyTicketAction);

    void onSetPredictCompleted(Integer matchIdt, Boolean isPredictable, Boolean isFormationPredict, String message);

    void onBackToChargFragment(int PAYMENT_STATUS, Integer idBill);

    void backToAllServicePackage(Integer backState,Integer idMenuClicked);

    void onBackToHomeWallet(int i);

    void onBackToMatch();

    void onChangeMediaPosition(MediaPosition mediaPosition);

    void onShowDetailWinnerList(List<Winner> winnerList);

    void onShowLast5PastMatch(Integer teamLiveScoreId);

    void onIntroduceTeam();

    void onCompationTeam();

    void onHeadCoach(Integer coachId, String title, boolean flagFavorite);

    void onMediaPlayersFragment();

    void openBillPaymentFragment(String url, String textBillPayment, String number, Integer idSelectedBillType, String amount, int PAYMENT_STATUS_BILL);

    void onPerformanceEvaluation(Integer matchId, MatchItem matchItem);

    void onSetPlayerPerformanceEvaluation(Integer matchId, Integer playerId, String name, String imageURL);

    void onPlayerPerformanceEvaluationResult(Integer matchId, Integer playerId, String name, String imageURL);
}
