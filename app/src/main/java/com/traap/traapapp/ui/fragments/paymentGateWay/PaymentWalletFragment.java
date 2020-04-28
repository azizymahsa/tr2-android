package com.traap.traapapp.ui.fragments.paymentGateWay;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import br.com.simplepass.loading_button_lib.interfaces.OnAnimationEndListener;

import com.squareup.picasso.Picasso;
import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.buyChargeWallet.BuyChargeWalletResponse;
import com.traap.traapapp.apiServices.model.buyPackage.response.BuyPackageWalletResponse;
import com.traap.traapapp.apiServices.model.getBalancePasswordLess.GetBalancePasswordLessRequest;
import com.traap.traapapp.apiServices.model.getBalancePasswordLess.GetBalancePasswordLessResponse;
import com.traap.traapapp.apiServices.model.matchList.MatchItem;
import com.traap.traapapp.apiServices.model.paymentMatch.PaymentMatchRequest;
import com.traap.traapapp.apiServices.model.paymentWallet.ResponsePaymentWallet;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.models.otherModels.paymentInstance.SimChargePaymentInstance;
import com.traap.traapapp.models.otherModels.paymentInstance.SimPackPaymentInstance;
import com.traap.traapapp.ui.activities.paymentResult.PaymentResultActivity;
import com.traap.traapapp.ui.activities.paymentResult.PaymentResultChargeActivity;
import com.traap.traapapp.ui.activities.ticket.ShowTicketActivity;
import com.traap.traapapp.ui.adapters.Leaguse.DataBean;
import com.traap.traapapp.ui.adapters.Leaguse.matchResult.MatchAdapter;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.dialogs.MessageAlertDialog;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.ui.fragments.paymentGateWay.paymentWallet.PaymentWalletImpl;
import com.traap.traapapp.ui.fragments.paymentGateWay.paymentWallet.PaymentWalletInteractor;
import com.traap.traapapp.ui.fragments.simcardCharge.imp.BuyChargeWalletImpl;
import com.traap.traapapp.ui.fragments.simcardCharge.imp.BuyChargeWalletInteractor;
import com.traap.traapapp.ui.fragments.simcardPack.imp.BuyPackageWalletImpl;
import com.traap.traapapp.ui.fragments.simcardPack.imp.BuyPackageWalletInteractor;
import com.traap.traapapp.utilities.Logger;
import com.traap.traapapp.utilities.Tools;
import com.traap.traapapp.utilities.Utility;
import com.uncopt.android.widget.text.justify.JustifiedTextView;

/**
 * Created by MahsaAzizi on 11/25/2019.
 */
public class PaymentWalletFragment extends BaseFragment implements OnAnimationEndListener, View.OnClickListener, MatchAdapter.ItemClickListener, PaymentWalletInteractor.OnFinishedPaymentWalletListener, BuyChargeWalletInteractor.OnBuyChargeWalletListener, BuyPackageWalletInteractor.OnBuyPackageWalletListener
{
    private View rootView;

    private MainActionView mainView;

    /*scroll view*/
    public List<DataBean> data = new ArrayList<>();
    private CircularProgressButton btnBuy;
    private TextView btnBack;
    private EditText etPin2;
    private MessageAlertDialog.OnConfirmListener listener = null;
    private View llConfirm, llInVisible;
    private PaymentMatchRequest paymentMatchRequest;
    private PaymentWalletImpl paymentWallet;
    private BuyChargeWalletImpl buyChargeWallet;
    private BuyPackageWalletImpl buyPackWallet;
    private TextView tvBalance,tvDate;
    private int imageDrawable;
    private String amount;
    private String title;
    private SimChargePaymentInstance simChargePaymentInstance;
    private String mobile;
    private SimPackPaymentInstance simPackPaymentInstance;
    private TextView tvAmount;
    private JustifiedTextView tvTitlePay;
    private ImageView imgLogo;
    private int PAYMENT_STATUS =0;
    public Integer balance=0;



    public PaymentWalletFragment()
    {

    }


    public static PaymentWalletFragment newInstance(MainActionView mainActionView, PaymentMatchRequest paymentMatchRequest)
    {
        PaymentWalletFragment fragment = new PaymentWalletFragment();
        Bundle args = new Bundle();

        fragment.setMainView(mainActionView, paymentMatchRequest);
        return fragment;
    }



    public static PaymentWalletFragment newInstance(MainActionView mainActionView, int imageDrawable, SimChargePaymentInstance simChargePaymentInstance, String amount, String mobile, String title, SimPackPaymentInstance simPackPaymentInstance,int PAYMENT_STATUS)
    {
        PaymentWalletFragment fragment = new PaymentWalletFragment();
        Bundle args = new Bundle();

        fragment.setMainView(mainActionView, imageDrawable, amount, title,simChargePaymentInstance,mobile,simPackPaymentInstance,PAYMENT_STATUS);
        return fragment;
    }

