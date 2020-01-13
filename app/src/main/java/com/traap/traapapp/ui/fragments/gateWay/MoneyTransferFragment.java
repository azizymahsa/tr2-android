package com.traap.traapapp.ui.fragments.gateWay;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.contact.OnSelectContact;
import com.traap.traapapp.apiServices.model.increaseWallet.RequestIncreaseWallet;
import com.traap.traapapp.apiServices.model.increaseWallet.ResponseIncreaseWallet;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.models.otherModels.headerModel.HeaderModel;
import com.traap.traapapp.models.otherModels.paymentInstance.SimChargePaymentInstance;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.dialogs.MessageAlertDialog;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.utilities.ClearableEditText;
import com.traap.traapapp.utilities.Utility;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by MahsaAzizi on 28/12/2019.
 */
public class MoneyTransferFragment extends BaseFragment implements View.OnClickListener
{


    private View rootView, btnConfirm,llCardNumber,llPhoneNumber,llUserCode;
    private MainActionView mainView;
    private ClearableEditText etCardNumber,etPhoneNum,etUserCode,etAmount;
    private EditText etPass;
    private ImageView ivContact;
    private Spinner spinnerType;
    private String[] ListType = {"کدمشتری", "شماره کارت", "شماره موبایل"};
    private String strType="";
    private Integer type=0;
    private Integer TYPE_USER_CODE=0;
    private Integer TYPE_CARD_NUMBER=1;
    private Integer TYPE_PHONE_NUMBER=2;



    public MoneyTransferFragment()
    {

    }

    public static MoneyTransferFragment newInstance(MainActionView mainView)
    {
        MoneyTransferFragment f = new MoneyTransferFragment();
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
        rootView = inflater.inflate(R.layout.fragment_money_transfer, container, false);

        initView();


        onGetBoutForSuccess();


        return rootView;
    }

