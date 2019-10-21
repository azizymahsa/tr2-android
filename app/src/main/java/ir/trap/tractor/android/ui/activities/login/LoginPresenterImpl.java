package ir.trap.tractor.android.ui.activities.login;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;

import com.alimuzaffar.lib.pin.PinEntryEditText;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.pixplicity.easyprefs.library.Prefs;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import ir.trap.tractor.android.R;
import ir.trap.tractor.android.apiServices.generator.SingletonService;
import ir.trap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.trap.tractor.android.apiServices.model.WebServiceClass;
import ir.trap.tractor.android.apiServices.model.login.LoginRequest;
import ir.trap.tractor.android.apiServices.model.login.LoginResponse;
import ir.trap.tractor.android.apiServices.model.verify.Profile;
import ir.trap.tractor.android.apiServices.model.verify.VerifyRequest;
import ir.trap.tractor.android.apiServices.model.verify.VerifyResponse;
import ir.trap.tractor.android.conf.TrapConfig;
import ir.trap.tractor.android.singleton.SingletonContext;
import ir.trap.tractor.android.ui.base.GoToActivity;
import ir.trap.tractor.android.utilities.IMEI_Device;
import ir.trap.tractor.android.utilities.Tools;
import library.android.eniac.utility.Utility;


/**
 * Created by Javad.Abadi on 7/2/2018.
 */
public class LoginPresenterImpl implements LoginPresenter, View.OnClickListener, OnServiceStatus<WebServiceClass<LoginResponse>>
{
    private Context appContext;
    private Context activityContext;
    private LoginView loginView;
    /* private SendActiveCodeImpl sendActiveCode;
     private ConfirmActiveCodeImpl activeCode;*/
    private EditText mobileNumber;
    private CountDownTimer countDownTimer;
    private PinEntryEditText codeView;
    private final long startTime = 120000;
    private final long interval = 1000;
    private int height, width;
    private Intent intent;