    private void setMainView(MainActionView mainView, int imageDrawable, String amount, String title, SimChargePaymentInstance simChargePaymentInstance, String mobile, SimPackPaymentInstance simPackPaymentInstance,int PAYMENT_STATUS)
    {
        this.mainView = mainView;
        this.imageDrawable=imageDrawable;
        this.amount=amount;
        this.title=title;
        this.simChargePaymentInstance=simChargePaymentInstance;
        this.mobile=mobile;
        this.simPackPaymentInstance=simPackPaymentInstance;
        this.PAYMENT_STATUS=PAYMENT_STATUS;

    }


    private void setMainView(MainActionView mainView, PaymentMatchRequest paymentMatchRequest)
    {
        this.mainView = mainView;
        this.paymentMatchRequest = paymentMatchRequest;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }

    public void initView()
    {
        try
        {

            etPin2 = rootView.findViewById(R.id.etPin2);

            tvBalance=rootView.findViewById(R.id.tvBalance);
            tvDate=rootView.findViewById(R.id.tvDate);

            llConfirm = rootView.findViewById(R.id.llConfirm);
            llInVisible = rootView.findViewById(R.id.llInVisible);

            btnBuy = rootView.findViewById(R.id.btnBuy);
            btnBuy.setOnClickListener(clickListener);

            btnBack = rootView.findViewById(R.id.btnBack);
            btnBack.setOnClickListener(clickListener);

            tvAmount = rootView.findViewById(R.id.tvAmountPay);
            tvTitlePay = rootView.findViewById(R.id.tvTitlePay);
            imgLogo = rootView.findViewById(R.id.imgLogo);

            llConfirm.setVisibility(View.VISIBLE);

/*
            tvTitlePay.setTypeFace(Typeface.createFromAsset(getActivity().getAssets(), "fonts/iran_sans_normal.ttf"));
            tvTitlePay.setLineSpacing(10);
            tvTitlePay.setTextSize(getResources().getDimension(R.dimen.textSize_14dp));
*/

            setContentData();
            requestGetBalance();

        } catch (Exception e)
        {
            Logger.e("---Exception---", e.getMessage());
        }
    }

    private void setContentData()
    {
        tvAmount.setText(Utility.priceFormat(amount));
        tvTitlePay.setText(title);

        if (imageDrawable == 0)
        {
            imgLogo.setVisibility(View.GONE);
        } else
        {
            Picasso.with(getActivity()).load(imageDrawable).into(imgLogo);
        }
    }

    private void requestGetBalance()
    {
        mainView.showLoading();
        GetBalancePasswordLessRequest request = new GetBalancePasswordLessRequest();
        request.setIsWallet(true);
        SingletonService.getInstance().getBalancePasswordLessService().GetBalancePasswordLessService(new OnServiceStatus<WebServiceClass<GetBalancePasswordLessResponse>>()
        {


            @Override
            public void onReady(WebServiceClass<GetBalancePasswordLessResponse> response)
            {
                mainView.hideLoading();
                try
                {
                    if (response.info.statusCode == 200)
                    {
                        setBalanceData(response.data);

                    } else
                    {

                        mainView.showError(response.info.message);

                    }
                } catch (Exception e)
                {
                    mainView.showError(e.getMessage());

                }


            }

            @Override
            public void onError(String message)
            {


                mainView.showError(message);

            }
        }, request);
    }

    private void setBalanceData(GetBalancePasswordLessResponse data)
    {
        tvBalance.setText(Utility.priceFormat(data.getBalanceAmount()));
        try
        {
            balance=Integer.valueOf(data.getBalanceAmount());
            if (balance<Integer.valueOf(amount)){
                btnBuy.setEnabled(false);
                btnBuy.setClickable(false);
                btnBuy.setText("موجودی کافی نمی باشد.");
                btnBuy.setTextColor(getActivity().getResources().getColor(R.color.black));
                btnBuy.setBackground(getActivity().getResources().getDrawable(R.drawable.background_button_login_disable));

            }

        }catch (Exception e){};
        tvDate.setText(data.getDateTime());
    }

    View.OnClickListener clickListener = v ->
    {
        if (v.getId() == R.id.btnBuy)
        {


            if (etPin2.getText().toString() != null && etPin2.getText().toString().length() > 3)
            {

                mainView.showLoading();

               // if (simChargePaymentInstance.getPAYMENT_STATUS()== TrapConfig.PAYMENT_STAUS_ChargeSimCard){
                if (simChargePaymentInstance!=null){

                    requestBuyChargeWallet(etPin2.getText().toString(),simChargePaymentInstance,mobile,amount);
                }else if (simPackPaymentInstance!=null)
                {
                   requestBuyPackageWallet(etPin2.getText().toString(),simPackPaymentInstance,mobile,amount);
                }else {
                    paymentMatchRequest.setPin2(etPin2.getText().toString());
                    callPaymentWalletRequest();
                }
            } else
            {
                showAlertFailure(getContext(),"رمز کارت وارد نشده است.","",true);

            }


        }
        else if (v.getId() == R.id.btnBack)
        {
            mainView.onBackToChargFragment(PAYMENT_STATUS);
        }
    };

