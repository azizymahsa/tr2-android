package com.traap.traapapp.ui.fragments.paymentGateWay;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import br.com.simplepass.loading_button_lib.interfaces.OnAnimationEndListener;

import com.pixplicity.easyprefs.library.Prefs;
import com.squareup.picasso.Picasso;
import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.buyChargeWallet.BuyChargeWalletResponse;
import com.traap.traapapp.apiServices.model.getBalancePasswordLess.GetBalancePasswordLessRequest;
import com.traap.traapapp.apiServices.model.getBalancePasswordLess.GetBalancePasswordLessResponse;
import com.traap.traapapp.apiServices.model.matchList.MatchItem;
import com.traap.traapapp.apiServices.model.paymentMatch.PaymentMatchRequest;
import com.traap.traapapp.apiServices.model.paymentWallet.ResponsePaymentWallet;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.models.otherModels.paymentInstance.SimChargePaymentInstance;
import com.traap.traapapp.models.otherModels.paymentInstance.SimPackPaymentInstance;
import com.traap.traapapp.ui.activities.paymentResult.PaymentResultActivity;
import com.traap.traapapp.ui.activities.ticket.ShowTicketActivity;
import com.traap.traapapp.ui.adapters.Leaguse.DataBean;
import com.traap.traapapp.ui.adapters.Leaguse.matchResult.MatchAdapter;
import com.traap.traapapp.ui.dialogs.MessageAlertDialog;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.ui.fragments.paymentGateWay.paymentWallet.PaymentWalletImpl;
import com.traap.traapapp.ui.fragments.paymentGateWay.paymentWallet.PaymentWalletInteractor;
import com.traap.traapapp.ui.fragments.simcardCharge.imp.BuyChargeWalletImpl;
import com.traap.traapapp.ui.fragments.simcardCharge.imp.BuyChargeWalletInteractor;
import com.traap.traapapp.utilities.Logger;
import com.traap.traapapp.utilities.Tools;

/**
 * Created by MahsaAzizi on 11/25/2019.
 */
public class PaymentWalletFragment extends Fragment implements OnAnimationEndListener, View.OnClickListener, MatchAdapter.ItemClickListener, PaymentWalletInteractor.OnFinishedPaymentWalletListener, BuyChargeWalletInteractor.OnBuyChargeWalletListener
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
    private TextView tvBalance,tvDate;
    private int imageDrawable;
    private String amount;
    private String title;
    private SimChargePaymentInstance simChargePaymentInstance;
    private String mobile;
    private SimPackPaymentInstance simPackPaymentInstance;
    private TextView tvAmount;
    private TextView tvTitlePay;
    private ImageView imgLogo;


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



    public static PaymentWalletFragment newInstance(MainActionView mainActionView, int imageDrawable, SimChargePaymentInstance simChargePaymentInstance, String amount, String mobile, String title, SimPackPaymentInstance simPackPaymentInstance)
    {
        PaymentWalletFragment fragment = new PaymentWalletFragment();
        Bundle args = new Bundle();

        fragment.setMainView(mainActionView, imageDrawable, amount, title,simChargePaymentInstance,mobile,simPackPaymentInstance);
        return fragment;
    }

    private void setMainView(MainActionView mainView, int imageDrawable, String amount, String title, SimChargePaymentInstance simChargePaymentInstance, String mobile, SimPackPaymentInstance simPackPaymentInstance)
    {
        this.mainView = mainView;
        this.imageDrawable=imageDrawable;
        this.amount=amount;
        this.title=title;
        this.simChargePaymentInstance=simChargePaymentInstance;
        this.mobile=mobile;
        this.simPackPaymentInstance=simPackPaymentInstance;

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

            tvAmount = rootView.findViewById(R.id.tvAmount);
            tvTitlePay = rootView.findViewById(R.id.tvTitlePay);
            imgLogo = rootView.findViewById(R.id.imgLogo);

            llConfirm.setVisibility(View.VISIBLE);

            setContentData();
            requestGetBalance();

        } catch (Exception e)
        {
            Logger.e("---Exception---", e.getMessage());
        }
    }

    private void setContentData()
    {
        tvAmount.setText(amount);
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
        GetBalancePasswordLessRequest request = new GetBalancePasswordLessRequest();
        request.setIsWallet(true);
        SingletonService.getInstance().getBalancePasswordLessService().GetBalancePasswordLessService(new OnServiceStatus<WebServiceClass<GetBalancePasswordLessResponse>>()
        {


            @Override
            public void onReady(WebServiceClass<GetBalancePasswordLessResponse> response)
            {

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
        tvBalance.setText(data.getBalanceAmount());
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
                Tools.showToast(getContext(), "رمز کارت وارد نشده است.", R.color.red);

            }


        } else if (v.getId() == R.id.btnBack)
        {
            MessageAlertDialog dialog = new MessageAlertDialog(getActivity(), "بازگشت به خانه", "آیا از بستن این صفحه مطمئن هستید؟",
                    false, "بله", "بستن", listener);
            dialog.show(getActivity().getFragmentManager(), "dialog");
        }
    };

    private void requestBuyPackageWallet(String toString, SimPackPaymentInstance simPackPaymentInstance, String mobile, String amount)
    {

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

                mainView.backToMainFragment();
            }
        };
        initView();
        paymentWallet = new PaymentWalletImpl();
        buyChargeWallet=new BuyChargeWalletImpl();
        addDataRecyclerList();

        return rootView;
    }

    private void addDataRecyclerList()
    {


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
            Tools.showToast(getContext(), response.getMessage(), R.color.red);


        } else
        {
            Intent intent = new Intent(getContext(), ShowTicketActivity.class);

            intent.putExtra("RefrenceNumber", response.getRefNumber());
            intent.putExtra("isTransactionList", false);
            startActivity(intent);
        }


    }

    @Override
    public void onErrorPaymentWallet(String error)
    {
        mainView.hideLoading();
        Tools.showToast(getContext(), error, R.color.red);
    }

    @Override
    public void onSuccessBuyChargeWallet(WebServiceClass<BuyChargeWalletResponse> response)
    {
        mainView.hideLoading();

        // Tools.showToast(getContext(), "خرید شارژ با موفقیت انجام شد.", R.color.green);
        Intent intent = new Intent(getContext(), PaymentResultActivity.class);
        intent.putExtra("RefrenceNumber", response.data.getRefNumber());
        intent.putExtra("StatusPayment", true);
        getContext().startActivity(intent);

    }

    @Override
    public void onErrorBuyChargeWallet(String error)
    {
        mainView.hideLoading();

        Tools.showToast(getContext(), "پرداخت ناموفق", R.color.red);

       /* Intent intent = new Intent(getContext(), PaymentResultActivity.class);
        intent.putExtra("RefrenceNumber", item.getId().toString());
        intent.putExtra("StatusPayment", false);
        getContext().startActivity(intent);*/

    }
}
