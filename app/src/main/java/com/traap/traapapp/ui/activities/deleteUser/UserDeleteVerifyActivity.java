package com.traap.traapapp.ui.activities.deleteUser;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.alimuzaffar.lib.pin.PinEntryEditText;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pixplicity.easyprefs.library.Prefs;
import com.traap.traapapp.R;
import com.traap.traapapp.models.CountryCodeModel;
import com.traap.traapapp.ui.activities.editUser.UserEditVerifyActivity;
import com.traap.traapapp.ui.activities.editUser.UserEditVerifyPresenterImpl;
import com.traap.traapapp.ui.activities.main.MainActivity;
import com.traap.traapapp.ui.base.BaseActivity;
import com.traap.traapapp.ui.base.GoToActivity;
import com.traap.traapapp.utilities.ClearableEditText;
import com.traap.traapapp.utilities.KeyboardUtils;
import com.traap.traapapp.utilities.Logger;
import com.traap.traapapp.utilities.Tools;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import br.com.simplepass.loading_button_lib.interfaces.OnAnimationEndListener;
import io.reactivex.disposables.CompositeDisposable;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class UserDeleteVerifyActivity extends BaseActivity implements UserDeleteVerifyView, OnAnimationEndListener
{
    private UserDeleteVerifyPresenterImpl UserEditVerifyPresenter;
    private CircularProgressButton btnConfirm;
    private TextView tvDesc,txtphoneLast, tvCountDown, tvPhoneNumber, tvMenu, tvResend;
    private int dimeSpace80, dimeSpace40, dimeLogo150, dimeLogo70;
    private RelativeLayout.LayoutParams logoLayoutParams;
    private LinearLayout.LayoutParams spaceLayoutParams;

    private TextInputLayout etLayout;
    private PinEntryEditText codeView;
    private boolean isCode = false;
    private ClearableEditText etMobileNumber;
    private LinearLayout countDownTimer, llPin, llCondition;
    private ArrayList<CountryCodeModel> countryCodeModels = new ArrayList<>();
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
        setContentView(R.layout.activity_edit_user_verify);
//        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        // Prefs.putString("accessToken", "");
        UserEditVerifyPresenter = new UserDeleteVerifyPresenterImpl(getApplicationContext(), this, this);
        initView();
        initCountryCode();
        filter();



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



    }

    public void initView()
    {
        tvDesc = findViewById(R.id.tvDesc);
        txtphoneLast = findViewById(R.id.txtphoneLast);
        // tvTitle = findViewById(R.id.tvTitle);
        //etLayout = findViewById(R.id.etLayout);
        btnConfirm = findViewById(R.id.btnConfirm);
        btnConfirm.setText(getString(R.string.verify_user));

        tvCountDown = findViewById(R.id.tvCountDown);
        codeView = findViewById(R.id.codeView);
        // tvMenu = findViewById(R.id.tvMenu);


        countDownTimer = findViewById(R.id.countDownTimer);
        tvResend = findViewById(R.id.tvResend);
        // tvMenu.setVisibility(View.GONE);
        llPin = findViewById(R.id.llPin);
        // tvTitle.setVisibility(View.GONE);
        UserEditVerifyPresenter.getCode(codeView);
        btnConfirm.setOnClickListener(UserEditVerifyPresenter);

        btnConfirm.setTag("mobile");
        tvDesc.setText(" ﮐﺪ ﻓﻌﺎﻟﺴﺎزی ارﺳﺎل ﺷﺪه ﺑﻪ ﺷﻤﺎره ﺗﻠﻔﻦ ﻫﻤﺮاه خود را وارد ﮐﻨﯿﺪ.");
        String mobileLast = getIntent().getStringExtra("mobileLast");

        txtphoneLast.setText("شماره تلفن همراه : " +Prefs.getString("mobile", ""));
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        UserEditVerifyPresenter.setScreenSize(displayMetrics.heightPixels, displayMetrics.widthPixels);

        tvResend.setOnClickListener(view ->
        {
           // UserEditVerifyPresenter.sendMobileRequest();
            tvResend.setVisibility(View.GONE);
            codeView.setText("");
            tvCountDown.setVisibility(View.VISIBLE);
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
                UserEditVerifyPresenter.verifyRequest();
            }
            else
            {
                showAlert(UserDeleteVerifyActivity.this, "لطفا کد فعال سازی خود را صحیح وارد نمایید.", R.string.error);
            }

        });






    }


    @Override
    protected void onStart()
    {
        super.onStart();
        UserEditVerifyPresenter.onStart();

    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        UserEditVerifyPresenter.onDestroy();

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
        btnConfirm.revertAnimation(UserDeleteVerifyActivity.this);
        btnConfirm.setClickable(true);

    }


    @Override
    public void onButtonActions(boolean canEnter, GoToActivity goToActivity)
    {
       /* if (isCode && canEnter)
        {*/
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
        //getParent().finish();
        //mainView.backToMainFragment();
        intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivityForResult(intent, 100);
        finish();


       /* }
        else
        {
            mobileToCode();
        }*/


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
        countDownTimer.setVisibility(View.VISIBLE);

    }

    @Override
    public void showErrorMessage(String message, String name, boolean b)
    {
        //  showError(this, message);
        if (Tools.isNetworkAvailable(UserDeleteVerifyActivity.this))
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
                etMobileNumber.setVisibility(View.GONE);

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
                btnConfirm.setText(getString(R.string.verify_user));
                llPin.setVisibility(View.GONE);
                etMobileNumber.setVisibility(View.VISIBLE);
                // etCountryName.setVisibility(View.VISIBLE);
                // rlCountryCode.setVisibility(View.VISIBLE);
                isCode = false;
                YoYo.with(Techniques.SlideInLeft)
                        .duration(500)
                        .playOn(etMobileNumber);
               /* YoYo.with(Techniques.SlideInLeft)
                        .duration(500)
                        .playOn(rlCountryCode);
                YoYo.with(Techniques.SlideInLeft)
                        .duration(500)
                        .playOn(etCountryName);*/

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
      /*  if (requestCode == 1002 && resultCode == Activity.RESULT_OK)
        {
            etCountryName.setText(data.getExtras().getString("name"));
            etCountryCode.setText(data.getExtras().getString("code").replace("+", ""));

        }*/
    }
}

