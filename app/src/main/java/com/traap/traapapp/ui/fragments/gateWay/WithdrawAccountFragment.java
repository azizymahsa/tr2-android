package com.traap.traapapp.ui.fragments.gateWay;

import android.app.MediaRouteButton;
import android.os.Bundle;

import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.increaseWallet.ResponseIncreaseWallet;
import com.traap.traapapp.apiServices.model.withdrawWallet.WithdrawWalletRequest;
import com.traap.traapapp.apiServices.model.withdrawWallet.WithdrawWalletResponse;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.models.otherModels.headerModel.HeaderModel;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.dialogs.MessageAlertDialog;
import com.traap.traapapp.ui.dialogs.WalletWithdrawAlertDialog;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.utilities.ClearableEditText;
import com.traap.traapapp.utilities.ConvertPersianNumberToString;
import com.traap.traapapp.utilities.NumberTextWatcher;
import com.traap.traapapp.utilities.Tools;
import com.traap.traapapp.utilities.Utility;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Objects;

import nl.garvelink.iban.IBAN;
import nl.garvelink.iban.Modulo97;
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
    private EditText edtCurrency;
    ConvertPersianNumberToString convertPersianNumberToString = new ConvertPersianNumberToString();
    private Fragment fragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private LinearLayout llWithDraw;

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

        llWithDraw = rootView.findViewById(R.id.llWithDraw);
        edtShabaNum = rootView.findViewById(R.id.edtShabaNum);
        edtCurrency = rootView.findViewById(R.id.edtCurrency);
        btnGetMoney = rootView.findViewById(R.id.btnGetMoney);
        btnGetMoney.setOnClickListener(this);
        btnBackStep = rootView.findViewById(R.id.btnBackStep);
        btnBackStep.setOnClickListener(this);

        InputFilter[] filterArray = new InputFilter[1];
        try
        {
            //filterArray[0] = new InputFilter.LengthFilter(8);
            //edtCurrency.setFilters(filterArray);
            edtCurrency.addTextChangedListener(new NumberTextWatcher(edtCurrency));
        } catch (Exception e)
        {
            e.getMessage();
        }

        changeTitle();

    }
    private void changeTitle()
    {
        WalletTitle walletTitle = new WalletTitle();
        walletTitle.setTitle("برداشت از کیف پول");

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

            case R.id.btnGetMoney:
                if (edtCurrency.getText().toString().length() >= 3)
                {
                    if (Integer.parseInt(edtCurrency.getText().toString().replaceAll(",", "")) <= 900000)
                    {
//if (edtShabaNum.getText().toString().length()==24){
                        if (!edtShabaNum.getText().toString().contains("_"))
                        {

                            if (!Modulo97.verifyCheckDigits(edtShabaNum.getText().toString().replaceAll("-", ""))){
                                mainView.showError("لطفا شماره شبا را صحیح وارد کنید.");

                                return;
                            }

                            String txtAmountDigit = " مبلغ " + edtCurrency.getText().toString() + " ریال ";
                            String txtAmountChar = convertPersianNumberToString.getNumberConvertToString(BigDecimal.valueOf(Integer.parseInt(edtCurrency.getText().toString().replaceAll(",", ""))), "ریال");
                            String txtNumberShaba = edtShabaNum.getText().toString() + " به شماره شبا ";
                            String txtName = "";

                            WalletWithdrawAlertDialog dialog = new WalletWithdrawAlertDialog(getActivity(), "تایید برداشت از کیف پول", "از : کارت کیف پول " + TrapConfig.HEADER_USER_NAME, true,
                                    new WalletWithdrawAlertDialog.OnConfirmListener()
                                    {
                                        @Override
                                        public void onConfirmClick()
                                        {
                                            sendRequest();

                                        }

                                        @Override
                                        public void onCancelClick()
                                        {
                                          //  mainView.backToMainFragment();
                                        }
                                    }, txtAmountDigit,"("+ txtAmountChar+")", txtNumberShaba, txtName);
                            dialog.show((getActivity()).getFragmentManager(), "dialog");

                        } else
                        {
                            mainView.showError("لطفا شماره شبا را وارد کنید.");

                        }
                    } else
                    {
                        mainView.showError("مبلغ غیر مجاز می باشد.(حداکثر 900,000 ریال)");

                    }
                } else
                {
                    mainView.showError("لطفا مبلغ را وارد کنید.(حداقل سه رقم)");
                }
                break;
            case R.id.btnBackStep:

                fragment = DetailsCartFragment.newInstance(mainView);
                showFragment(fragment);
                break;
        }
    }

    void showFragment(Fragment fragment)
    {
        llWithDraw.setVisibility(View.GONE);
        fragmentManager = getChildFragmentManager();


        transaction = fragmentManager.beginTransaction();

        transaction.replace(R.id.container, fragment, "WalletFragment").commit();
    }

    private void sendRequest()
    {
        mainView.showLoading();
        WithdrawWalletRequest request = new WithdrawWalletRequest();
        request.setAmount(Integer.parseInt(edtCurrency.getText().toString().replaceAll(",", "")));
        request.setSheba_number(edtShabaNum.getText().toString().replaceAll("-", "").replaceAll(" ", ""));
        SingletonService.getInstance().withdrawWalletService().WithdrawWalletService(new OnServiceStatus<WebServiceClass<WithdrawWalletResponse>>()
        {


            @Override
            public void onReady(WebServiceClass<WithdrawWalletResponse> response)
            {

                try
                {
                    mainView.hideLoading();

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
                mainView.hideLoading();

                if (Tools.isNetworkAvailable(Objects.requireNonNull(getActivity())))
                {
                    mainView.showError(message);


                } else
                {
                    mainView.showError(getString(R.string.networkErrorMessage));

                }


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
