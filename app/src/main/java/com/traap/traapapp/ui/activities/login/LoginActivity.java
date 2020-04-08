package com.traap.traapapp.ui.activities.login;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.InputFilter;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.alimuzaffar.lib.pin.PinEntryEditText;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jakewharton.rxbinding3.widget.RxTextView;
import com.pixplicity.easyprefs.library.Prefs;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import br.com.simplepass.loading_button_lib.interfaces.OnAnimationEndListener;

import com.traap.traapapp.R;
import com.traap.traapapp.models.CountryCodeModel;
import com.traap.traapapp.models.otherModels.newsFilterItem.FilterItem;
import com.traap.traapapp.ui.activities.SearchCountryActivity;
import com.traap.traapapp.ui.adapters.filterArchive.FilterArchiveAdapter;
import com.traap.traapapp.ui.base.BaseActivity;
import com.traap.traapapp.ui.base.GoToActivity;
import com.traap.traapapp.ui.activities.main.MainActivity;
import com.traap.traapapp.ui.dialogs.MessageAlertDialog;
import com.traap.traapapp.utilities.ClearableEditText;
import com.traap.traapapp.utilities.KeyboardUtils;
import com.traap.traapapp.utilities.Logger;
import com.traap.traapapp.utilities.ReplacePersianNumberToEnglish;
import com.traap.traapapp.utilities.Tools;
import com.traap.traapapp.utilities.Utility;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

//import android.support.v7.app.AppCompatActivity;

public class LoginActivity extends BaseActivity implements LoginView, OnAnimationEndListener
{
    private LoginPresenterImpl loginPresenter;
    private CircularProgressButton btnConfirm;
    private TextView tvDesc, tvCountDown, tvPhoneNumber, tvMenu, tvResend, txtCondition,tvChangeNumber;
    private int dimeSpace80, dimeSpace40, dimeLogo150, dimeLogo70;
    private RelativeLayout.LayoutParams logoLayoutParams;
    private LinearLayout.LayoutParams spaceLayoutParams;

    private TextInputLayout etLayout;
    private PinEntryEditText codeView;
    private boolean isCode = false;
    private ClearableEditText etMobileNumber,etInviteCode;
    private LinearLayout countDownTimer, llPin, llCondition,llInvite;
    private ArrayList<CountryCodeModel> countryCodeModels = new ArrayList<>();
    private EditText etCountryName, etCountryCode;
    private CompositeDisposable disposable = new CompositeDisposable();
    private RelativeLayout rlCountryCode;


    @Override
    protected void attachBaseContext(Context context)
    {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        Prefs.putString("accessToken", "");
        loginPresenter = new LoginPresenterImpl(getApplicationContext(), this, this);
        initView();
        initCountryCode();
        filter();

        //-----------------test------------------
       /* Prefs.putString("accessToken", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NzExNDcyMDUsInVzZXJfaWQiOjE5LCJqd3QiOiJhY2Nlc3MiLCJqdGkiOiJjZDRhMTlmZTJhMmU0MGQxYWQwZTRhNjkxNWQ3OGNlZSJ9.yaa5Wy-iwcuAPu5tkzYiLg8QCcv1LM9zLg4yBK7zvsY");
        Prefs.putString("refreshToken", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJqd3QiOiJyZWZyZXNoIiwidXNlcl9pZCI6MTksImV4cCI6MTU3MTIyMTUwOSwianRpIjoiZDU5NTAzMTE5ODQwNDNkNjk5MWJjNDQxMjJhOThhMzQifQ.JtsGamNvUX2XiZxq0OxhdZmPmNogQtdX8zjK8tqoKKY");
      */  //-----------------test------------------

    }

    private void initCountryCode()
    {
        Gson gson = new Gson();
        String json = null;
        try
        {
            InputStream inputStream = getAssets().open("country.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");

        } catch (IOException e)
        {
            e.printStackTrace();
        }

        countryCodeModels = gson.fromJson(json,
                new TypeToken<ArrayList<CountryCodeModel>>()
                {
                }.getType());

    }

