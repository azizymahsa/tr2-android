package com.traap.traapapp.ui.fragments.gateWay;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.core.view.ViewCompat;

import com.google.android.material.textfield.TextInputLayout;
import com.pixplicity.easyprefs.library.Prefs;
import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.contact.OnSelectContact;
import com.traap.traapapp.apiServices.model.doTransfer.DoTransferWalletRequest;
import com.traap.traapapp.apiServices.model.doTransfer.DoTransferWalletResponse;
import com.traap.traapapp.apiServices.model.getBalancePasswordLess.GetBalancePasswordLessRequest;
import com.traap.traapapp.apiServices.model.getInfoWallet.GetInfoWalletResponse;
import com.traap.traapapp.apiServices.model.increaseWallet.RequestIncreaseWallet;
import com.traap.traapapp.apiServices.model.increaseWallet.ResponseIncreaseWallet;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.models.otherModels.headerModel.HeaderModel;
import com.traap.traapapp.ui.activities.paymentResult.PaymentResultIncreaseInventoryActivity;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.dialogs.MessageAlertDialog;
import com.traap.traapapp.ui.dialogs.MoneyTransferAlertDialog;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.utilities.ClearableEditText;
import com.traap.traapapp.utilities.ConvertPersianNumberToString;
import com.traap.traapapp.utilities.ScreenShot;
import com.traap.traapapp.utilities.Tools;
import com.traap.traapapp.utilities.Utility;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Objects;

/**
 * Created by MahsaAzizi on 28/12/2019.
 */
public class MoneyTransferFragment extends BaseFragment implements View.OnClickListener
{


    private View rootView, btnConfirm, llCardNumber, llPhoneNumber, llUserCode;
    private MainActionView mainView;
    private ClearableEditText etCardNumber, etPhoneNum, etUserCode, etAmount;
    private EditText etPass;
    private TextView txtChrAmount;
    private ImageView ivContact, ivCreditCart;
    private Spinner spinnerType;
    private String[] ListType = {"کدمشتری", "شماره کارت", "شماره موبایل"};
    private String strType = "";
    private Integer type = 0;
    private Integer TYPE_USER_CODE = 0;
    private Integer TYPE_CARD_NUMBER = 1;
    private Integer TYPE_PHONE_NUMBER = 2;
    private ConvertPersianNumberToString convertPersianNumberToString;
    private String userName;
    private TextInputLayout inputLayout3;


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

        // WalletFragment.setTitleFragmentWallet("انتقال وجه");

        onGetBoutForSuccess();

        WalletTitle walletTitle = new WalletTitle();
        walletTitle.setTitle("انتقال وجه");

        EventBus.getDefault().post(walletTitle);

