package com.traap.traapapp.ui.fragments.paymentWithoutCard;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.material.textfield.TextInputLayout;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.pixplicity.easyprefs.library.Prefs;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import br.com.simplepass.loading_button_lib.interfaces.OnAnimationEndListener;
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
import com.traap.traapapp.models.dbModels.ArchiveCardDBModel;
import com.traap.traapapp.models.otherModels.headerModel.HeaderModel;
import com.traap.traapapp.singleton.SingletonContext;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.dialogs.PaymentResultDialog;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.utilities.NumberTextWatcher;
import com.traap.traapapp.utilities.Tools;
import com.traap.traapapp.utilities.Utility;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Objects;


@SuppressLint("ValidFragment")
public class PaymentWithoutCardFragment_old extends BaseFragment implements OnAnimationEndListener,
        View.OnClickListener, OnServiceStatus<WebServiceClass<PaymentPrintPosResponse>>
{
    private View v;
    private CircularProgressButton btnConfirm, btnPaymentConfirm;
    private TextView btnBackToDetail;
    private LinearLayout llDetailPayment, llPricePeyment, llPayment, llCvv2;
    private EditText etAmountPayment, etQR, etPassPayment, etCvv2;
    private LinearLayout llBarcode, llList;
    private TextInputLayout llPaymentPass, tipCvv2, etLayoutCode;
    private String cardNumber, cardName, barcode, qrCode, cardImage;
    private boolean isDetailPaymentList = false, isDetailPaymentBarcode = false;
    private ArchiveCardDBModel archiveCardDBModels;
    private String cvv2 = "";
    private String cardNumberCheck = "";
    private boolean continue_ = false;
    private CheckBox cbPrint;
    private LinearLayout llCheckBox;
    private MainActionView mainView;

    private Toolbar mToolbar;
    private TextView tvTitle, tvUserName,tvPopularPlayer;
    private View imgBack, imgMenu;

    public PaymentWithoutCardFragment_old()
    {

    }

    public static PaymentWithoutCardFragment_old newInstance(MainActionView mainView)
    {
        PaymentWithoutCardFragment_old f = new PaymentWithoutCardFragment_old();
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
//        if (v != null)
//        {
//            if (isDetailPaymentList)
//            {
//                isDetailPaymentList = false;
//                return v;
//
//            } else
//            {
//                if (isDetailPaymentBarcode)
//                {
//                    isDetailPaymentBarcode = false;
//                    return v;
//
//                } else
//                    v = null;
//
//            }
//
//        }

        v = inflater.inflate(R.layout.fragment_payment_without_card, container, false);
//        v = inflater.inflate(R.layout.fragment_traap_market, container, false);


        //--------------------------test--------------------

//        MessageAlertDialog dialog = new MessageAlertDialog(getActivity(), "", "این سرویس بزودی راه اندازی میگردد.", false,
//                MessageAlertDialog.TYPE_MESSAGE, new MessageAlertDialog.OnConfirmListener()
//                {
//                    @Override
//                    public void onConfirmClick()
//                    {
//                        mainView.backToMainFragment();
//                    }
//
//                    @Override
//                    public void onCancelClick()
//                    {
//
//                    }
//                });
//
//        dialog.setCancelable(false);
//        dialog.show(getActivity().getFragmentManager(), "messageDialog");
        //--------------------------test--------------------

        initView();

        EventBus.getDefault().register(this);
        return v;
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
//        if (Prefs.getString("qrCode", "").length() > 5)
//        {
//
//            Logger.d("--QRCode:--", Prefs.getString("qrCode", ""));
//            decryptBarcode(barcode);
//        }
//        continue_ = false;
//        etPassPayment.setText("");
//        etQR.setText("");
//        btnConfirm.revertAnimation(PaymentWithoutCardFragment.this);
//        btnConfirm.setClickable(true);
//        etCvv2.setText("");
//        etAmountPayment.setText("");


    }

    public void initView()
    {
        try
        {
            etAmountPayment = v.findViewById(R.id.etAmountPayment);
            etLayoutCode = v.findViewById(R.id.etLayoutCode);
            etQR = v.findViewById(R.id.etQR);
            llCheckBox = v.findViewById(R.id.llCheckBox);
            etCvv2 = v.findViewById(R.id.etCvv2);
            llCvv2 = v.findViewById(R.id.llCvv2);
            tipCvv2 = v.findViewById(R.id.tipCvv2);
            cbPrint = v.findViewById(R.id.cbPrint);
            llBarcode = v.findViewById(R.id.llBarcode);
            llPayment = v.findViewById(R.id.llPayment);

            llList = v.findViewById(R.id.llList);
            etAmountPayment.addTextChangedListener(new NumberTextWatcher(etAmountPayment));


            btnConfirm = v.findViewById(R.id.btnConfirm);
            llDetailPayment = v.findViewById(R.id.llDetailPayment);
            btnBackToDetail = v.findViewById(R.id.btnBackToDetail);
            btnPaymentConfirm = v.findViewById(R.id.btnPaymentConfirm);
            llPricePeyment = v.findViewById(R.id.llPricePeyment);
            llPaymentPass.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/iran_sans_normal.ttf"));
            tipCvv2.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/iran_sans_normal.ttf"));


            btnConfirm.setText("ادامه");
            btnPaymentConfirm.setText("تایید نهایی");
            btnBackToDetail.setText("بازگشت");
            btnConfirm.setOnClickListener(this);
            btnBackToDetail.setOnClickListener(this);
            btnPaymentConfirm.setOnClickListener(this);
            llBarcode.setOnClickListener(this);
            llList.setOnClickListener(this);
            if (!cardNumberCheck.equals("003725"))
            {
                llCvv2.setVisibility(View.VISIBLE);

            }
            //toolbar
            mToolbar = v.findViewById(R.id.toolbar);

            mToolbar.findViewById(R.id.imgMenu).setOnClickListener(v -> mainView.openDrawer());
            TextView tvUserName = mToolbar.findViewById(R.id.tvUserName);
            tvUserName.setText(TrapConfig.HEADER_USER_NAME);

            tvTitle = v.findViewById(R.id.tvTitle);
            tvTitle.setText("پرداخت بدون کارت");
            tvUserName = v.findViewById(R.id.tvUserName);
            tvUserName.setText(TrapConfig.HEADER_USER_NAME);
            imgMenu = v.findViewById(R.id.imgMenu);

            imgMenu.setOnClickListener(v -> mainView.openDrawer());
            imgBack = v.findViewById(R.id.imgBack);
            imgBack.setOnClickListener(v ->
            {
                getActivity().onBackPressed();
            });
            tvPopularPlayer = mToolbar.findViewById(R.id.tvPopularPlayer);
            tvPopularPlayer.setText(Prefs.getString("PopularPlayer", ""));

        } catch (Exception e)
        {
            e.getMessage();
        }

    }


    @Override
    public void onAnimationEnd()
    {
        btnPaymentConfirm.setBackground(ContextCompat.getDrawable(SingletonContext.getInstance().getContext(), R.drawable.background_button_login));
        btnConfirm.setBackground(ContextCompat.getDrawable(SingletonContext.getInstance().getContext(), R.drawable.background_button_login));

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
                if (!cardNumberCheck.equals("003725"))
                    if (TextUtils.isEmpty(etCvv2.getText().toString()))
                    {
                        mainView.showError("لطفا شماره cvv2 کارت خود را وارد نمایید.");
                        return;
                    }
                cvv2 = etCvv2.getText().toString();
                requestPayment();

                break;
            case R.id.btnConfirm:
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

            case R.id.btnBackToDetail:
                backToHome();

                break;
            case R.id.llBarcode:
                try
                {
                    new TedPermission(getContext())
                            .setPermissionListener(new PermissionListener()
                            {
                                @Override
                                public void onPermissionGranted()
                                {
                                    mainView.openBarcode(BarcodeType.Payment);
                                    isDetailPaymentBarcode = true;
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
            case R.id.llList:


                if (!Utility.isNetworkAvailable())
                {
                    mainView.onInternetAlert();
                    return;

                }


                isDetailPaymentList = true;
                //  getActivity().startActivityForResult(new Intent(getActivity(), MapActivity.class).putExtra("isSelect", true), 9090);


                break;
        }

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
        btnConfirm.revertAnimation(PaymentWithoutCardFragment_old.this);
        btnConfirm.setClickable(true);
        llPayment.setVisibility(View.VISIBLE);
//        llBtnConfirm.setVisibility(View.VISIBLE);
        etQR.setText("");
        Prefs.putString("qrCode", "");
        etAmountPayment.setText("");
        etLayoutCode.setHint("کد پذیرنده");

    }

    public void requestPayment()
    {
        if (!Utility.isNetworkAvailable())
        {
            mainView.onInternetAlert();
            return;
        }
        btnPaymentConfirm.startAnimation();
        btnPaymentConfirm.setClickable(false);
        PaymentPrintPosRequest request = new PaymentPrintPosRequest();
        request.setAmount(Integer.valueOf(etAmountPayment.getText().toString().replaceAll(",", "")));
        request.setPin2(etPassPayment.getText().toString());
        //  request.setDeviceId(qrCode);
        //   request.setUserId(Prefs.getInt("userId", 0));
        request.setCardId(Integer.parseInt(cardNumber));
        request.setCvv2(cvv2);
        request.setIsPrintPos(cbPrint.isChecked());
        request.setExpDate(archiveCardDBModels.getExpireYear() + archiveCardDBModels.getExpireMonth());
        SingletonService.getInstance().getMerchantService().PaymentPrintPosService(this, request);


    }

    @Override
    public void onPause()
    {
        super.onPause();

    }

   /* @Override
    public void cardModel(ArchiveCardDBModel archiveCardDBModels)
    {
        try
        {
            this.cardNumber = archiveCardDBModels.getCardNo();//.getNumber();
            this.cardName = archiveCardDBModels.getFullName();//getfullName();
            this.cardImage = archiveCardDBModels.getCardImage();
            this.archiveCardDBModels = archiveCardDBModels;
            cardNumberCheck = archiveCardDBModels.getCardNo().substring(0, 6);//Number().substring(0, 6);


            if (v != null)
                if (!cardNumberCheck.equals("003725"))
                {

                    llCvv2.setVisibility(View.VISIBLE);
                    YoYo.with(Techniques.SlideInLeft)
                            .duration(200)
                            .playOn(llCvv2);


                } else
                {

                    YoYo.with(Techniques.SlideOutLeft).withListener(new AnimatorListenerAdapter()
                    {
                        @Override
                        public void onAnimationEnd(Animator animation)
                        {
                            super.onAnimationEnd(animation);
                            llCvv2.setVisibility(View.GONE);

                        }
                    })
                            .duration(200)
                            .playOn(llCvv2);

                }
        } catch (Exception e)
        {
            mainView.showError(e.getMessage());

        }


    }

*/

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
                            btnConfirm.revertAnimation(PaymentWithoutCardFragment_old.this);
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
//                        llBtnConfirm.setVisibility(View.GONE);

                        etLayoutCode.setHint("نام پذیرنده");
                        etQR.setText(decryptQrResponse.data.getMerchantName());
                        etAmountPayment.setText(decryptQrResponse.data.getAmount());
                        etQR.setEnabled(false);
                        qrCode = decryptQrResponse.data.getSerialNumberPos();//DeviceId();

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
                        btnConfirm.revertAnimation(PaymentWithoutCardFragment_old.this);
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
            btnPaymentConfirm.revertAnimation(this);
            btnPaymentConfirm.setClickable(true);
            if (posResponse.info.statusCode == 200)
            {
                etPassPayment.setText("");
                PaymentResultDialog dialog = new PaymentResultDialog(getActivity(), posResponse, cardNumber, cardName, etAmountPayment.getText().toString(), cardImage, archiveCardDBModels.getCardNumberColor());
                dialog.show(getActivity().getSupportFragmentManager(), "payment");
                etPassPayment.setText("");
                etCvv2.setText("");
                cvv2 = "";

            } else
            {
                mainView.showError(posResponse.info.message);
            }

        } catch (Exception e)
        {
            btnConfirm.revertAnimation(PaymentWithoutCardFragment_old.this);
            btnConfirm.setClickable(true);
            mainView.showError(e.getMessage());


        }

    }

    @Override
    public void onError(String message)
    {
        btnPaymentConfirm.revertAnimation(this);
        btnPaymentConfirm.setClickable(true);

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
                        llBtnConfirm.setVisibility(View.GONE);

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