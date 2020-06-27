package com.traap.traapapp.ui.fragments.paymentWithoutCard;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.jakewharton.rxbinding3.widget.RxTextView;
import com.jakewharton.rxbinding3.widget.TextViewAfterTextChangeEvent;
import com.pixplicity.easyprefs.library.Prefs;
import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.getDecQrCode.DecryptQrRequest;
import com.traap.traapapp.apiServices.model.getDecQrCode.DecryptQrResponse;
import com.traap.traapapp.apiServices.model.paymentPrintPos.PaymentPrintPosRequest;
import com.traap.traapapp.apiServices.model.paymentPrintPos.PaymentPrintPosResponse;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.enums.BarcodeType;
import com.traap.traapapp.models.otherModels.headerModel.HeaderModel;
import com.traap.traapapp.models.otherModels.qrModel.QrModel;
import com.traap.traapapp.singleton.SingletonContext;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.dialogs.PaymentResultDialog;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.ui.fragments.simcardCharge.OnClickContinueSelectPayment;
import com.traap.traapapp.utilities.ConvertPersianNumberToString;
import com.traap.traapapp.utilities.Logger;
import com.traap.traapapp.utilities.NumberTextWatcher;
import com.traap.traapapp.utilities.Tools;
import com.traap.traapapp.utilities.Utility;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import br.com.simplepass.loading_button_lib.interfaces.OnAnimationEndListener;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;