        etAmount.addTextChangedListener(new TextWatcher()
        {
            private String current = "";

            @Override
            public void afterTextChanged(Editable ss)
            {
                if (etAmount.getText().toString().replaceAll(",", "").equals(""))
                    etAmount.setText("0");

                if (etAmount.getText().toString().length() > 2)
                    txtChrAmount.setText(ConvertPersianNumberToString.getNumberConvertToString(BigDecimal.valueOf(Integer.parseInt(etAmount.getText().toString().replaceAll(",", ""))), "ریال"));

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
        return rootView;
    }

    @Override
    public void onStop()
    {
        super.onStop();
        WalletTitle walletTitle = new WalletTitle();
        walletTitle.setTitle("کیف پول");

        EventBus.getDefault().post(walletTitle);
    }

    private void initView()
    {


        inputLayout3 = rootView.findViewById(R.id.inputLayout3);

        ViewCompat.setLayoutDirection(inputLayout3, ViewCompat.LAYOUT_DIRECTION_RTL);
        convertPersianNumberToString = new ConvertPersianNumberToString();

        spinnerType = rootView.findViewById(R.id.spinnerType);
        ivContact = rootView.findViewById(R.id.ivContact);
        ivContact.setOnClickListener(this);
        ivCreditCart = rootView.findViewById(R.id.ivCreditCart);
        ivCreditCart.setOnClickListener(this);

        llCardNumber = rootView.findViewById(R.id.llCardNumber);
        llPhoneNumber = rootView.findViewById(R.id.llPhoneNumber);
        llUserCode = rootView.findViewById(R.id.llUserCode);
        etCardNumber = rootView.findViewById(R.id.etCardNumber);
        etPhoneNum = rootView.findViewById(R.id.etPhoneNum);
        etUserCode = rootView.findViewById(R.id.etUserCode);
        etAmount = rootView.findViewById(R.id.etAmount);
        txtChrAmount = rootView.findViewById(R.id.txtChrAmount);
        etPass = rootView.findViewById(R.id.etPass);

        InputFilter[] filterPass = new InputFilter[1];
        filterPass[0] = new InputFilter.LengthFilter(12);
        etPass.setFilters(filterPass);

        InputFilter[] filterArray = new InputFilter[1];
        filterArray[0] = new InputFilter.LengthFilter(19);
        etCardNumber.setFilters(filterArray);

        InputFilter[] filterNumber = new InputFilter[1];
        filterNumber[0] = new InputFilter.LengthFilter(11);
        etPhoneNum.setFilters(filterNumber);

        InputFilter[] filterAmount = new InputFilter[1];
        filterAmount[0] = new InputFilter.LengthFilter(14);
        etAmount.setFilters(filterAmount);


        InputFilter[] filterUserCode = new InputFilter[1];
        filterUserCode[0] = new InputFilter.LengthFilter(16);
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

        etAmount.addTextChangedListener(new TextWatcher()
        {
            private String current = "";

            @Override
            public void afterTextChanged(Editable ss)
            {
                etAmount.removeTextChangedListener(this);

                String s = etAmount.getText().toString();

                s = s.replace(",", "");
                if (s.length() > 0)
                {
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

        if (position == TYPE_USER_CODE)
        {

            type = TYPE_USER_CODE;
            llCardNumber.setVisibility(View.GONE);
            ivCreditCart.setVisibility(View.GONE);
            ivContact.setVisibility(View.GONE);
            llPhoneNumber.setVisibility(View.GONE);
            llUserCode.setVisibility(View.VISIBLE);


        } else if (position == TYPE_CARD_NUMBER)
        {

            type = TYPE_CARD_NUMBER;
            llCardNumber.setVisibility(View.VISIBLE);
            ivCreditCart.setVisibility(View.VISIBLE);
            ivContact.setVisibility(View.GONE);
            llPhoneNumber.setVisibility(View.GONE);
            llUserCode.setVisibility(View.GONE);

        } else if (position == TYPE_PHONE_NUMBER)
        {

            type = TYPE_PHONE_NUMBER;
            llCardNumber.setVisibility(View.GONE);
            ivCreditCart.setVisibility(View.GONE);
            ivContact.setVisibility(View.VISIBLE);
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
                // new ScreenShot(v, getActivity(),true,"برای ذخیره تصویر رسید، اخذ این مجوز الزامی است.");

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
        if (type.equals(TYPE_PHONE_NUMBER))
        {
            try
            {
                if (!Utility.getMobileValidation(etPhoneNum.getText().toString()))
                {

                    showAlertFailure(getContext(), "لطفا شماره تلفن همراه را صحیح وارد نمایید.", "", false);

                    return;
                }
            } catch (Exception e)
            {
                showAlertFailure(getContext(), "لطفا شماره تلفن همراه را صحیح وارد نمایید.", "", false);

                return;
            }
            userName = etPhoneNum.getText().toString();


        } else if (type.equals(TYPE_USER_CODE))
        {

            if (TextUtils.isEmpty(etUserCode.getText().toString()))
            {
                showAlertFailure(getContext(), "لطفا کد مشتری را وارد نمایید.", "", false);

                return;
            }
            if (etUserCode.getText().length() < 4)
            {
                showAlertFailure(getContext(), "لطفا کد مشتری را صحیح وارد نمایید.(حداقل 4 رقم)", "", false);

                return;
            }

            userName = etUserCode.getText().toString();


        } else if (type == TYPE_CARD_NUMBER)
        {

            if (TextUtils.isEmpty(etCardNumber.getText().toString()))
            {
                showAlertFailure(getContext(), "لطفا شماره کارت را وارد نمایید.", "", false);
                return;
            }

            userName = etCardNumber.getText().toString().replaceAll("-", "");


        }

        if (TextUtils.isEmpty(etAmount.getText().toString()))
        {
            showAlertFailure(getContext(), "لطفا مبلغ را وارد نمایید.", "", false);
            return;
        }
        if (etAmount.getText().toString().length()>1 & Integer.parseInt(etAmount.getText().toString().replaceAll(",",""))<20000)
        {
            showAlertFailure(getContext(), "حداقل مبلغ 20,000 ریال می باشد.", "", false);
            return;
        }

        if (TextUtils.isEmpty(etPass.getText().toString()))
        {
            showAlertFailure(getContext(), "لطفا رمز را وارد نمایید.", "", false);

            return;
        }
        if (etPass.getText().toString().length() < 4 || etPass.getText().toString().length() > 12)
        {
            showAlertFailure(getContext(), "رمز وارد شده حداقل باید 4 کارکتر باشد.", "", false);

            return;
        }
        if (Prefs.getString("W_CustomerCode", "").equals(etUserCode.getText().toString()))
        {
            showAlertFailure(getContext(), "کد مشتری مبدا و مقصد یکسان می باشد.", "", false);

            return;
        }
        if (Prefs.getString("W_CardNo", "").equals(etCardNumber.getText().toString().replaceAll("-", "")))
        {
            showAlertFailure(getContext(), "شماره کارت مبدا و مقصد یکسان می باشد.", "", false);

            return;
        }
        if (Prefs.getString("W_Phone", "").equals(etPhoneNum.getText().toString()))
        {
            showAlertFailure(getContext(), "شماره موبایل مبدا و مقصد یکسان می باشد.", "", false);

            return;
        }
        requestGetInfo();


       // this.startActivity(intent);
    }

    private void showDialogGetInfo(GetInfoWalletResponse data)
    {
        String txtAmountDigit = " مبلغ: " + etAmount.getText().toString() + " ریال ";
        String txtAmountChar = "(" + convertPersianNumberToString.getNumberConvertToString(BigDecimal.valueOf(Integer.parseInt(etAmount.getText().toString().replaceAll(",", ""))), "ریال") + ")";
        String txtUserName = data.getFull_name();
        String txtName = "";

        MoneyTransferAlertDialog dialog = new MoneyTransferAlertDialog(getActivity(), "تایید انتقال وجه از کیف پول", "از : کارت کیف پول " + TrapConfig.HEADER_USER_NAME, true,
                new MoneyTransferAlertDialog.OnConfirmListener()
                {
                    @Override
                    public void onConfirmClick()
                    {
                        requestDoTransfer(data.getCard_no());

                    }

                    @Override
                    public void onCancelClick()
                    {
                        //mainView.backToMainFragment();
                    }
                }, txtAmountDigit, txtAmountChar, txtUserName, txtName);
        dialog.show((getActivity()).getFragmentManager(), "dialog");
    }

    private void requestDoTransfer(String card_no)
    {
        mainView.showLoading();
        DoTransferWalletRequest request = new DoTransferWalletRequest();
        request.setPin2(etPass.getText().toString());
        request.setAmount(Integer.valueOf(etAmount.getText().toString().replaceAll(",", "")));
        request.setTo(card_no);
        SingletonService.getInstance().doTransferWalletService().DoTransferWalletService(new OnServiceStatus<WebServiceClass<DoTransferWalletResponse>>()
        {


            @Override
            public void onReady(WebServiceClass<DoTransferWalletResponse> response)
            {

                try
                {
                    mainView.hideLoading();

                    if (response.info.statusCode == 200)
                    {

                        showResultPayment(response);

                    } else
                    {
                        showAlertFailure(getContext(), response.info.message, "", false);


                    }
                } catch (Exception e)
                {
                    mainView.hideLoading();
                    showAlertFailure(getContext(), e.getMessage(), "", false);


                }


            }

            @Override
            public void onError(String message)
            {

                mainView.hideLoading();

                if (Tools.isNetworkAvailable(Objects.requireNonNull(getActivity())))
                {
                    showAlertFailure(getContext(), message, "", false);


                } else
                {
                    showAlertFailure(getContext(), getString(R.string.networkErrorMessage), "", false);


                }

            }
        }, request);
    }

    private void showResultPayment(WebServiceClass<DoTransferWalletResponse> response)
    {
        Intent intent = new Intent(this.getContext(), PaymentResultIncreaseInventoryActivity.class);
        intent.putExtra("RefrenceNumber", response.data.getRefrenceNumber());
        //this.startActivity(intent);
        getActivity().startActivityForResult(intent,0);


    }

    private void requestGetInfo()
    {
        mainView.showLoading();
        GetBalancePasswordLessRequest request = new GetBalancePasswordLessRequest();
        request.setIsWallet(true);
        request.setUsername(userName);
        SingletonService.getInstance().getBalancePasswordLessService().GetInfoWalletService(new OnServiceStatus<WebServiceClass<GetInfoWalletResponse>>()
        {


            @Override
            public void onReady(WebServiceClass<GetInfoWalletResponse> response)
            {
                mainView.hideLoading();

                try
                {
                    if (response.info.statusCode == 200)
                    {
                        showDialogGetInfo(response.data);

                    } else
                    {
                        showAlertFailure(getContext(), response.info.message, "", false);


                    }
                } catch (Exception e)
                {

                    showAlertFailure(getContext(), e.getMessage(), "", false);


                }


            }

            @Override
            public void onError(String message)
            {

                mainView.hideLoading();

                if (Tools.isNetworkAvailable(Objects.requireNonNull(getActivity())))
                {
                    showAlertFailure(getContext(), message, "", false);


                } else
                {
                    showAlertFailure(getContext(), getString(R.string.networkErrorMessage), "", false);


                }


            }
        }, request);
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