    private void requestBuyPackageWallet(String pin2, SimPackPaymentInstance simPackPaymentInstance, String mobile, String amount)
    {
        buyPackWallet.findBuyPackageWalletRequest(this,simPackPaymentInstance.getOperatorType().toString(),amount,mobile,pin2,simPackPaymentInstance.getRequestId()
        ,simPackPaymentInstance.getProfileId().toString(),simPackPaymentInstance.getTitlePackageType());

    }

    private void requestBuyChargeWallet(String pin2, SimChargePaymentInstance simChargePaymentInstance, String mobile, String amount)
    {

        buyChargeWallet.findBuyChargeWalletRequest(this,simChargePaymentInstance.getOperatorType()
                ,simChargePaymentInstance.getSimcardType(),simChargePaymentInstance.getTypeCharge(),
                amount,mobile,pin2);

    }

    private void callPaymentWalletRequest()
    {
        mainView.showLoading();

        //paymentWallet.paymentWalletRequest(this, paymentMatchRequest);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        if (rootView != null)
        {
            rootView = null;
        }

        rootView = inflater.inflate(R.layout.fragment_payment_wallet, container, false);

        listener = new MessageAlertDialog.OnConfirmListener()
        {


            @Override
            public void onConfirmClick()
            {
                mainView.backToMainFragment();

            }

            @Override
            public void onCancelClick()
            {

               // mainView.backToMainFragment();
            }
        };
        initView();
        paymentWallet = new PaymentWalletImpl();
        buyChargeWallet=new BuyChargeWalletImpl();
        buyPackWallet=new BuyPackageWalletImpl();

        return rootView;
    }



    @Override
    public void onDestroy()
    {
        super.onDestroy();

    }

    @Override
    public void onStop()
    {
        super.onStop();


    }

    @Override
    public void onResume()
    {
        super.onResume();

    }

    @Override
    public void onPause()
    {
        super.onPause();
    }


    @Override
    public void onAnimationEnd()
    {


    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
           /* case R.id.rbMellat:
                rbMellat.setEnabled(true);
                llConfirm.setVisibility(View.VISIBLE);
                llInVisible.setVisibility(View.GONE);
                break;
           */
        }

    }


    @Override
    public void onItemClick(View view, int position, MatchItem matchItem)
    {

    }

    @Override
    public void onItemPredictClick(View view, int position, MatchItem matchItem)
    {

    }

    @Override
    public void onItemLogoTeamClick(View view, Integer id, String logo, String name)
    {

    }

    @Override
    public void onFinishedPaymentWallet(ResponsePaymentWallet response)
    {
        mainView.hideLoading();
        if (response.getMessage().contains("خطا"))
        {
            showAlertFailure(getContext(),response.getMessage(),"",true);


        } else
        {
            Intent intent = new Intent(getContext(), ShowTicketActivity.class);

            intent.putExtra("RefrenceNumber", response.getRefNumber());
            intent.putExtra("isTransactionList", false);
            startActivityForResult(intent,100);
        }


    }

    @Override
    public void onErrorPaymentWallet(String error)
    {
        mainView.hideLoading();
        showAlertFailure(getContext(),error,"",true);
    }

    @Override
    public void onSuccessBuyChargeWallet(WebServiceClass<BuyChargeWalletResponse> response)
    {
        mainView.hideLoading();

        Intent intent = new Intent(getContext(), PaymentResultChargeActivity.class);
        intent.putExtra("RefrenceNumber", response.data.getRefNumber());

        intent.putExtra("PaymentStatus",PAYMENT_STATUS);
        getActivity().startActivityForResult(intent,33);

    }

    @Override
    public void onErrorBuyChargeWallet(String error)
    {
        mainView.hideLoading();

        showAlertFailure(getContext(),error,"",true);

    }

    @Override
    public void onSuccessBuyPackageWallet(WebServiceClass<BuyPackageWalletResponse> response)
    {
        mainView.hideLoading();

        Intent intent = new Intent(getContext(), PaymentResultChargeActivity.class);
        intent.putExtra("RefrenceNumber", response.data.getRefNumber());

        intent.putExtra("PaymentStatus",PAYMENT_STATUS);
        getActivity().startActivityForResult(intent,33);
    }

    @Override
    public void onErrorBuyPackageWallet(String error)
    {
        mainView.hideLoading();

        showAlertFailure(getContext(),error,"",true);

    }
}