    public LoginPresenterImpl(Context appContext, Context activityContext, LoginView loginView)
    {
        this.loginView = loginView;

        this.appContext = appContext;
        this.activityContext = activityContext;
        countDownTimer = new CountDownTimerResendCode(startTime, interval, loginView);
        // EventBus.getDefault().register(this);

    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.btnConfirm:


                if (TextUtils.isEmpty(mobileNumber.getText().toString()))
                {
                    loginView.onError("لطفا شماره موبایل خود را وارد نمایید", this.getClass().getSimpleName(), false);
                    return;
                }
                if (mobileNumber.getText().toString().length() != 11)
                {
                    loginView.onError("لطفا شماره موبایل خود را صحیح وارد نمایید", this.getClass().getSimpleName(), false);
                    return;
                }
                if (!Utility.isNetworkAvailable())
                {
                    loginView.onError("اینترنت خود را بررسی نمایید", this.getClass().getSimpleName(), false);
                    return;

                }

                if (view.getTag().equals("mobile"))
                {
                    // sendMobileRequest();
                    new TedPermission(SingletonContext.getInstance().getContext())
                            .setPermissionListener(new PermissionListener()
                            {
                                @Override
                                public void onPermissionGranted()
                                {

                                    sendMobileRequest();
                                }

                                @Override
                                public void onPermissionDenied(ArrayList<String> deniedPermissions)
                                {
                                    sendMobileRequest();
                                }
                            })
                            .setPermissions(Manifest.permission.RECEIVE_SMS)
                            .check();
                } else
                {
                    if (TextUtils.isEmpty(codeView.getText().toString()))
                    {
                        loginView.onError("لطفا کد فعال سازی را وارد نمایید", this.getClass().getSimpleName(), false);
                        return;
                    }
                    loginView.showLoading();
                    sendVerifyRequest();


                /*    activeCode.findCodeActiveRequest(appContext, activityContext, this, mobileNumber.getText().toString(),
                            codeView.getText().toString(), height, width);*/

                }

                break;

        }

    }

    private void sendVerifyRequest()
    {
        VerifyRequest request = new VerifyRequest();
        request.setUsername(mobileNumber.getText().toString());
        request.setCode(codeView.getText().toString());
//        request.setCurrentVersion(BuildConfig.VERSION_NAME);
        request.setDevice_type(TrapConfig.AndroidDeviceType);
        request.setImei(IMEI_Device.getIMEI(SingletonContext.getInstance().getContext(), activityContext));
//        request.setImei("864890030464324");

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) activityContext).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        request.setScreenSizeHeight(String.valueOf(displayMetrics.heightPixels));
        request.setScreenSizeWidth(String.valueOf(displayMetrics.widthPixels));

        SingletonService.getInstance().getVerifyService().verify(new OnServiceStatus<WebServiceClass<VerifyResponse>>()
        {
            @Override
            public void onReady(WebServiceClass<VerifyResponse> response)
            {
                if (response != null)
                {
                    setProfileData(response);
                    loginView.onButtonActions(true, GoToActivity.UserActivity);
                    loginView.hideLoading();
                } else
                {
                    Tools.showToast(appContext, "خطایی رخ داده است", R.color.red);
                    loginView.hideLoading();
                }
            }

            @Override
            public void onError(String message)
            {
                loginView.hideLoading();
                Tools.showToast(appContext, message, R.color.red);
            }
        }, request);
    }

    private void setProfileData(WebServiceClass<VerifyResponse> response)
    {
        Prefs.putString("accessToken", "Bearer " + response.data.getAccess());
//        Prefs.putString("accessToken","Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MDMwOTM2MTcsInVzZXJfaWQiOjE5LCJqd3QiOiJhY2Nlc3MiLCJqdGkiOiI1NjA3MTg3NDUyOWE0MTQ5YmU4MDliMTBkOTY3NzI5YSJ9.EQ3pzj46tNquy1AKiZNJ3rPcBg8DR1PaDFpeikuWX8I");

        Profile profile = response.data.getProfile();
        Prefs.putString("firstName", profile.getFirstName());
        Prefs.putString("lastName", profile.getLastName());
        Prefs.putString("englishName", profile.getEnglishName());
        if (profile.getBirthday() != null)
        {
            Prefs.putString("birthday", profile.getBirthday().toString());
        }
        if (profile.getPopularPlayer() != null)
        {
            Prefs.putInt("popularPlayer", profile.getPopularPlayer());
        }
        Prefs.putString("nationalCode", profile.getNationalCode());
        Prefs.putString("keyInvite", profile.getKeyInvite());

    }

    public void sendMobileRequest()
    {
        loginView.showLoading();

        LoginRequest request = new LoginRequest();
        request.setUsername(mobileNumber.getText().toString());
        SingletonService.getInstance().getLoginService().login(this, request);


      /*  intent = new Intent(appContext, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        appContext.startActivity(intent);
        ((Activity) activityContext).finish();*/

        // sendActiveCode.findCodeDataRequest(this, mobileNumber.getText().toString());

    }

    @Override
    public void onResume()
    {

    }

    @Override
    public void onDestroy()
    {
        EventBus.getDefault().unregister(this);
        countDownTimer.cancel();


    }

    @Override
    public void onStart()
    {


    }

    @Override
    public void onBack()
    {
        countDownTimer.cancel();
    }