@SuppressLint("ValidFragment")
public class PaymentWithoutCardFragment extends BaseFragment implements OnAnimationEndListener,
        View.OnClickListener, OnServiceStatus<WebServiceClass<PaymentPrintPosResponse>>,
        OnClickContinueSelectPayment
{
    private LinearLayout llBarcode, llList;
    private View rootView;
    private CircularProgressButton btnConfirm;
    private EditText edtPrice, edtQR;

    private MainActionView mainView;

    private Toolbar mToolbar;
    private TextView tvTitle, tvUserName,tvPopularPlayer, tvPriceSequence;
    private View imgBack, imgMenu;
    private QrModel model;

//    private String cardNumber, cardName, barcode, qrCode, cardImage;

    public PaymentWithoutCardFragment()
    {

    }

    public static PaymentWithoutCardFragment newInstance(MainActionView mainView, @Nullable QrModel model)
    {
        PaymentWithoutCardFragment f = new PaymentWithoutCardFragment();
        Bundle arg = new Bundle();
        arg.putParcelable("QrModel", model);
        f.setArguments(arg);

        f.setMainView(mainView);
        return f;
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
            model = getArguments().getParcelable("QrModel");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_payment_without_card, container, false);

        initView();

        EventBus.getDefault().register(this);
        return rootView;
    }

    public void initView()
    {
        try
        {
            edtPrice = rootView.findViewById(R.id.edtPrice);
            edtQR = rootView.findViewById(R.id.edtQR);
            llBarcode = rootView.findViewById(R.id.llBarcode);

            llList = rootView.findViewById(R.id.llList);
//            edtPrice.addTextChangedListener(new NumberTextWatcher(edtPrice));
            edtPrice.addTextChangedListener(new TextWatcher()
            {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) { }

                @Override
                public void afterTextChanged(Editable ss)
                {
                    edtPrice.removeTextChangedListener(this);
                    String s = edtPrice.getText().toString();
                    s = s.replace(",", "");
                    if (s.length() > 0)
                    {
                        DecimalFormat sdd = new DecimalFormat("#,###");
                        Double doubleNumber = Double.parseDouble(s);

                        String format = sdd.format(doubleNumber);
                        edtPrice.setText(format);
                        edtPrice.setSelection(format.length());
                    }

                    if (edtPrice.getText().toString().isEmpty())
                    {
                        tvPriceSequence.setVisibility(View.GONE);
                    }
                    else
                    {
                        tvPriceSequence.setVisibility(View.VISIBLE);
                        tvPriceSequence.setText(ConvertPersianNumberToString.getNumberConvertToString(
                                BigDecimal.valueOf(Integer.parseInt(edtPrice.getText().toString().replace(",", ""))), "ریال")
                                .replace("  ", " "));
                    }
                    edtPrice.addTextChangedListener(this);
                }
            });

            if (model != null)
            {
                edtQR.setText(model.getMerchantId());
                edtPrice.setText(Utility.priceFormat(model.getPrice()));
                tvPriceSequence.setText(ConvertPersianNumberToString.getNumberConvertToString(BigDecimal.valueOf(model.getPrice()), "ریال"));
            }

            btnConfirm = rootView.findViewById(R.id.btnConfirm);

            btnConfirm.setText("ادامه");
            btnConfirm.setOnClickListener(this);
            llBarcode.setOnClickListener(this);
            llList.setOnClickListener(this);
            mToolbar = rootView.findViewById(R.id.toolbar);

            mToolbar.findViewById(R.id.imgMenu).setOnClickListener(v -> mainView.openDrawer());
            tvUserName = mToolbar.findViewById(R.id.tvUserName);
            tvUserName.setText(TrapConfig.HEADER_USER_NAME);

            tvTitle = rootView.findViewById(R.id.tvTitle);
            tvTitle.setText("پرداخت بدون کارت");
            tvUserName = rootView.findViewById(R.id.tvUserName);
            tvUserName.setText(TrapConfig.HEADER_USER_NAME);
            imgMenu = rootView.findViewById(R.id.imgMenu);

            tvPriceSequence = rootView.findViewById(R.id.tvPriceSequence);

            imgMenu.setOnClickListener(v -> mainView.openDrawer());
            imgBack = rootView.findViewById(R.id.imgBack);
            imgBack.setOnClickListener(v -> getActivity().onBackPressed());
            tvPopularPlayer = mToolbar.findViewById(R.id.tvPopularPlayer);
            tvPopularPlayer.setText(Prefs.getInt("PopularPlayer", 12));

        }
        catch (Exception e)
        {
            e.getMessage();
        }
    }

    @Override
    public void onAnimationEnd()
    {
        btnConfirm.setBackground(ContextCompat.getDrawable(SingletonContext.getInstance().getContext(), R.drawable.background_button_login));
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.btnConfirm:
                // mainView.message("بزودی ...");

                if (TextUtils.isEmpty(edtQR.getText().toString()))
                {
                    mainView.showError("لطفا کد را وارد نمایید.");
                    return;
                }
                if (TextUtils.isEmpty(edtPrice.getText().toString()))
                {
                    mainView.showError("لطفا مبلغ را وارد نمایید.");
                    return;
                }
                mainView.onPaymentWithoutCard(this,
                        "",
                        0,
                        "پرداخت بدون کارت",
                        model.getPrice().toString(),
                        null,
                        model.getMerchantId(),
                        TrapConfig.PAYMENT_STATUS_PAYMENT_WITHOUT_CARD
                );
                break;

            case R.id.llBarcode:
            {
                try
                {
                    new TedPermission(getContext())
                            .setPermissionListener(new PermissionListener()
                            {
                                @Override
                                public void onPermissionGranted()
                                {
                                    mainView.openBarcode(BarcodeType.Payment);
                                }

                                @Override
                                public void onPermissionDenied(ArrayList<String> deniedPermissions)
                                {

                                }
                            })
                            .setDeniedMessage("لطفا جهت خواند بارکد، مجوز دسترسی به دوربین دستگاه خود را صادر نمایید.")
                            .setPermissions(Manifest.permission.CAMERA)
                            .check();
                }
                catch (Exception e)
                {
                    e.getMessage();
                }
                break;
            }
            case R.id.llList:
            {
                if (!Utility.isNetworkAvailable())
                {
//                    mainView.onInternetAlert();
                    return;
                }
                //  getActivity().startActivityForResult(new Intent(getActivity(), MapActivity.class).putExtra("isSelect", true), 9090);
                break;
            }
        }

    }

    public void backToHome()
    {
//        continue_ = false;
//        edtQR.setEnabled(true);
//        llDetailPayment.setVisibility(View.VISIBLE);
//        YoYo.with(Techniques.SlideInLeft)
//                .duration(200)
//                .playOn(llDetailPayment);
//        btnConfirm.revertAnimation(PaymentWithoutCardFragment.this);
//        btnConfirm.setClickable(true);
//        llPayment.setVisibility(View.VISIBLE);
//        edtQR.setText("");
//        Prefs.putString("qrCode", "");
//        edtPrice.setText("");
//        etLayoutCode.setHint("کد پذیرنده");

    }

    public void requestPayment()
    {
        if (!Utility.isNetworkAvailable())
        {
            mainView.onInternetAlert();
            return;
        }
//        btnPaymentConfirm.startAnimation();
//        btnPaymentConfirm.setClickable(false);
        PaymentPrintPosRequest request = new PaymentPrintPosRequest();
        request.setAmount(Integer.valueOf(edtPrice.getText().toString().replaceAll(",", "")));
//        request.setPin2(etPassPayment.getText().toString());
        //  request.setDeviceId(qrCode);
        //   request.setUserId(Prefs.getInt("userId", 0));
//        request.setCardId(Integer.parseInt(cardNumber));
//        request.setCvv2(cvv2);
//        request.setIsPrintPos(cbPrint.isChecked());
//        request.setExpDate(archiveCardDBModels.getExpireYear() + archiveCardDBModels.getExpireMonth());
        SingletonService.getInstance().getMerchantService().PaymentPrintPosService(this, request);


    }


    /*@Override
    public void barcode(String barcode)
    {
        this.barcode = barcode;
        //etQR.setText(barcode);
        if (v == null)
        {
            new Handler().postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    llPayment.setVisibility(View.GONE);
                    llDetailPayment.setVisibility(View.VISIBLE);
                    decryptBarcode(barcode);
                }
            }, 500);
        } else
        {

            llPayment.setVisibility(View.GONE);
            llDetailPayment.setVisibility(View.VISIBLE);
            decryptBarcode(barcode);

        }


    }
*/
    public void decryptBarcode(String barcode)
    {
        btnConfirm.startAnimation();
        btnConfirm.setClickable(false);

        //request.setDecryptQr(barcode);
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
                            btnConfirm.revertAnimation(PaymentWithoutCardFragment.this);
                            btnConfirm.setClickable(true);
                        }
                    }, 200);
                    if (decryptQrResponse.info.statusCode == 200)
                    {
                        if (decryptQrResponse.data.getIsPrintPos())
                        {
//                            llCheckBox.setVisibility(View.VISIBLE);
                        } else
                        {
//                            llCheckBox.setVisibility(View.GONE);

                        }
//                        continue_ = true;
                        btnConfirm.setText("ادامه");

//                        etLayoutCode.setHint("نام پذیرنده");
                        edtQR.setText(decryptQrResponse.data.getMerchantName());
                        edtPrice.setText(decryptQrResponse.data.getAmount());
                        edtQR.setEnabled(false);
//                        qrCode = decryptQrResponse.data.getSerialNumberPos();//DeviceId();

                        if (decryptQrResponse.data.getAmount().equals("0") || TextUtils.isEmpty(decryptQrResponse.data.getAmount()))
                        {
                            showKeybord(edtPrice);
                            edtPrice.setText("");
                            edtPrice.setEnabled(true);


                        } else
                        {
                            edtPrice.setText(decryptQrResponse.data.getAmount());
                            edtPrice.setEnabled(false);

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

                if (Tools.isNetworkAvailable(Objects.requireNonNull(getActivity())))
                {
                    mainView.showError("خطای ارتباط با سرور!");

                }
                else
                {
                    mainView.showError(getString(R.string.networkErrorMessage));

                }
/*                llPayment.setVisibility(View.VISIBLE);
                llDetailPayment.setVisibility(View.GONE);*/
                backToHome();


                new Handler().postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        btnConfirm.revertAnimation(PaymentWithoutCardFragment.this);
                        btnConfirm.setClickable(true);
                    }
                }, 200);

            }
        }, request);


    }


    @Override
    public void onReady(WebServiceClass<PaymentPrintPosResponse> posResponse)
    {

        try
        {
            if (posResponse.info.statusCode == 200)
            {
//                PaymentResultDialog dialog = new PaymentResultDialog(getActivity(), posResponse, cardNumber, cardName, edtPrice.getText().toString(), cardImage,
//                        archiveCardDBModels.getCardNumberColor());
//                dialog.show(getActivity().getSupportFragmentManager(), "payment");
            }
            else
            {
                mainView.showError(posResponse.info.message);
            }

        }
        catch (Exception e)
        {
            btnConfirm.revertAnimation(PaymentWithoutCardFragment.this);
            btnConfirm.setClickable(true);
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

    public void showKeybord(final EditText ettext)
    {
        ettext.requestFocus();
        ettext.postDelayed(() ->
                {
                    InputMethodManager keyboard = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    keyboard.showSoftInput(ettext, 0);
                }
                , 200);
    }

    @Subscribe
    public void getHeaderContent(HeaderModel headerModel)
    {
        if (headerModel.getPopularNo() != 0)
        {
            tvPopularPlayer.setText(String.valueOf(headerModel.getPopularNo()));
        }
        tvUserName.setText(TrapConfig.HEADER_USER_NAME);
    }


    @Override
    public void onDestroy()
    {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
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


    /* public void getMerchantByCode(String code)
    {
        btnConfirm.startAnimation();
        btnConfirm.setClickable(false);
        GetMerchantByCodeRequest request= new GetMerchantByCodeRequest();
        request.setCode(code);
        request.setUserId(Prefs.getInt("userId", 0)+"");
        SingletonService.getInstance().getMerchantService().getMerchantByCode(new OnServiceStatus<GetMerchantByCodeResponse>()
        {
            @Override
            public void onReady(GetMerchantByCodeResponse response)
            {
                btnConfirm.revertAnimation(PaymentWithoutCardFragment.this);
                btnConfirm.setClickable(true);
                try
                {

                    if (response.getServiceMessage().getCode() == 200)
                    {
                        if (response.getIsPrintPos())
                        {
                            llCheckBox.setVisibility(View.VISIBLE);
                        } else
                        {
                            llCheckBox.setVisibility(View.GONE);

                        }
                        continue_ = true;
                        btnConfirm.setText("ادامه");

                        etLayoutCode.setHint("نام پذیرنده");
                        etQR.setText(response.getMerchantName());
                        // etQR.setEnabled(false);
                        qrCode = response.getDeviceId();
                        YoYo.with(Techniques.SlideInLeft)
                                .duration(300)
                                .playOn(etLayoutCode);

                    } else
                    {
                        mainView.showErrorMessage(response.getServiceMessage().getMessage(), this.getClass().getSimpleName(), DibaConfig.showClassNameInMessage);

                    }

                } catch (Exception e)
                {

                    mainView.showErrorMessage(e.getMessage(), this.getClass().getSimpleName(), DibaConfig.showClassNameInException);

                }

            }

            @Override
            public void showErrorMessage(String message)
            {
                btnConfirm.revertAnimation(PaymentWithoutCardFragment.this);
                btnConfirm.setClickable(true);
                mainView.showErrorMessage(message, this.getClass().getSimpleName(), DibaConfig.showClassNameInException);

            }
        }, request);

    }*/
}
