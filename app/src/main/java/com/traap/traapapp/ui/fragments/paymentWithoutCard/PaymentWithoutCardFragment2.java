package com.traap.traapapp.ui.fragments.paymentWithoutCard;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.material.textfield.TextInputLayout;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;
import java.util.Objects;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import br.com.simplepass.loading_button_lib.interfaces.OnAnimationEndListener;
import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.getDecQrCode.DecryptQrRequest;
import com.traap.traapapp.apiServices.model.getDecQrCode.DecryptQrResponse;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.enums.BarcodeType;
import com.traap.traapapp.singleton.SingletonContext;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.utilities.Tools;

public class PaymentWithoutCardFragment2 extends BaseFragment implements View.OnClickListener, OnAnimationEndListener
{

    private Toolbar mToolbar;

    private MainActionView mainView;
    private LinearLayout llQrScan, llPayment, llDetailPayment, llBtnConfirm, llPricePeyment, llBtnConfirm2,llCheckBox;
    private CircularProgressButton btnConfirm,btnPaymentConfirm,btnConfirm2;
    private EditText etAmountPayment, etQR, etPassPayment, etCvv2;
    TextInputLayout etLayoutCode,llPaymentPass;
    View rootView;
    private boolean isDetailPaymentList = false, isDetailPaymentBarcode = false;
    private boolean continue_ = false;
    private TextView tvUserName;

    public PaymentWithoutCardFragment2()
    {
    }

    private void initView()
    {
        tvUserName = mToolbar.findViewById(R.id.tvUserName);

        tvUserName.setText(TrapConfig.HEADER_USER_NAME);

        llQrScan = rootView.findViewById(R.id.llQrScan);
        llDetailPayment = rootView.findViewById(R.id.llDetailPayment);
        llPayment = rootView.findViewById(R.id.llPayment);
        llBtnConfirm = rootView.findViewById(R.id.llBtnConfirm);
        btnConfirm = rootView.findViewById(R.id.btnConfirm);
        btnConfirm2 = rootView.findViewById(R.id.btnConfirm2);
        etAmountPayment = rootView.findViewById(R.id.etAmountPayment);
        etQR = rootView.findViewById(R.id.etQR);
        etLayoutCode = rootView.findViewById(R.id.etLayoutCode);
        llPricePeyment = rootView.findViewById(R.id.llPricePeyment);
        //  llPaymentPass = rootView.findViewById(R.id.llPaymentPass);
        //  llPaymentPass.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/iran_sans_normal.ttf"));
        llBtnConfirm2 = rootView.findViewById(R.id.llBtnConfirm2);
        llCheckBox = rootView.findViewById(R.id.llCheckBox);
        btnPaymentConfirm = rootView.findViewById(R.id.btnPaymentConfirm);

        llQrScan.setOnClickListener(this);
        btnConfirm2.setOnClickListener(this);
        btnConfirm.setOnClickListener(this);
        btnConfirm.setText("ادامه");
        btnPaymentConfirm.setText("تایید نهایی");
        // etPassPayment = rootView.findViewById(R.id.etPassPayment);

       /* btnBackToDetail.setText("بازگشت");
        btnBackToHome.setText("بازگشت");*/
        btnConfirm.setOnClickListener(this);
        // btnBackToDetail.setOnClickListener(this);
        btnPaymentConfirm.setOnClickListener(this);
       /* btnBackToHome.setOnClickListener(this);
        llBarcode.setOnClickListener(this);
        llList.setOnClickListener(this);
        if (!cardNumberCheck.equals("003725"))
        {
            llCvv2.setVisibility(View.VISIBLE);

        }*/
    }

    public static PaymentWithoutCardFragment2 newInstance(MainActionView mainActionView)
    {
        PaymentWithoutCardFragment2 fragment = new PaymentWithoutCardFragment2();
        fragment.setMainView(mainActionView);

        Bundle args = new Bundle();

        fragment.setArguments(args);


        return fragment;
    }

