package com.traap.traapapp.ui.activities.deleteUser;

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
import com.pixplicity.easyprefs.library.Prefs;
import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.editUser.verifyReq.VerifyRequest;
import com.traap.traapapp.apiServices.model.editUser.verifyRes.Profile;
import com.traap.traapapp.apiServices.model.editUser.verifyRes.VerifyResponse;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.ui.base.GoToActivity;
import com.traap.traapapp.ui.dialogs.MessageAlertDialog;
import com.traap.traapapp.utilities.Logger;
import com.traap.traapapp.utilities.Tools;

import java.util.UUID;

import static com.traap.traapapp.ui.base.BaseActivity.showAlert;

//import com.adpdigital.push.AdpPushClient;


/**
 * Created by Mahsa.Azizi
 */
public class UserDeleteVerifyPresenterImpl implements UserDeleteVerifyPresenter, View.OnClickListener {
    private Context appContext;
    private Context activityContext;
    private UserDeleteVerifyView loginView;
    /* private SendActiveCodeImpl sendActiveCode;
     private ConfirmActiveCodeImpl activeCode;*/
    private EditText mobileNumber;
    private CountDownTimer countDownTimer;
    private PinEntryEditText codeView;
    private final long startTime = 120000;
    private final long interval = 1000;
    private int height, width;
    private Intent intent;


    public UserDeleteVerifyPresenterImpl(Context appContext, Context activityContext, UserDeleteVerifyView loginView)
    {
        this.loginView = loginView;

        this.appContext = appContext;
        this.activityContext = activityContext;
        countDownTimer = new com.traap.traapapp.ui.activities.deleteUser.CountDownTimerResendCode(startTime, interval, loginView);
        // EventBus.getDefault().register(this);
        countDownTimer.start();
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.btnConfirm:
            {


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

                if (TextUtils.isEmpty(codeView.getText().toString()))
                {
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

    public void verifyRequest()
    {
        loginView.showLoading();
        countDownTimer.cancel();
        //loginView.hideLoading();
        sendVerifyRequest();
    }

    private void sendVerifyRequest()
    {
        VerifyRequest request = new VerifyRequest();

        request.setCode(codeView.getText().toString());

        //new Gson().toJson(request);
        SingletonService.getInstance().sendProfileService().deleteUserVerifyCode(request, new OnServiceStatus<WebServiceClass<VerifyResponse>>()
        {
            @Override
            public void onReady(WebServiceClass<VerifyResponse> response)
            {
                try
                {
                    if (response.info.statusCode == 200)
                    {
                      //  setProfileData(response);
                        loginView.hideLoading();

                        showAlertSuccess(activityContext, "حساب کاربری شما با موفقیت حذف شد .", "", true);
                    } else
                    {
                        codeView.setText("");
                        MessageAlertDialog dialog = new MessageAlertDialog((Activity) activityContext, "",
                                response.info.message,
                                MessageAlertDialog.TYPE_ERROR);
                        dialog.show(((Activity) activityContext).getFragmentManager(), "dialog");
                        loginView.hideLoading();
                    }
                } catch (Exception e)
                {
                    MessageAlertDialog dialog = new MessageAlertDialog((Activity) activityContext, "",
                            response.info.message,
                            MessageAlertDialog.TYPE_ERROR);
                    dialog.show(((Activity) activityContext).getFragmentManager(), "dialog");
                    loginView.hideLoading();
                }
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
                    dialog.show(((Activity) activityContext).getFragmentManager(), "dialog");

                } else
                {
                    showAlert(appContext, R.string.networkErrorMessage, R.string.networkError);
                }
            }
        });


    }

    public void showAlertSuccess(Context context, String Msg, String mTitle, boolean finish)
    {
        MessageAlertDialog dialog = new MessageAlertDialog((Activity) context, mTitle, Msg, false,
                "بازگشت به خانه", "", true,
                MessageAlertDialog.TYPE_SUCCESS, new MessageAlertDialog.OnConfirmListener()
        {
            @Override
            public void onConfirmClick()
            {
                if (finish)
                {
                    // ((Activity) context).onBackPressed();
                   loginView.onButtonActions(true, GoToActivity.MainActivity);
                    // Intent returnIntent = new Intent();
                    // ((Activity) context).setResult(Activity.RESULT_OK,returnIntent);
                    //loginView.onButtonActions(true, GoToActivity.MainActivity);
                    // ((Activity) context).finish();

                    // ((Activity) context).getParent().finish();

                }
            }

            @Override
            public void onCancelClick()
            {

            }
        });
        dialog.setCancelable(!finish);
        dialog.show(((Activity) context).getFragmentManager(), "dialog");
    }




    @Override
    public void onResume()
    {
        countDownTimer.start();
    }

    @Override
    public void onDestroy()
    {
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



}
