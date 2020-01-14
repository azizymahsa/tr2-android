package com.traap.traapapp.ui.activities.login;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.InputFilter;
import android.util.DisplayMetrics;
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

import androidx.core.content.ContextCompat;

import com.alimuzaffar.lib.pin.PinEntryEditText;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.material.textfield.TextInputLayout;
import com.pixplicity.easyprefs.library.Prefs;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import br.com.simplepass.loading_button_lib.interfaces.OnAnimationEndListener;
import com.traap.traapapp.R;
import com.traap.traapapp.ui.base.BaseActivity;
import com.traap.traapapp.ui.base.GoToActivity;
import com.traap.traapapp.ui.activities.main.MainActivity;
import com.traap.traapapp.ui.dialogs.MessageAlertDialog;
import com.traap.traapapp.utilities.ClearableEditText;
import com.traap.traapapp.utilities.KeyboardUtils;
import com.traap.traapapp.utilities.Logger;
import com.traap.traapapp.utilities.Tools;
import com.traap.traapapp.utilities.Utility;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

//import android.support.v7.app.AppCompatActivity;

public class LoginActivity extends BaseActivity implements LoginView, OnAnimationEndListener
{
    private LoginPresenterImpl loginPresenter;
    private CircularProgressButton btnConfirm;
    private TextView tvDesc, tvCountDown, tvPhoneNumber, tvMenu, tvResend,txtCondition;
    private int dimeSpace80, dimeSpace40, dimeLogo150, dimeLogo70;
    private RelativeLayout.LayoutParams logoLayoutParams;
    private LinearLayout.LayoutParams spaceLayoutParams;

    private TextInputLayout etLayout;
    private PinEntryEditText codeView;
    private boolean isCode = false;
    private ClearableEditText etMobileNumber;
    private LinearLayout countDownTimer, llPin,llCondition;


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
        Prefs.putString("accessToken","");
        loginPresenter = new LoginPresenterImpl(getApplicationContext(), this, this);
        initView();



        //-----------------test------------------
       /* Prefs.putString("accessToken", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NzExNDcyMDUsInVzZXJfaWQiOjE5LCJqd3QiOiJhY2Nlc3MiLCJqdGkiOiJjZDRhMTlmZTJhMmU0MGQxYWQwZTRhNjkxNWQ3OGNlZSJ9.yaa5Wy-iwcuAPu5tkzYiLg8QCcv1LM9zLg4yBK7zvsY");
        Prefs.putString("refreshToken", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJqd3QiOiJyZWZyZXNoIiwidXNlcl9pZCI6MTksImV4cCI6MTU3MTIyMTUwOSwianRpIjoiZDU5NTAzMTE5ODQwNDNkNjk5MWJjNDQxMjJhOThhMzQifQ.JtsGamNvUX2XiZxq0OxhdZmPmNogQtdX8zjK8tqoKKY");
      */  //-----------------test------------------

    }

    public void initView()
    {
        txtCondition = findViewById(R.id.txtCondition);
        llCondition = findViewById(R.id.llCondition);
        tvDesc = findViewById(R.id.tvDesc);
        // tvTitle = findViewById(R.id.tvTitle);
        //etLayout = findViewById(R.id.etLayout);
        btnConfirm = findViewById(R.id.btnConfirm);
        btnConfirm.setText(getString(R.string.login));

        tvCountDown = findViewById(R.id.tvCountDown);
        codeView = findViewById(R.id.codeView);
        // tvMenu = findViewById(R.id.tvMenu);
        tvPhoneNumber = findViewById(R.id.tvPhoneNumber);
        etMobileNumber = findViewById(R.id.etMobileNumber);

        InputFilter[] filterArray = new InputFilter[1];
        filterArray[0] = new InputFilter.LengthFilter(11);
        etMobileNumber.setFilters(filterArray);

        countDownTimer = findViewById(R.id.countDownTimer);
        tvResend = findViewById(R.id.tvResend);
        // tvMenu.setVisibility(View.GONE);
        llPin = findViewById(R.id.llPin);
        // tvTitle.setVisibility(View.GONE);
        loginPresenter.getCode(codeView);
        loginPresenter.getMobile(etMobileNumber);
        btnConfirm.setOnClickListener(loginPresenter);
        btnConfirm.setTag("mobile");
        tvDesc.setText(Html.fromHtml("جهت ورود به " + "<font color='#ff0000'> تراپ </font>" + " \n" + "  شماره تلفن همراه خود را وارد کنید."));
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        loginPresenter.setScreenSize(displayMetrics.heightPixels, displayMetrics.widthPixels);
        tvResend.setOnClickListener(view -> {
            loginPresenter.sendMobileRequest();
            tvResend.setVisibility(View.GONE);
            codeView.setText("");
            tvCountDown.setVisibility(View.VISIBLE);
        });
        txtCondition.setOnClickListener(view -> {
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




        codeView.setOnPinEnteredListener(new PinEntryEditText.OnPinEnteredListener() {
            @Override
            public void onPinEntered(CharSequence str) {
                if (str.length()==4){
                    loginPresenter.verifyRequest();
                }

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
            startActivityForResult(intent,100);
            finish();
        } else
            mobileToCode();


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
    public void onError(String message, String name, boolean b)
    {

      //  showError(this, message);

        if (!Tools.isNetworkAvailable(LoginActivity.this))
        {
            Logger.e("-OnError-", "Error: " + message);
            Tools.showToast(this, "خطا در دریافت اطلاعات از سرور!", R.color.red);

            // showError(appContext, "خطا در دریافت اطلاعات از سرور!");
        }
        else
        {
            showAlert(this, R.string.networkErrorMessage, R.string.networkError);
        }
    }

    @Override
    public void onError(String message) {
        MessageAlertDialog dialog = new MessageAlertDialog(this, "", message);
        dialog.show(getFragmentManager(), "dialog");
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
                btnConfirm.setText(getString(R.string.login));
                llPin.setVisibility(View.GONE);
                etMobileNumber.setVisibility(View.VISIBLE);
                isCode = false;
                YoYo.with(Techniques.SlideInLeft)
                        .duration(500)
                        .playOn(etMobileNumber);

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
        } else
            super.onBackPressed();
    }
}

