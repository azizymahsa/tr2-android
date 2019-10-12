package ir.trap.tractor.android.ui.activities.login;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.alimuzaffar.lib.pin.PinEntryEditText;

import org.greenrobot.eventbus.EventBus;

import ir.trap.tractor.android.R;
import ir.trap.tractor.android.ui.base.GoToActivity;
import library.android.eniac.utility.Utility;


/**
 * Created by Javad.Abadi on 7/2/2018.
 */
public class LoginPresenterImpl implements LoginPresenter, View.OnClickListener
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
                    sendMobileRequest();
                   /* new TedPermission(SingletonContext.getInstance().getContext())
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
                            .check();*/
                }
                else
                {
                    if (TextUtils.isEmpty(codeView.getText().toString()))
                    {
                        loginView.onError("لطفا کد فعال سازی را وارد نمایید", this.getClass().getSimpleName(), false);
                        return;
                    }
                    loginView.showLoading();
                    loginView.onButtonActions(true, GoToActivity.UserActivity);
                    loginView.hideLoading();

                /*    activeCode.findCodeActiveRequest(appContext, activityContext, this, mobileNumber.getText().toString(),
                            codeView.getText().toString(), height, width);*/

                }

                break;

        }

    }

    public void sendMobileRequest()
    {
        loginView.showLoading();


        loginView.onButtonActions(false, null);
        loginView.hideLoading();
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
        loginView.hideLoading();

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
        loginView.hideLoading();
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

/*    @Override
    public void onFinishedActive(ConfirmActiveCodeResponse response)
    {
        loginView.hideLoading();

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
        loginView.hideLoading();
    }*/


/*    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(SmsEvent event)
    {
        codeView.setText(event.getCode());
        activeCode.findCodeActiveRequest(appContext, activityContext, this, mobileNumber.getText().toString(),
                codeView.getText().toString(), height, width);
    }*/


}
