package ir.traap.tractor.android.ui.fragments.paymentWithoutCard;

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
import com.pixplicity.easyprefs.library.Prefs;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import br.com.simplepass.loading_button_lib.interfaces.OnAnimationEndListener;
import ir.traap.tractor.android.R;
import ir.traap.tractor.android.apiServices.generator.SingletonService;
import ir.traap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.traap.tractor.android.apiServices.model.WebServiceClass;
import ir.traap.tractor.android.apiServices.model.getDecQrCode.DecryptQrRequest;
import ir.traap.tractor.android.apiServices.model.getDecQrCode.DecryptQrResponse;
import ir.traap.tractor.android.apiServices.model.paymentPrintPos.PaymentPrintPosRequest;
import ir.traap.tractor.android.apiServices.model.paymentPrintPos.PaymentPrintPosResponse;
import ir.traap.tractor.android.conf.TrapConfig;
import ir.traap.tractor.android.enums.BarcodeType;
import ir.traap.tractor.android.models.dbModels.ArchiveCardDBModel;
import ir.traap.tractor.android.singleton.SingletonContext;
import ir.traap.tractor.android.ui.base.BaseFragment;
import ir.traap.tractor.android.ui.dialogs.PaymentResultDialog;
import ir.traap.tractor.android.ui.fragments.main.MainActionView;
import ir.traap.tractor.android.utilities.Logger;
import ir.traap.tractor.android.utilities.NumberTextWatcher;
import ir.traap.tractor.android.utilities.Utility;


@SuppressLint("ValidFragment")
public class PaymentWithoutCardFragment extends BaseFragment implements OnAnimationEndListener,
        View.OnClickListener, OnServiceStatus<WebServiceClass<PaymentPrintPosResponse>>
{
    private View v;
    private CircularProgressButton btnConfirm, btnPaymentConfirm, btnConfirm2;
    private TextView btnBackToDetail, btnBackToHome;
    private LinearLayout llDetailPayment, llPricePeyment, llPayment, llCvv2, llBtnConfirm, llBtnConfirm2;
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

    public PaymentWithoutCardFragment()
    {

    }

    public static PaymentWithoutCardFragment newInstance(MainActionView mainView)
    {
        PaymentWithoutCardFragment f = new PaymentWithoutCardFragment();
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
        if (v != null)
        {
            if (isDetailPaymentList)
            {
                isDetailPaymentList = false;
                return v;

            } else
            {
                if (isDetailPaymentBarcode)
                {
                    isDetailPaymentBarcode = false;
                    return v;

                } else
                    v = null;

            }

        }

        v = inflater.inflate(R.layout.fragment_payment_without_card, container, false);



        initView();

        return v;
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
        if (Prefs.getString("qrCode", "").length() > 5)
        {

            Logger.d("--QRCode:--", Prefs.getString("qrCode", ""));
            decryptBarcode(barcode);
        }
        continue_ = false;
        etPassPayment.setText("");
        etQR.setText("");
        btnConfirm.revertAnimation(PaymentWithoutCardFragment.this);
        btnConfirm.setClickable(true);
        etCvv2.setText("");
        etAmountPayment.setText("");


    }

    public void initView()
    {
        try
        {
            etAmountPayment = v.findViewById(R.id.etAmountPayment);
            etLayoutCode = v.findViewById(R.id.etLayoutCode);
            etQR = v.findViewById(R.id.etQR);
            llCheckBox = v.findViewById(R.id.llCheckBox);
            btnBackToHome = v.findViewById(R.id.btnBackToHome);
            llBtnConfirm = v.findViewById(R.id.llBtnConfirm);
            llBtnConfirm2 = v.findViewById(R.id.llBtnConfirm2);
            btnConfirm2 = v.findViewById(R.id.btnConfirm2);
            etCvv2 = v.findViewById(R.id.etCvv2);
            llCvv2 = v.findViewById(R.id.llCvv2);
            tipCvv2 = v.findViewById(R.id.tipCvv2);
            cbPrint = v.findViewById(R.id.cbPrint);
            llBarcode = v.findViewById(R.id.llBarcode);
            llPayment = v.findViewById(R.id.llPayment);
            etPassPayment = v.findViewById(R.id.etPassPayment);

            llList = v.findViewById(R.id.llList);
            llPaymentPass = v.findViewById(R.id.llPaymentPass);
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
            btnBackToHome.setText("بازگشت");
            btnConfirm.setOnClickListener(this);
            btnBackToDetail.setOnClickListener(this);
            btnPaymentConfirm.setOnClickListener(this);
            btnBackToHome.setOnClickListener(this);
            llBarcode.setOnClickListener(this);
            llList.setOnClickListener(this);
            btnConfirm2.setOnClickListener(this);
            if (!cardNumberCheck.equals("003725"))
            {
                llCvv2.setVisibility(View.VISIBLE);

            }
            //toolbar
            mToolbar = v.findViewById(R.id.toolbar);

            mToolbar.findViewById(R.id.imgMenu).setOnClickListener(v -> mainView.openDrawer());
            TextView tvUserName = mToolbar.findViewById(R.id.tvUserName);
            tvUserName.setText(Prefs.getString("mobile", ""));

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
            case R.id.btnConfirm2:

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
                    getMerchantByCode(etQR.getText().toString());
*/

                break;


            case R.id.btnBackToDetail:
                backToHome();

                break;
            case R.id.llBarcode:
                try
                {
                    mainView.openBarcode(BarcodeType.Payment);
                    isDetailPaymentBarcode = true;

                } catch (Exception e)
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


            case R.id.btnBackToHome:
                backToHome();
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
        btnConfirm.revertAnimation(PaymentWithoutCardFragment.this);
        btnConfirm.setClickable(true);
        llPayment.setVisibility(View.VISIBLE);
        llBtnConfirm2.setVisibility(View.GONE);
        llBtnConfirm.setVisibility(View.VISIBLE);
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
                new Handler().postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        btnConfirm.revertAnimation(PaymentWithoutCardFragment.this);
                        btnConfirm.setClickable(true);
                    }
                }, 200);


                try
                {
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
                mainView.showError(message);
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
        btnPaymentConfirm.revertAnimation(this);
        btnPaymentConfirm.setClickable(true);
        try
        {
            if (posResponse.info.statusCode == 200)
            {
                etPassPayment.setText("");
                PaymentResultDialog dialog = new PaymentResultDialog(getActivity(), posResponse, cardNumber, cardName, etAmountPayment.getText().toString(), cardImage, archiveCardDBModels.getCardNumberColor());
                dialog.show(getActivity().getFragmentManager(), "payment");
                etPassPayment.setText("");
                etCvv2.setText("");
                cvv2 = "";

            } else
            {
                mainView.showError(posResponse.info.message);
            }

        } catch (Exception e)
        {
            btnConfirm.revertAnimation(PaymentWithoutCardFragment.this);
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
                        llBtnConfirm2.setVisibility(View.VISIBLE);
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
                        mainView.onError(response.getServiceMessage().getMessage(), this.getClass().getSimpleName(), DibaConfig.showClassNameInMessage);

                    }

                } catch (Exception e)
                {

                    mainView.onError(e.getMessage(), this.getClass().getSimpleName(), DibaConfig.showClassNameInException);

                }

            }

            @Override
            public void onError(String message)
            {
                btnConfirm.revertAnimation(PaymentWithoutCardFragment.this);
                btnConfirm.setClickable(true);
                mainView.onError(message, this.getClass().getSimpleName(), DibaConfig.showClassNameInException);

            }
        }, request);

    }*/
}
