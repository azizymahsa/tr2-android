package com.traap.traapapp.ui.activities.editUser;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;

import com.alimuzaffar.lib.pin.PinEntryEditText;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.pixplicity.easyprefs.library.Prefs;
import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;

import com.traap.traapapp.apiServices.model.editUser.verifyReq.VerifyRequest;
import com.traap.traapapp.apiServices.model.editUser.verifyRes.VerifyResponse;

import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.singleton.SingletonContext;

import com.traap.traapapp.ui.base.GoToActivity;
import com.traap.traapapp.ui.dialogs.MessageAlertDialog;
import com.traap.traapapp.utilities.Logger;
import com.traap.traapapp.utilities.Tools;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.UUID;

import static com.traap.traapapp.ui.base.BaseActivity.showAlert;

//import com.adpdigital.push.AdpPushClient;


/**
 * Created by Mahsa.Azizi
 */
public class UserEditVerifyPresenterImpl implements UserEditVerifyPresenter, View.OnClickListener {
    private Context appContext;
    private Context activityContext;
    private UserEditVerifyView loginView;
    /* private SendActiveCodeImpl sendActiveCode;
     private ConfirmActiveCodeImpl activeCode;*/
    private EditText mobileNumber;
    private CountDownTimer countDownTimer;
    private PinEntryEditText codeView;
    private final long startTime = 120000;
    private final long interval = 1000;
    private int height, width;
    private Intent intent;


