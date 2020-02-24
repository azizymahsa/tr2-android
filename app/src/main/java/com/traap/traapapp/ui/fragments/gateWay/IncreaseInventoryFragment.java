package com.traap.traapapp.ui.fragments.gateWay;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;

import com.pixplicity.easyprefs.library.Prefs;
import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.getBalancePasswordLess.SettingBalance;
import com.traap.traapapp.apiServices.model.increaseWallet.RequestIncreaseWallet;
import com.traap.traapapp.apiServices.model.increaseWallet.ResponseIncreaseWallet;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.models.otherModels.headerModel.HeaderModel;
import com.traap.traapapp.models.otherModels.paymentInstance.SimChargePaymentInstance;
import com.traap.traapapp.ui.adapters.increaseinventory.IncreaseInventoryAmountsAdapter;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.ui.fragments.simcardCharge.OnClickContinueSelectPayment;
import com.traap.traapapp.utilities.ConvertPersianNumberToString;
import com.traap.traapapp.utilities.MyGridView;
import com.traap.traapapp.utilities.Tools;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Objects;

/**
 * Created by MahsaAzizi on 28/12/2019.
 */
public class IncreaseInventoryFragment extends BaseFragment implements View.OnClickListener, OnClickContinueSelectPayment, IncreaseInventoryAmountsAdapter.IncreaseInventoryAmounts
{


    private View rootView, btnChargeConfirmRightel;
    private MainActionView mainView;
    //private ClearableEditText etAmount;
    private EditText txtAmount;
    private TextView tvMines, tvPlus, txtChrAmount;
    private int counterAmount = 0;
    private SettingBalance settingsData;
    private MyGridView rvAmounts;
    private IncreaseInventoryAmountsAdapter amountsAdapter;


    public IncreaseInventoryFragment()
    {

    }


    public static IncreaseInventoryFragment newInstance(MainActionView mainView, SettingBalance settingsData)
    {
        IncreaseInventoryFragment f = new IncreaseInventoryFragment();
        f.setMainView(mainView);
        f.setSetting(settingsData);
        return f;
    }

    private void setSetting(SettingBalance settingsData)
    {
        this.settingsData=settingsData;
    }

    private void setMainView(MainActionView mainView)
    {
        this.mainView = mainView;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_increase_inventory, container, false);

        Prefs.putBoolean("isMainWalletFragment", false);

        initView();

        setAmountsList();

