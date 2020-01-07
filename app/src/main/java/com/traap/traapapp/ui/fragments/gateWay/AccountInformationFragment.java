package com.traap.traapapp.ui.fragments.gateWay;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Spinner;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.increaseWallet.RequestIncreaseWallet;
import com.traap.traapapp.apiServices.model.increaseWallet.ResponseIncreaseWallet;
import com.traap.traapapp.models.otherModels.headerModel.HeaderModel;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.utilities.ClearableEditText;
import com.traap.traapapp.utilities.Utility;

import org.greenrobot.eventbus.Subscribe;

/**
 * Created by MahsaAzizi on 07/01/2020.
 */
public class AccountInformationFragment extends BaseFragment implements View.OnClickListener
{


    private View rootView, btnChargeConfirmRightel;
    private MainActionView mainView;
    private ClearableEditText edtDestination;
    private ImageView ivContact;
    private Spinner spinnerType;

    public AccountInformationFragment()
    {

    }

    public static AccountInformationFragment newInstance(MainActionView mainView)
    {
        AccountInformationFragment f = new AccountInformationFragment();
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
        rootView = inflater.inflate(R.layout.fragment_account_information, container, false);

        initView();




        return rootView;
    }

    private void initView()
    {



        edtDestination = rootView.findViewById(R.id.edtDestination);
        InputFilter[] filterArray = new InputFilter[1];
        filterArray[0] = new InputFilter.LengthFilter(19);
        edtDestination.setFilters(filterArray);

        btnChargeConfirmRightel = rootView.findViewById(R.id.btnChargeConfirmRightel);
        btnChargeConfirmRightel.setOnClickListener(this);
        edtDestination.addTextChangedListener(new TextWatcher()
        {
            int len = 0;

            @Override
            public void afterTextChanged(Editable s)
            {
                String str = edtDestination.getText().toString();
                if (str.length() == 4 && len < str.length())
                {//len check for backspace
                    edtDestination.append("-");
                }
                if (str.length() == 9 && len < str.length())
                {//len check for backspace
                    edtDestination.append("-");
                }
                if (str.length() == 14 && len < str.length())
                {//len check for backspace
                    edtDestination.append("-");
                }
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3)
            {

                String str = edtDestination.getText().toString();
                len = str.length();
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
            case R.id.ivContact:
                mainView.onContact();
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
        request.setAmount(Integer.parseInt(edtDestination.getText().toString()));
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
        Utility.openUrlCustomTab(getActivity(), data.getUrl());
    }
}