/*
    @Override
    public void onFinishedSendCode(SendActiveCodeResponse activeCodeResponse)
    {
        loginView.hideFavoriteCardParentLoading();

        if (activeCodeResponse.getServiceMessage().getCode() == 200)
        {
            loginView.onButtonActions(false, null);
            countDownTimer.start();
        } else if (activeCodeResponse.getServiceMessage().getCode() == 201)
        {
            loginView.onButtonActions(false, null);
            loginView.onError(activeCodeResponse.getServiceMessage().getMessage(), this.getClass().getSimpleName(), DibaConfig.showClassNameInMessage);
        } else
        {
            loginView.onError(activeCodeResponse.getServiceMessage().getMessage(), this.getClass().getSimpleName(), DibaConfig.showClassNameInMessage);
            countDownTimer.cancel();
        }
    }

    @Override
    public void onErrorSendCode(String error)
    {
        loginView.hideFavoriteCardParentLoading();
        loginView.onError(error, this.getClass().getSimpleName(), DibaConfig.showClassNameInException);

    }
*/


    @Override
    public void getCode(PinEntryEditText codeView)
    {
        this.codeView = codeView;

    }

    @Override
    public void getMobile(EditText mobile)
    {
        mobileNumber = mobile;

    }

    @Override
    public void setScreenSize(int height, int width)
    {
        this.height = height;
        this.width = width;

    }

    @Override
    public void onReady(WebServiceClass<LoginResponse> response)
    {
        if (response != null)
        {
            loginView.onButtonActions(false, null);
            countDownTimer.start();
            loginView.hideLoading();
        } else
        {
            Tools.showToast(appContext, "خطایی رخ داده است", R.color.red);
            loginView.hideLoading();
        }
      /*  if (globalResponseWebServiceClass.statusCode == 200)
        {
            loginView.onButtonActions(false, null);
            countDownTimer.start();
        } else
        {
            loginView.onError(activeCodeResponse.getServiceMessage().getMessage(), this.getClass().getSimpleName(), DibaConfig.showClassNameInMessage);
            countDownTimer.cancel();
        }*/
    }


    @Override
    public void onError(String message)
    {
        Tools.showToast(appContext, message, R.color.red);
        loginView.hideLoading();
    }

/*    @Override
    public void onFinishedActive(ConfirmActiveCodeResponse response)
    {
        loginView.hideFavoriteCardParentLoading();

        if (response.getServiceMessage().getCode() == 200)
        {
            if (response.getLastName() == null || response.getFirstName() == null)
            {
                loginView.onButtonActions(true, GoToActivity.UserActivity);
                Prefs.putString("mobile", response.getMobile());
                Prefs.putString("keyInvite", response.getKeyInvite());
            }
            else
            {
                loginView.onButtonActions(true, GoToActivity.PassCodeActivity);
                Prefs.putString(Utility.encryption(SingletonDiba.getInstance().getPASS_KEY()), Utility.encryption(response.getPassword()));
                Prefs.putString("firstName", response.getFirstName());
                Prefs.putString("lastName", response.getLastName());
                Prefs.putString("image", response.getImage());
                Prefs.putString("mobile", response.getMobile());
                Prefs.putString("email", response.getEmail());
                Prefs.putString("codeMeli", response.getCodeMeli());
                Prefs.putString("address", response.getAddress());
                Prefs.putString("keyInvite", response.getKeyInvite());
                Prefs.putString("serverToken", "bearer "+response.getToken());

                if (response.getPosDeviceId() != null)
                    Prefs.putInt("posId", Integer.valueOf(response.getPosDeviceId()));

                Prefs.putString("userImage", response.getImage());

            }
            Prefs.putInt("userId", response.getUserId());

        } else
        {
            codeView.clearComposingText();
            loginView.onError(response.getServiceMessage().getMessage(), this.getClass().getSimpleName(), DibaConfig.showClassNameInMessage);
        }
    }

    @Override
    public void onErrorActive(String error)
    {
        loginView.onError(error, this.getClass().getSimpleName(), DibaConfig.showClassNameInException);
        loginView.hideFavoriteCardParentLoading();
    }*/


/*    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(SmsEvent event)
    {
        codeView.setText(event.getCode());
        activeCode.findCodeActiveRequest(appContext, activityContext, this, mobileNumber.getText().toString(),
                codeView.getText().toString(), height, width);
    }*/


}
