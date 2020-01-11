package com.traap.traapapp.ui.fragments.gateWay;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.increaseWallet.RequestIncreaseWallet;
import com.traap.traapapp.apiServices.model.increaseWallet.ResponseIncreaseWallet;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.models.otherModels.headerModel.HeaderModel;
import com.traap.traapapp.models.otherModels.paymentInstance.SimChargePaymentInstance;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.utilities.ClearableEditText;
import com.traap.traapapp.utilities.Utility;

import org.greenrobot.eventbus.Subscribe;

import java.text.DecimalFormat;

/**
 * Created by MahsaAzizi on 28/12/2019.
 */
public class IncreaseInventoryFragment extends BaseFragment implements View.OnClickListener
{


    private View rootView, btnChargeConfirmRightel;
    private MainActionView mainView;
    private ClearableEditText etAmount;


    public IncreaseInventoryFragment()
    {

    }

    public static IncreaseInventoryFragment newInstance(MainActionView mainView)
    {
        IncreaseInventoryFragment f = new IncreaseInventoryFragment();
        f.setMainView(mainView);
        return f;
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

        initView();


        return rootView;
    }

    private void initView()
    {
        etAmount = rootView.findViewById(R.id.etAmount);
        btnChargeConfirmRightel = rootView.findViewById(R.id.btnChargeConfirmRightel);
        btnChargeConfirmRightel.setOnClickListener(this);

        InputFilter[] filterArray = new InputFilter[1];
        filterArray[0] = new InputFilter.LengthFilter(10);
        etAmount.setFilters(filterArray);
        etAmount.addTextChangedListener(new TextWatcher()
        {
            private String current = "";
            @Override
            public void afterTextChanged(Editable ss)
            {
                etAmount.removeTextChangedListener(this);

                String s = etAmount.getText().toString();

                s = s.replace(",", "");
                if (s.length() > 0) {
                    DecimalFormat sdd = new DecimalFormat("#,###");
                    Double doubleNumber = Double.parseDouble(s);

                    String format = sdd.format(doubleNumber);
                    etAmount.setText(format);
                    etAmount.setSelection(format.length());

                }
                etAmount.addTextChangedListener(this);
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
                if (etAmount.getText().toString().length() > 0)
                {
                    if (etAmount.getText().toString().length() > 3)
                    {
                        sendRequest();
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
        /*case R.id.etAmount:
            // sendRequest();
            break;*/
        }

    }

    private void sendRequest()
    {
        mainView.showLoading();
        RequestIncreaseWallet request = new RequestIncreaseWallet();
        request.setAmount(Integer.parseInt(etAmount.getText().toString().replaceAll(",", "")));
        SingletonService.getInstance().getBalancePasswordLessService().IncreaseInventoryWalletService(new OnServiceStatus<WebServiceClass<ResponseIncreaseWallet>>()
        {


            @Override
            public void onReady(WebServiceClass<ResponseIncreaseWallet> response)
            {
                mainView.hideLoading();

                try
                {
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

        String title=" ریال  بابت \"افزایش موجودی\"، از حساب شما کسر خواهد شد."+etAmount.getText().toString()+"با انجام این پرداخت، مبلغ ";
        String mobile="";
       /* mainView.openIncreaseWalletPaymentFragment(this,data.getUrl(), R.drawable.ic_inc_inv_small,
                title, etAmount.getText().toString(), paymentInstance, mobile,TrapConfig.PAYMENT_STAUS_ChargeSimCard);*/
    }
}