    private void setMainView(MainActionView mainView)
    {
        this.mainView = mainView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {

        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        if (rootView != null)
        {
            if (isDetailPaymentList)
            {
                isDetailPaymentList = false;
                return rootView;

            } else
            {
                if (isDetailPaymentBarcode)
                {
                    isDetailPaymentBarcode = false;
                    return rootView;

                } else
                    rootView = null;

            }

        }
        rootView = inflater.inflate(R.layout.fragment_payment_without_card, container, false);

        mToolbar = rootView.findViewById(R.id.toolbar);
        initView();

        mToolbar.findViewById(R.id.imgMenu).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mainView.openDrawer();
            }
        });
        if (Prefs.getString("qrCode", "").length() > 5)
        {
            DecryptQrRequest request = new DecryptQrRequest();
            request.setDecryptQr(Prefs.getString("qrCode", ""));
            SingletonService.getInstance().getMerchantService().GetdecryptQrService(new OnServiceStatus<WebServiceClass<DecryptQrResponse>>()
            {
                @Override
                public void onReady(WebServiceClass<DecryptQrResponse> decryptQrResponse)
                {


                    try
                    {
                        new Handler().postDelayed(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                btnConfirm.revertAnimation(PaymentWithoutCardFragment2.this);
                                btnConfirm.setClickable(true);
                            }
                        }, 200);

                        if (decryptQrResponse.info.statusCode == 200)
                        {
                            if (decryptQrResponse.data.getIsPrintPos())
                            {
                                llCheckBox.setVisibility(View.VISIBLE);
                            } else
                            {
                                llCheckBox.setVisibility(View.GONE);

                            }
                            continue_ = true;
                            btnConfirm.setText("ادامه");
                            llBtnConfirm2.setVisibility(View.VISIBLE);
                            llBtnConfirm.setVisibility(View.GONE);

                            etLayoutCode.setHint("نام پذیرنده");
                            etQR.setText(decryptQrResponse.data.getMerchantName());
                            etAmountPayment.setText(decryptQrResponse.data.getAmount());
                            etQR.setEnabled(false);
                            // qrCode = decryptQrResponse.data.getDeviceId();

                            if (decryptQrResponse.data.getAmount().equals("0") || TextUtils.isEmpty(decryptQrResponse.data.getAmount()))
                            {
                                showKeybord(etAmountPayment);
                                etAmountPayment.setText("");
                                etAmountPayment.setEnabled(true);


                            } else
                            {
                                etAmountPayment.setText(decryptQrResponse.data.getAmount());
                                etAmountPayment.setEnabled(false);

                            }

                        } else
                        {
                            backToHome();
                            mainView.showError(decryptQrResponse.info.message);

                        }
                    } catch (Exception e)
                    {
                        mainView.showError(e.getMessage());
                  /*  llPayment.setVisibility(View.VISIBLE);
                    llDetailPayment.setVisibility(View.GONE);*/
                        backToHome();

                    }


                }

                @Override
                public void onError(String message)
                {
//...
/*                llPayment.setVisibility(View.VISIBLE);
                llDetailPayment.setVisibility(View.GONE);*/
                    backToHome();


                    new Handler().postDelayed(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            btnConfirm.revertAnimation(PaymentWithoutCardFragment2.this);
                            btnConfirm.setClickable(true);
                        }
                    }, 200);
                    if (Tools.isNetworkAvailable(Objects.requireNonNull(getActivity())))
                    {
                        mainView.showError(message);
                    }
                    else
                    {

                        showAlert(getActivity(), R.string.networkErrorMessage, R.string.networkError);
                    }


                }
            }, request);

        }

        return rootView;
    }
    public void showKeybord(final EditText ettext)
    {
        ettext.requestFocus();
        ettext.postDelayed(() -> {
                    InputMethodManager keyboard = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    keyboard.showSoftInput(ettext, 0);
                }
                , 200);
    }
    public void backToHome()
    {
        continue_ = false;
        etQR.setEnabled(true);
        llPricePeyment.setVisibility(View.GONE);
        llDetailPayment.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.SlideInLeft)
                .duration(200)
                .playOn(llDetailPayment);
        btnConfirm.revertAnimation(PaymentWithoutCardFragment2.this);
        btnConfirm.setClickable(true);
        llPayment.setVisibility(View.VISIBLE);
        llBtnConfirm2.setVisibility(View.GONE);
        llBtnConfirm.setVisibility(View.VISIBLE);
        etQR.setText("");
        etAmountPayment.setText("");
        etLayoutCode.setHint("کد پذیرنده");

    }


    public void openBarcode(BarcodeType bill)
    {
        new TedPermission(getContext())
                .setPermissionListener(new PermissionListener()
                {
                    @Override
                    public void onPermissionGranted()
                    {
                        onBarcodReader();
                    }

                    @Override
                    public void onPermissionDenied(ArrayList<String> deniedPermissions)
                    {

                    }
                })
                .setDeniedMessage("If you reject permission,you can not use this application, Please turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.CAMERA)
                .check();

    }

    public void onBarcodReader()
    {
        // changeFragment(fragments.get(13), "13");
        Prefs.putString("qrCode", "");
        mainView.onBarcodReader();
        //    mFragNavController.switchTab(13);
       /* new Handler().postDelayed(() -> {
            layoutBehavior();
            appBar.setExpanded(false, true);

        }, 200);
        presenter.barcodeType(barcodeType);*/
        Log.d("barcode:", Prefs.getString("qrCode", ""));
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.btnPaymentConfirm:

                if (TextUtils.isEmpty(etPassPayment.getText().toString()))
                {
                    mainView.showError("لطفارمز دوم خود را وارد نمایید.");
                    return;
                }
                /*if (!cardNumberCheck.equals("003725"))
                    if (TextUtils.isEmpty(etCvv2.getText().toString()))
                    {
                        mainView.showError("لطفا شماره cvv2 کارت خود را وارد نمایید.");
                        return;
                    }
                cvv2 = etCvv2.getText().toString();
                requestPayment();*/

                break;
            case R.id.llQrScan:
                openBarcode(BarcodeType.Payment);
                // isDetailPaymentBarcode = true;


                break;
            case R.id.btnConfirm2:
                // mainView.message("بزودی ...");

                if (TextUtils.isEmpty(etQR.getText().toString()))
                {
                    mainView.showError("لطفا کد را وارد نمایید.");
                    return;
                }
                if (TextUtils.isEmpty(etAmountPayment.getText().toString()))
                {
                    mainView.showError("لطفا مبلغ را وارد نمایید.");
                    return;
                }

                if (continue_)
                {
                    llDetailPayment.setVisibility(View.GONE);
                    llPricePeyment.setVisibility(View.VISIBLE);
                    YoYo.with(Techniques.SlideInRight)
                            .duration(200)
                            .playOn(llPricePeyment);
                    llPayment.setVisibility(View.GONE);
                } /*else
                    getMerchantByCode(etQR.getText().toString());*/


                break;
            case R.id.btnConfirm:

                if (TextUtils.isEmpty(etQR.getText().toString()))
                {
                    mainView.showError("لطفا کد را وارد نمایید.");
                    return;
                }
                if (TextUtils.isEmpty(etAmountPayment.getText().toString()))
                {
                    mainView.showError("لطفا مبلغ را وارد نمایید.");
                    return;
                }

                if (continue_)
                {
                    llDetailPayment.setVisibility(View.GONE);
                    llPricePeyment.setVisibility(View.VISIBLE);
                    YoYo.with(Techniques.SlideInRight)
                            .duration(200)
                            .playOn(llPricePeyment);
                    llPayment.setVisibility(View.GONE);
                } else
                {
                    //getMerchantByCode(etQR.getText().toString());
                }


                break;
        }
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
        if (isDetailPaymentList)
            isDetailPaymentList = false;

    }

    @Override
    public void onResume()
    {
        super.onResume();
        //  decryptBarcode(barcode);
        continue_ = false;
        //etPassPayment.setText("");
        etQR.setText("");
        btnConfirm.revertAnimation(PaymentWithoutCardFragment2.this);
        btnConfirm.setClickable(true);
        // etCvv2.setText("");
        etAmountPayment.setText("");




    }


    @Override
    public void onAnimationEnd()
    {
        btnPaymentConfirm.setBackground(ContextCompat.getDrawable(SingletonContext.getInstance().getContext(), R.drawable.background_border_red));
        btnConfirm.setBackground(ContextCompat.getDrawable(SingletonContext.getInstance().getContext(), R.drawable.background_border_red));
    }
}