    private void initView()
    {

        spinnerType=rootView.findViewById(R.id.spinnerType);
        ivContact=rootView.findViewById(R.id.ivContact);
        ivContact.setOnClickListener(this);

        llCardNumber=rootView.findViewById(R.id.llCardNumber);
        llPhoneNumber=rootView.findViewById(R.id.llPhoneNumber);
        llUserCode=rootView.findViewById(R.id.llUserCode);
        etCardNumber = rootView.findViewById(R.id.etCardNumber);
        etPhoneNum=rootView.findViewById(R.id.etPhoneNum);
        etUserCode=rootView.findViewById(R.id.etUserCode);
        etAmount=rootView.findViewById(R.id.etAmount);
        etPass=rootView.findViewById(R.id.etPass);
        InputFilter[] filterArray = new InputFilter[1];
        filterArray[0] = new InputFilter.LengthFilter(19);
        etCardNumber.setFilters(filterArray);

        InputFilter[] filterNumber = new InputFilter[1];
        filterNumber[0] = new InputFilter.LengthFilter(11);
        etPhoneNum.setFilters(filterNumber);

        InputFilter[] filterUserCode = new InputFilter[1];
        filterUserCode[0] = new InputFilter.LengthFilter(5);
        etUserCode.setFilters(filterUserCode);

        btnConfirm = rootView.findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener(this);
        etCardNumber.addTextChangedListener(new TextWatcher()
        {
            int len = 0;

            @Override
            public void afterTextChanged(Editable s)
            {
                String str = etCardNumber.getText().toString();
                if (str.length() == 4 && len < str.length())
                {//len check for backspace
                    etCardNumber.append("-");
                }
                if (str.length() == 9 && len < str.length())
                {//len check for backspace
                    etCardNumber.append("-");
                }
                if (str.length() == 14 && len < str.length())
                {//len check for backspace
                    etCardNumber.append("-");
                }
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3)
            {

                String str = etCardNumber.getText().toString();
                len = str.length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
            }


        });
    }
    private void onGetBoutForSuccess()
    {
        ArrayAdapter<String> adapterAmount = new ArrayAdapter<String>(getActivity(),
                R.layout.simple_spinner_item, ListType);
        adapterAmount.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        spinnerType.setAdapter(adapterAmount);
        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {

                strType = adapterAmount.getItem(position);
                setTypeLayout(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
            }
        });
    }

    private void setTypeLayout(Integer position)
    {

        if (position==TYPE_USER_CODE){

            type=TYPE_USER_CODE;
            llCardNumber.setVisibility(View.GONE);
            llPhoneNumber.setVisibility(View.GONE);
            llUserCode.setVisibility(View.VISIBLE);


        }else if (position==TYPE_CARD_NUMBER){

            type=TYPE_CARD_NUMBER;
            llCardNumber.setVisibility(View.VISIBLE);
            llPhoneNumber.setVisibility(View.GONE);
            llUserCode.setVisibility(View.GONE);

        }else if (position==TYPE_PHONE_NUMBER){

            type=TYPE_PHONE_NUMBER;
            llCardNumber.setVisibility(View.GONE);
            llPhoneNumber.setVisibility(View.VISIBLE);
            llUserCode.setVisibility(View.GONE);


        }
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
            case R.id.btnConfirm:

                onConfirmClicked();
                break;
        /*case R.id.etAmount:
            // sendRequest();
            break;*/
        }
    }

    private void onConfirmClicked()
    {
        if (type==TYPE_PHONE_NUMBER){
            if (!Utility.getMobileValidation(etPhoneNum.getText().toString()))
            {
                mainView.showError("لطفا شماره تلفن همراه را صحیح وارد نمایید.");
                return;
            }

        }else if (type==TYPE_USER_CODE){

            if (TextUtils.isEmpty(etUserCode.getText().toString()))
            {
                mainView.showError("لطفا کد مشتری را وارد نمایید.");
                return;
            }
            if (etUserCode.getText().length() < 3){

                mainView.showError("لطفا کد مشتری را صحیح وارد نمایید.");
                return;
            }

        }else if (type==TYPE_CARD_NUMBER){

            if (TextUtils.isEmpty(etCardNumber.getText().toString()))
            {
                mainView.showError("لطفا شماره کارت را وارد نمایید.");
                return;
            }

        }

        if (TextUtils.isEmpty(etAmount.getText().toString()))
        {
            mainView.showError("لطفا مبلغ را وارد نمایید.");
            return;
        }

        if (TextUtils.isEmpty(etPass.getText().toString())){
            mainView.showError("لطفا رمز را وارد نمایید.");
            return;
        }

        showDialogGetInfo();

    }

    private void showDialogGetInfo()
    {
        MessageAlertDialog dialog = new MessageAlertDialog(getActivity(), "تایید انتقال وجه از کیف پول", "از : کارت کیف پول ...", false,
                new MessageAlertDialog.OnConfirmListener() {
                    @Override
                    public void onConfirmClick() {

                        showPaymentFragment();
                    }

                    @Override
                    public void onCancelClick() {

                    }
                });

        dialog.setCancelable(false);
        dialog.show(getActivity().getFragmentManager(), "messageDialog");
    }

    private void showPaymentFragment()
    {
        SimChargePaymentInstance paymentInstance = new SimChargePaymentInstance();
        paymentInstance.setPAYMENT_STATUS(TrapConfig.PAYMENT_STATUS_TRANSFER_MONEY);//13

        String title = "با انجام این پرداخت، مبلغ "+etAmount.getText().toString()+"ریال بابت \"انتقال وجه\"، از حساب شما کسر خواهد شد.";
        String mobile = "";
       /* mainView.openIncreaseWalletPaymentFragment(this, data.getUrl(), R.drawable.ic_inc_inv,
                title, etAmount.getText().toString(), paymentInstance, mobile, TrapConfig.PAYMENT_STATUS_INCREASE_WALLET);*/
    }

    private void sendRequest()
    {
        mainView.showLoading();
        RequestIncreaseWallet request = new RequestIncreaseWallet();
        request.setAmount(Integer.parseInt(etCardNumber.getText().toString()));
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

    public void onSelectContact(OnSelectContact onSelectContact)
    {
        try
        {
                etPhoneNum.setText(onSelectContact.getNumber().replaceAll(" ", ""));

        } catch (Exception e)
        {
        }
    }
}