    @SuppressLint("CheckResult")
    private void filter()
    {
        RxTextView.textChangeEvents(etCountryCode)

                .subscribe(e ->
                        {
                            Observable.fromIterable(countryCodeModels)
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribeOn(Schedulers.computation())
                                    .filter(x ->
                                    {
                                        return x.getDialCode().equals("+" + e.getText().toString());
                                    })
                                    .toList()
                                    .subscribe(new SingleObserver<List<CountryCodeModel>>()
                                    {
                                        @Override
                                        public void onSubscribe(Disposable d)
                                        {
                                        }

                                        @Override
                                        public void onSuccess(List<CountryCodeModel> codeModels)
                                        {
                                            if (codeModels.size() > 0)
                                            {
                                                etCountryName.setText(codeModels.get(0).getName());
                                            }

                                        }

                                        @Override
                                        public void onError(Throwable e)
                                        {
                                        }
                                    });

                        }
                );


    }

    public void initView()
    {
        txtCondition = findViewById(R.id.txtCondition);
        tvChangeNumber = findViewById(R.id.tvChangeNumber);
        llCondition = findViewById(R.id.llCondition);
        etCountryCode = findViewById(R.id.etCountryCode);
        etCountryName = findViewById(R.id.etCountryName);
        tvDesc = findViewById(R.id.tvDesc);
        // tvTitle = findViewById(R.id.tvTitle);
        //etLayout = findViewById(R.id.etLayout);
        btnConfirm = findViewById(R.id.btnConfirm);
        llInvite = findViewById(R.id.llInvite);
        btnConfirm.setText(getString(R.string.login));

        tvCountDown = findViewById(R.id.tvCountDown);
        codeView = findViewById(R.id.codeView);
        // tvMenu = findViewById(R.id.tvMenu);
        tvPhoneNumber = findViewById(R.id.tvPhoneNumber);
        etMobileNumber = findViewById(R.id.etMobileNumber);
        etInviteCode=findViewById(R.id.etInviteCode);
        rlCountryCode = findViewById(R.id.rlCountryCode);

        InputFilter[] filterArray = new InputFilter[1];
        filterArray[0] = new InputFilter.LengthFilter(11);
        etMobileNumber.setFilters(filterArray);

        InputFilter[] filterArrayInviteCode = new InputFilter[1];
        filterArrayInviteCode[0] = new InputFilter.LengthFilter(8);
        etInviteCode.setFilters(filterArrayInviteCode);

        countDownTimer = findViewById(R.id.countDownTimer);
        tvResend = findViewById(R.id.tvResend);
        // tvMenu.setVisibility(View.GONE);
        llPin = findViewById(R.id.llPin);
        // tvTitle.setVisibility(View.GONE);
        loginPresenter.getCode(codeView);
        loginPresenter.getMobile(etMobileNumber,etCountryCode,etInviteCode);
        btnConfirm.setOnClickListener(loginPresenter);

        btnConfirm.setTag("mobile");
        tvDesc.setText(Html.fromHtml("جهت ورود به " + "<font color='#ff0000'> تراپ </font>" + " \n" + "کشور را انتخاب و شماره تلفن همراه خود را وارد کنید."));
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        loginPresenter.setScreenSize(displayMetrics.heightPixels, displayMetrics.widthPixels);

        tvResend.setOnClickListener(view ->
        {
            loginPresenter.sendMobileRequest();
            tvResend.setVisibility(View.GONE);
            codeView.setText("");
            tvCountDown.setVisibility(View.VISIBLE);
        });

        txtCondition.setOnClickListener(view ->
        {
            Utility.openUrlCustomTab(this, "http://www.traap.com/terms");

        });

        dimeLogo70 = (int) getResources().getDimension(R.dimen._70dp);
        dimeLogo150 = (int) getResources().getDimension(R.dimen._150dp);
        dimeSpace40 = (int) getResources().getDimension(R.dimen._40dp);
        dimeSpace80 = (int) getResources().getDimension(R.dimen._80dp);

        KeyboardUtils.addKeyboardToggleListener(this, isVisible ->
        {
            if (!isVisible)
            {
                logoLayoutParams = new RelativeLayout.LayoutParams(dimeLogo150, dimeLogo150);
                spaceLayoutParams = new LinearLayout.LayoutParams(dimeSpace80, dimeSpace80);
            }
            else
            {
                logoLayoutParams = new RelativeLayout.LayoutParams(dimeLogo70, dimeLogo70);
                spaceLayoutParams = new LinearLayout.LayoutParams(dimeSpace40, dimeSpace40);
            }
            findViewById(R.id.spaceTop).setLayoutParams(spaceLayoutParams);
            findViewById(R.id.logoTraap).setLayoutParams(logoLayoutParams);
        });


        codeView.setOnPinEnteredListener(str ->
        {
            if (str.length() == 4)
            {
                loginPresenter.verifyRequest();
            }
            else
            {
                showAlert(LoginActivity.this, "لطفا کد فعال سازی خود را صحیح وارد نمایید.", R.string.error);
            }

        });


        etCountryName.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View arg0, boolean hasfocus)
            {
                if (hasfocus)
                {
                    startActivityForResult(new Intent(LoginActivity.this, SearchCountryActivity.class), 1002);
                    etCountryName.clearFocus();
                }
            }
        });

        tvChangeNumber.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                codeToMobile();

            }
        });

    }


    @Override
    protected void onStart()
    {
        super.onStart();
        loginPresenter.onStart();

    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        loginPresenter.onDestroy();

    }

    @Override
    public void showLoading()
    {
        btnConfirm.startAnimation();
        btnConfirm.setClickable(false);

    }


    @Override
    public void hideLoading()
    {
        btnConfirm.revertAnimation(LoginActivity.this);
        btnConfirm.setClickable(true);

    }


    @Override
    public void onButtonActions(boolean canEnter, GoToActivity goToActivity)
    {
        if (isCode && canEnter)
        {
            Intent intent = null;
//            switch (goToActivity)
//            {
//                case UserProfileActivity:
//                    intent = new Intent(this, UserProfileActivity.class);
//
//                    break;
//                case PassCodeActivity:
//                    if (Const.TEST)
//                        intent = new Intent(this, MainActivity.class);
//                    else
//
//                        //  intent = new Intent(this, PassCodeActivity.class);
//
//                        break;
//            }
            intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivityForResult(intent, 100);
            finish();
        }
        else
        {
            mobileToCode();
        }


    }


    @Override
    public void onFinishTimer()
    {
        tvResend.setVisibility(View.VISIBLE);
        tvCountDown.setVisibility(View.GONE);
    }

    @Override
    public void onTick(String second)
    {
        tvCountDown.setText(second);
        //   countDownTimer.setVisibility(View.VISIBLE);

    }

    @Override
    public void showErrorMessage(String message, String name, boolean b)
    {
        //  showError(this, message);
        if (Tools.isNetworkAvailable(LoginActivity.this))
        {
            Logger.e("-OnError-", "Error: " + message);
            showErrorMessage(message);

        }
        else
        {
            showAlert(this, R.string.networkErrorMessage, R.string.networkError);
        }
    }

    @Override
    public void showErrorMessage(String message)
    {
        showAlert(this, message, R.string.error);
    }


    @Override
    public void onAnimationEnd()
    {
        // btnConfirm.setText(getString(R.string.send_code));
        btnConfirm.setBackground(ContextCompat.getDrawable(this, R.drawable.background_button_login));
    }

    public void mobileToCode()
    {
        llCondition.setVisibility(View.GONE);
        countDownTimer.setVisibility(View.VISIBLE);
//        btnConfirm.setText(getString(R.string.send_code));
        btnConfirm.setTag("code");
        tvDesc.setText(Html.fromHtml("جهت ورود به " + "<font color='#ff0000'> تراپ </font>" + " \n" + " کد فعال سازی ارسال شده را وارد کنید."));

        tvPhoneNumber.setText("شماره تلفن همراه شما: " + etMobileNumber.getText().toString());

        YoYo.with(Techniques.SlideOutLeft)
                .duration(500)
                .playOn(rlCountryCode);
        YoYo.with(Techniques.SlideOutLeft)
                .duration(500)
                .playOn(etCountryName);
        YoYo.with(Techniques.SlideOutLeft)
                .duration(500)
                .playOn(etInviteCode);
        YoYo.with(Techniques.SlideOutLeft)
                .duration(700).withListener(new Animator.AnimatorListener()
        {
            @Override
            public void onAnimationStart(Animator animator)
            {

            }

            @Override
            public void onAnimationEnd(Animator animator)
            {
                llPin.setVisibility(View.VISIBLE);
                tvChangeNumber.setVisibility(View.VISIBLE);
                etMobileNumber.setVisibility(View.GONE);
                etCountryName.setVisibility(View.GONE);
                rlCountryCode.setVisibility(View.GONE);
                etInviteCode.setVisibility(View.GONE);
                isCode = true;
                YoYo.with(Techniques.SlideInRight)
                        .duration(500)
                        .playOn(findViewById(R.id.llPin));
            }

            @Override
            public void onAnimationCancel(Animator animator)
            {

            }

            @Override
            public void onAnimationRepeat(Animator animator)
            {

            }
        })
                .playOn(findViewById(R.id.etMobileNumber));

    }

    public void codeToMobile()
    {
        countDownTimer.setVisibility(View.GONE);
        btnConfirm.setTag("mobile");
        tvDesc.setText(Html.fromHtml("جهت ورود به " + "<font color='#ff0000'> تراپ </font>" + " شماره\n" + "  شماره تلفن همراه خود را وارد کنید."));
        tvChangeNumber.setVisibility(View.GONE);


        YoYo.with(Techniques.SlideOutRight)
                .duration(500).withListener(new Animator.AnimatorListener()
        {
            @Override
            public void onAnimationStart(Animator animation)
            {

            }

            @Override
            public void onAnimationEnd(Animator animation)
            {
                btnConfirm.setText(getString(R.string.login));
                llPin.setVisibility(View.GONE);
                etMobileNumber.setVisibility(View.VISIBLE);
                etCountryName.setVisibility(View.VISIBLE);
                rlCountryCode.setVisibility(View.VISIBLE);
                etInviteCode.setVisibility(View.VISIBLE);
                isCode = false;
                YoYo.with(Techniques.SlideInLeft)
                        .duration(500)
                        .playOn(etMobileNumber);
                YoYo.with(Techniques.SlideInLeft)
                        .duration(500)
                        .playOn(rlCountryCode);
                YoYo.with(Techniques.SlideInLeft)
                        .duration(500)
                        .playOn(etCountryName);
                YoYo.with(Techniques.SlideInLeft)
                        .duration(500)
                        .playOn(etInviteCode);

            }

            @Override
            public void onAnimationCancel(Animator animation)
            {

            }

            @Override
            public void onAnimationRepeat(Animator animation)
            {

            }
        })

                .playOn(llPin);


    }

    @Override
    public void onBackPressed()
    {
        if (isCode)
        {
            codeToMobile();
        }
        else
        {
            super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1002 && resultCode == Activity.RESULT_OK)
        {
            etCountryName.setText(data.getExtras().getString("name"));
            etCountryCode.setText(data.getExtras().getString("code").replace("+", ""));

        }
    }
}