        return rootView;
    }

    private void initView()
    {
        rvAmounts=rootView.findViewById(R.id.rvAmounts);

    txtChrAmount = rootView.findViewById(R.id.txtChrAmount);
    tvMines = rootView.findViewById(R.id.tvMines);
    tvPlus = rootView.findViewById(R.id.tvPlus);
    txtAmount = rootView.findViewById(R.id.txtAmount);
    //  etAmount = rootView.findViewById(R.id.etAmount);
    btnChargeConfirmRightel = rootView.findViewById(R.id.btnChargeConfirmRightel);
    btnChargeConfirmRightel.setOnClickListener(this);

    tvPlus.setOnClickListener(this);
    tvMines.setOnClickListener(this);

    txtAmount.addTextChangedListener(new TextWatcher()
    {
        private String current = "";

        @Override
        public void afterTextChanged(Editable ss)
        {
            txtAmount.removeTextChangedListener(this);

            String s = txtAmount.getText().toString();

            s = s.replace(",", "");
            if (s.length() > 0)
            {
                DecimalFormat sdd = new DecimalFormat("#,###");
                Double doubleNumber = Double.parseDouble(s);

                String format = sdd.format(doubleNumber);
                txtAmount.setText(format);
                txtAmount.setSelection(format.length());

            }
            txtAmount.addTextChangedListener(this);

            if (txtAmount.getText().toString().replaceAll(",", "").equals(""))
                txtAmount.setText("0");

            txtChrAmount.setText(ConvertPersianNumberToString.getNumberConvertToString(BigDecimal.valueOf(Integer.parseInt(txtAmount.getText().toString().replaceAll(",", ""))), "ریال"));

        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3)
        {


        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {

        }


    });
        changeTitle();

    }

    private void setAmountsList()
    {

        amountsAdapter = new IncreaseInventoryAmountsAdapter(settingsData.getWalletCheckout().getAddValueList(),mainView,IncreaseInventoryFragment.this);

        amountsAdapter.setHasStableIds(true);
        rvAmounts.setAdapter(amountsAdapter);
        rvAmounts.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        rvAmounts.setHasFixedSize(true);
       // rvTagCharities.setItemViewCacheSize(20);
      //  rvTagCharities.setDrawingCacheEnabled(true);
      //  rvAmounts.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

       // ViewCompat.setNestedScrollingEnabled(rvTagCharities, false);
       // ViewCompat.setNestedScrollingEnabled(nested, false);
    }

    private void changeTitle()
    {
        WalletTitle walletTitle = new WalletTitle();
        walletTitle.setTitle("افزایش موجودی");

        EventBus.getDefault().post(walletTitle);

    }
    @Override
    public void onResume(){
        super.onResume();
        WalletTitle walletTitle = new WalletTitle();
        walletTitle.setTitle("افزایش موجودی");

        EventBus.getDefault().post(walletTitle);
    }

    @Override
    public void onStop()
    {
        super.onStop();
        WalletTitle walletTitle = new WalletTitle();
        walletTitle.setTitle("کیف پول");

        EventBus.getDefault().post(walletTitle);
    }

    @Subscribe
    public void getHeaderContent(HeaderModel headerModel)
    {

    }


    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btnChargeConfirmRightel:
                if (txtAmount.getText().toString().length() > 0)
                {
                    if (txtAmount.getText().toString().length() >= 3)
                    {
                        if (Integer.parseInt(txtAmount.getText().toString().replaceAll(",", "")) <= 500000000)
                        {
                            sendRequest();
                        } else
                        {
                            mainView.showError(getResources().getString(R.string._deny_currency_max_five));

                        }
                    } else
                    {
                        mainView.showError("مبلغ کمتر از سه رقم نباشد.");
                    }

                } else
                {
                    mainView.showError("لطفا مبلغ را وارد کنید.");
                }
                // showAlert(getActivity(), "لطفا مبلغ را وارد کنید.", 0);

                break;
            case R.id.tvPlus:

                counterAmount = Integer.parseInt(txtAmount.getText().toString().replaceAll(",", ""));
                counterAmount++;
                txtAmount.setText(counterAmount + "");
                break;

            case R.id.tvMines:
                counterAmount = Integer.parseInt(txtAmount.getText().toString().replaceAll(",", ""));
                if (counterAmount == 0)
                    return;
                counterAmount--;
                txtAmount.setText(counterAmount + "");
                break;

        }

    }

    private void sendRequest()
    {
        mainView.showLoading();
        RequestIncreaseWallet request = new RequestIncreaseWallet();
        request.setAmount(Integer.parseInt(txtAmount.getText().toString().replaceAll(",", "")));
        SingletonService.getInstance().getBalancePasswordLessService().IncreaseInventoryWalletService(new OnServiceStatus<WebServiceClass<ResponseIncreaseWallet>>()
        {


            @Override
            public void onReady(WebServiceClass<ResponseIncreaseWallet> response)
            {

                try
                {
                    mainView.hideLoading();

                    if (response.info.statusCode == 200)
                    {
                        openURL(response.data);

                    } else
                    {

                        mainView.showError(response.info.message);

                    }
                } catch (Exception e)
                {
                    mainView.hideLoading();

                    mainView.showError(e.getMessage());

                }


            }

            @Override
            public void onError(String message)
            {

                mainView.showError(message);
                mainView.hideLoading();
                if (Tools.isNetworkAvailable(Objects.requireNonNull(getActivity())))
                {
                    mainView.showError("خطای ارتباط با سرور!");

                }
                else
                {
                    mainView.showError(getString(R.string.networkErrorMessage));

                }


            }
        }, request);
    }


    private void openURL(ResponseIncreaseWallet data)
    {
        //  Utility.openUrlCustomTab(getActivity(), data.getUrl());
        SimChargePaymentInstance paymentInstance = new SimChargePaymentInstance();
        paymentInstance.setPAYMENT_STATUS(TrapConfig.PAYMENT_STATUS_INCREASE_WALLET);//13
        /*paymentInstance.setOperatorType(operatorType);
        paymentInstance.setSimcardType(simcardType);
        paymentInstance.setTypeCharge(Integer.valueOf(chargeType));*/

        String title = "با انجام این پرداخت، مبلغ " + txtAmount.getText().toString() + "ریال بابت \"افزایش موجودی\"، از حساب شما کسر خواهد شد.";
        String mobile = "";
        mainView.openIncreaseWalletPaymentFragment(this, data.getUrl(), R.drawable.ic_increase_payment,
                title, txtAmount.getText().toString(), paymentInstance, mobile, TrapConfig.PAYMENT_STATUS_INCREASE_WALLET);
    }

    @Override
    public void onBackClicked()
    {

    }

    @Override
    public void showPaymentParentLoading()
    {

    }

    @Override
    public void hidePaymentParentLoading()
    {

    }

    @Override
    public void onPaymentCancelAndBack()
    {

    }

    @Override
    public void onBalanceClicked(String balance)
    {
        txtAmount.setText(balance);

    }
}
