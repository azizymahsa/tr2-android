package com.traap.traapapp.ui.fragments.gateWay;

import android.os.Bundle;

import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.increaseWallet.ResponseIncreaseWallet;
import com.traap.traapapp.apiServices.model.withdrawWallet.WithdrawWalletRequest;
import com.traap.traapapp.apiServices.model.withdrawWallet.WithdrawWalletResponse;
import com.traap.traapapp.models.otherModels.headerModel.HeaderModel;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.utilities.ClearableEditText;
import com.traap.traapapp.utilities.Utility;

import org.greenrobot.eventbus.Subscribe;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import ru.kolotnev.formattedittext.MaskedEditText;

//import br.com.sapereaude.maskedEditText.MaskedEditText;

/**
 * Created by MahsaAzizi on 07/01/2020.
 */
public class WithdrawAccountFragment extends BaseFragment implements View.OnClickListener
{


    private View rootView, btnBackStep, btnGetMoney;
    private MainActionView mainView;
    private MaskedEditText edtShabaNum;
    private ClearableEditText edtCurrency;

    public WithdrawAccountFragment()
    {

    }

    public static WithdrawAccountFragment newInstance(MainActionView mainView)
    {
        WithdrawAccountFragment f = new WithdrawAccountFragment();
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
        rootView = inflater.inflate(R.layout.fragment_withdraw_acount, container, false);

        initView();


        return rootView;
    }

    private void initView()
    {

        edtShabaNum = rootView.findViewById(R.id.edtShabaNum);
        edtCurrency = rootView.findViewById(R.id.edtCurrency);
        btnGetMoney = rootView.findViewById(R.id.btnGetMoney);
        btnGetMoney.setOnClickListener(this);
        btnBackStep = rootView.findViewById(R.id.btnBackStep);
        btnBackStep.setOnClickListener(this);

        InputFilter[] filterArray = new InputFilter[1];
        filterArray[0] = new InputFilter.LengthFilter(19);
        edtCurrency.setFilters(filterArray);
        edtCurrency.addTextChangedListener(new TextWatcher()
        {
            private String current = "";
            @Override
            public void afterTextChanged(Editable ss)
            {
                edtCurrency.removeTextChangedListener(this);

                String s = edtCurrency.getText().toString();

                s = s.replace(",", "");
                if (s.length() > 0) {
                    DecimalFormat sdd = new DecimalFormat("#,###");
                    Double doubleNumber = Double.parseDouble(s);

                    String format = sdd.format(doubleNumber);
                    edtCurrency.setText(format);
                    edtCurrency.setSelection(format.length());

                }
                edtCurrency.addTextChangedListener(this);
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

            case R.id.btnGetMoney:
                if(edtCurrency.getText().toString().length()>3){
                    if( !edtShabaNum.getText().toString().contains("_")){
                        sendRequest();

                    }else {
                        mainView.showError("لطفا شماره شبا را وارد کنید.");

                    }
                }else{
                    mainView.showError("لطفا مبلغ را وارد کنید.");
                }
                break;
            case R.id.btnBackStep:
                mainView.backToMainFragment();
                break;
        }
    }

    private void sendRequest()
    {
        mainView.showLoading();
        WithdrawWalletRequest request = new WithdrawWalletRequest();
        request.setAmount(Integer.parseInt(edtCurrency.getText().toString().replaceAll(",","")));
        request.setSheba_number(edtShabaNum.getText().toString().replaceAll("-","").replaceAll(" ",""));
        SingletonService.getInstance().withdrawWalletService().WithdrawWalletService(new OnServiceStatus<WebServiceClass<WithdrawWalletResponse>>()
        {


            @Override
            public void onReady(WebServiceClass<WithdrawWalletResponse> response)
            {
                mainView.hideLoading();

                try
                {
                    if (response.info.statusCode == 200)
                    {
                        showAlert(getActivity(), response.info.message, 0);
                        clearEditText();
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

    private void clearEditText()
    {
        edtShabaNum.setText("");
        edtCurrency.setText("");
    }


    private void openURL(ResponseIncreaseWallet data)
    {
        Utility.openUrlCustomTab(getActivity(), data.getUrl());
    }
}