    public UserEditVerifyPresenterImpl(Context appContext, Context activityContext, UserEditVerifyView loginView) {
        this.loginView = loginView;

        this.appContext = appContext;
        this.activityContext = activityContext;
        countDownTimer = new com.traap.traapapp.ui.activities.editUser.CountDownTimerResendCode(startTime, interval, loginView);
        // EventBus.getDefault().register(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnConfirm: {


               /* if (view.getTag().equals("mobile")) {
                    // sendMobileRequest();
                    new TedPermission(SingletonContext.getInstance().getContext())
                            .setPermissionListener(new PermissionListener() {
                                @Override
                                public void onPermissionGranted() {
                                    //sendMobileRequest();

                                }

                                @Override
                                public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                                   // sendMobileRequest();

                                }
                            })
                            .setPermissions(Manifest.permission.RECEIVE_SMS)
                            .check();
                } else {*/

                    if (TextUtils.isEmpty(codeView.getText().toString())) {
                        loginView.showErrorMessage("لطفا کد فعال سازی را وارد نمایید.", this.getClass().getSimpleName(), false);
                        return;
                    }
                    loginView.showLoading();
                    sendVerifyRequest();


               // }
                break;
            }
        }
    }

    public void verifyRequest() {
        loginView.showLoading();
        sendVerifyRequest();
    }

    private void sendVerifyRequest() {
        VerifyRequest request = new VerifyRequest();

        request.setUsername(Prefs.getString("mobileLast", ""));
        request.setCode(codeView.getText().toString());
//        request.setCurrentVersion(BuildConfig.VERSION_NAME);0
        request.setDeviceType(TrapConfig.AndroidDeviceType);

//        request.setImei(IMEI_Device.getIMEI(SingletonContext.getInstance().getContext(), activityContext));
        request.setImei(UUID.randomUUID().toString());
        request.setDeviceModel(Build.BRAND + "-" + Build.MODEL);
//        request.setImei("864890030464324");

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) activityContext).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        request.setScreenSizeHeight(String.valueOf(displayMetrics.heightPixels));
        request.setScreenSizeWidth(String.valueOf(displayMetrics.widthPixels));

        SingletonService.getInstance().sendProfileService().editUserVerify(request, new OnServiceStatus<WebServiceClass<VerifyResponse>>() {
            @Override
            public void onReady(WebServiceClass<VerifyResponse> response) {
                try {
                    if (response.info.statusCode == 200) {
                        setProfileData(response);
                        loginView.onButtonActions(true, GoToActivity.MainActivity);
                        loginView.hideLoading();


                    } else {
                        codeView.setText("");
                        MessageAlertDialog dialog = new MessageAlertDialog((Activity) activityContext, "",
                                response.info.message,
                                MessageAlertDialog.TYPE_ERROR);
                        dialog.show(((Activity) activityContext).getFragmentManager(), "dialog");
                        loginView.hideLoading();
                    }
                } catch (Exception e) {
                    MessageAlertDialog dialog = new MessageAlertDialog((Activity) activityContext, "",
                            response.info.message,
                            MessageAlertDialog.TYPE_ERROR);
                    dialog.show(((Activity) activityContext).getFragmentManager(), "dialog");
                    loginView.hideLoading();
                }
            }

            @Override
            public void onError(String message) {
                loginView.hideLoading();
                if (Tools.isNetworkAvailable((Activity) activityContext)) {
                    Logger.e("-OnError-", "Error: " + message);
                    MessageAlertDialog dialog = new MessageAlertDialog((Activity) activityContext, "",
                            "خطا در دریافت اطلاعات از سرور!",
                            MessageAlertDialog.TYPE_ERROR);
                    dialog.show(((Activity) activityContext).getFragmentManager(), "dialog");

                } else {
                    showAlert(appContext, R.string.networkErrorMessage, R.string.networkError);
                }
            }
        });


    }

    private void setProfileData(WebServiceClass<VerifyResponse> response) {


       /* Prefs.putString("accessToken", "Bearer " + response.data.getAccess());

        Profile profile = response.data.getProfile();
        Prefs.putString("firstName", profile.getFirstName());
        Prefs.putString("lastName", profile.getLastName());
        Prefs.putString("FULLName", profile.getFirstName() + " " + profile.getLastName());
        Prefs.putString("nickName", profile.getEnglishName());
        if (profile.getBirthday() != null)
        {
            Prefs.putString("birthday", profile.getBirthday().toString());
        }
        if (profile.getPopularPlayer() != null)
        {
            Prefs.putInt("popularPlayer", profile.getPopularPlayer()==0 ? 12 : profile.getPopularPlayer());
        }
        Prefs.putString("nationalCode", profile.getNationalCode());
        Prefs.putString("keyInvite", profile.getKeyInvite());*/

    }

    public void sendMobileRequest() {
       /* loginView.showLoading();

        UserEditVerifyRequest request = new UserEditVerifyRequest();
        request.setUsername(mobileNumber.getText().toString());
        Prefs.putString("mobile", mobileNumber.getText().toString());
        SingletonService.getInstance().getLoginService().login(this, request);
*/


    }

    @Override
    public void onResume() {
        //loginView.onButtonActions(false, null);
        countDownTimer.start();
        //  loginView.hideLoading();
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        countDownTimer.cancel();


    }

    @Override
    public void onStart() {


    }

    @Override
    public void onBack() {
        countDownTimer.cancel();
    }


    @Override
    public void getCode(PinEntryEditText codeView) {
        this.codeView = codeView;

    }

    @Override
    public void getMobile(EditText mobile) {
        mobileNumber = mobile;

    }

    @Override
    public void setScreenSize(int height, int width) {
        this.height = height;
        this.width = width;

    }

   /* @Override
    public void onReady(WebServiceClass<LoginResponse> response)
    {
        try{
            if (response != null)
            {
                loginView.onButtonActions(false, null);
                countDownTimer.start();
                loginView.hideLoading();

            }
            else
            {
                MessageAlertDialog dialog = new MessageAlertDialog((Activity) activityContext, "",
                        "خطایی رخ داده است.",
                        MessageAlertDialog.TYPE_ERROR);
                dialog.show(((Activity)activityContext).getFragmentManager(), "dialog");
                loginView.hideLoading();
            }
        }catch (Exception e){}


    }


    @Override
    public void onError(String message)
    {
        loginView.hideLoading();
        if (Tools.isNetworkAvailable((Activity) activityContext))
        {
            Logger.e("-OnError-", "Error: " + message);
            MessageAlertDialog dialog = new MessageAlertDialog((Activity) activityContext, "",
                    "خطا در دریافت اطلاعات از سرور!",
                    MessageAlertDialog.TYPE_ERROR);
            dialog.show(((Activity)activityContext).getFragmentManager(), "dialog");

        }
        else
        {
            showAlert(activityContext, R.string.networkErrorMessage, R.string.networkError);
        }
    }*/


}
